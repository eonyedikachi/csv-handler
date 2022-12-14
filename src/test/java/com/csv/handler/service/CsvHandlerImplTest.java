package com.csv.handler.service;

import com.csv.handler.dtos.CsvDto;
import com.csv.handler.entities.CsvEntity;
import com.csv.handler.repos.CsvHandlerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CsvHandlerImplTest {


    @InjectMocks
    private CsvHandlerImpl csvHandler;

    @Mock
    private CsvHandlerRepository csvHandlerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadCSVFile() throws IOException {
        MultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", "some content".getBytes());
        when(csvHandlerRepository.saveAll(anyList())).thenReturn(List.of(new CsvEntity()));

        List<CsvDto> result = csvHandler.uploadCSVFile(file);
        assertEquals(1, result.size());

        verify(csvHandlerRepository, times(1)).saveAll(anyList());
        verifyNoMoreInteractions(csvHandlerRepository);
    }

    @Test
    public void testGetCSVRecord() {
        String id = "7498jfjnreifbrefhbre";
        when(csvHandlerRepository.findById(id)).thenReturn(java.util.Optional.of(new CsvEntity()));

        CsvDto result = csvHandler.getCSVRecord(id);
        assertNotNull(result);

        verify(csvHandlerRepository, times(1)).findById(id);
        verifyNoMoreInteractions(csvHandlerRepository);
    }

    @Test
    public void testDeleteCSVRecord() {
        String id = "7498jfjnreifbrefhbre";
        doNothing().when(csvHandlerRepository).deleteById(id);

        csvHandler.deleteCSVRecord(id);

        verify(csvHandlerRepository, times(1)).deleteById(id);
        verifyNoMoreInteractions(csvHandlerRepository);
    }

}