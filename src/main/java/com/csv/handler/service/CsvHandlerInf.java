package com.csv.handler.service;

import com.csv.handler.dtos.CsvDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CsvHandlerInf {

    /**
     * @param file
     * @return
     */
    List<CsvDto> uploadCSVFile(MultipartFile file);

    /**
     * @param id
     * @return
     */
    CsvDto getCSVRecord(String id);

    /**
     * @param id
     */
    void deleteCSVRecord(String id);
}
