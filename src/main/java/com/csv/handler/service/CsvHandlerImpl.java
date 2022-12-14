package com.csv.handler.service;

import com.csv.handler.entities.CsvEntity;
import com.csv.handler.dtos.CsvDto;
import com.csv.handler.repos.CsvHandlerRepository;
import com.csv.handler.exceptions.NotFoundException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.csv.handler.util.CSVUtil;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
@Slf4j
public class CsvHandlerImpl implements CsvHandlerInf {

    private ModelMapper mapper = new ModelMapper();

    private final CsvHandlerRepository csvHandlerRepository;

    public CsvHandlerImpl(final CsvHandlerRepository csvHandlerRepository) {
        this.csvHandlerRepository = csvHandlerRepository;
    }


    @Override
    public List<CsvDto> uploadCSVFile(MultipartFile file) {
        log.info("About to upload CSV file: {}", file.getOriginalFilename());

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            CsvToBean<CsvDto> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CsvDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<CsvDto> csvList = csvToBean.parse();

            csvList  = getValidRecords(csvList);

            List<CsvEntity> entities = csvList.stream().map(value ->
                    mapToEntity(value)
            ).collect(Collectors.toList());

            csvHandlerRepository.saveAll(entities);

            log.info("Successfully uploaded CSV file: {}", file.getOriginalFilename());
            return csvList;

        } catch (IOException e) {
            log.error("Error uploading CSV file: {}", file.getOriginalFilename(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading CSV file: " + file.getOriginalFilename());
        } catch (IllegalStateException e) {
            log.error("Error parsing CSV file", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error parsing CSV file: " + file.getOriginalFilename());
        }
    }

    @Override
    public CsvDto getCSVRecord(String id) {
        log.info("Fetching CSV record with PRIMARY_KEY {}" , id);

        CsvDto csv = csvHandlerRepository.findById(id)
                .map(cSVEntity -> mapToDTO(cSVEntity))
                .orElseThrow(() -> {
                    log.warn("No record with PRIMARY_KEY {} found", id);
                    throw new NotFoundException("Invalid PRIMARY_KEY: " + id);
                });

        log.info("Successfully fetched CSV record", csv);
        return csv;
    }

    @Override
    public void deleteCSVRecord(String id) {
        log.info("About to delete CSV record with PRIMARY_KEY {}", id);

        if(csvHandlerRepository.existsById(id)){
            log.info("Successfully deleted CSV record with PRIMARY_KEY {}", id);
            csvHandlerRepository.deleteById(id);
        } else{
            log.warn("Can not delete CSV record with PRIMARY_KEY {}: ", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "PRIMARY_KEY: " + id);
        }

    }

    private CsvDto mapToDTO(final CsvEntity entity) {
        CsvDto dto = new CsvDto();
        dto.setPrimaryKey(entity.getId());

        if (entity.getName() != null)
        dto.setName(entity.getName());

        if (entity.getDescription() != null)
        dto.setDescription(entity.getDescription());

        if (entity.getUpdatedTimestamp() != null)
        dto.setUpdatedTimestamp(entity.getUpdatedTimestamp().toString());

        return dto;
    }

    private CsvEntity mapToEntity(final CsvDto dto) {
        CsvEntity entity = new CsvEntity();
        entity.setId(dto.getPrimaryKey());

        if (dto.getName() != null)
        entity.setName(dto.getName());

        if (dto.getDescription() != null)
        entity.setDescription(dto.getDescription());

        if (dto.getUpdatedTimestamp() != null)
        entity.setUpdatedTimestamp(LocalDateTime.parse(dto.getUpdatedTimestamp()));

        return entity;
    }

    private List<CsvDto> getValidRecords(List<CsvDto> records){
        List<CsvDto> result = new ArrayList<>();
        for (CsvDto record: records){
            if (!StringUtils.isEmpty(record.getPrimaryKey()) && CSVUtil.isValidISODateTime(record.getUpdatedTimestamp())){
                result.add(record);
            }
        }

        return result;
    }
}
