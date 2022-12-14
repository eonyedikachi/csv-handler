package com.csv.handler.controller;

import com.csv.handler.dtos.CsvDto;
import com.csv.handler.service.CsvHandlerInf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CsvControllerTest {

    @InjectMocks
    private CsvController csvController;

    @Mock
    private CsvHandlerInf csvHandlerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadFile() {
        MultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", "28fdhjdfhfd, John, Jobsdjibfhfhfhfh".getBytes());
        when(csvHandlerService.uploadCSVFile(file)).thenReturn(List.of(new CsvDto()));

        ResponseEntity<List<CsvDto>> response = csvController.uploadFile(file);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());

        verify(csvHandlerService, times(1)).uploadCSVFile(file);
        verifyNoMoreInteractions(csvHandlerService);
    }

    @Test
    public void testGetRecord() {
        String id = "28fdhjdfhfd";
        when(csvHandlerService.getCSVRecord(id)).thenReturn(new CsvDto());

        ResponseEntity<CsvDto> response = csvController.getRecord(id);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        verify(csvHandlerService, times(1)).getCSVRecord(id);
        verifyNoMoreInteractions(csvHandlerService);
    }

    @Test
    public void testDeleteRecord() {
        String id = "28fdhjdfhfd";
        doNothing().when(csvHandlerService).deleteCSVRecord(id);

        ResponseEntity<Void> response = csvController.deleteRecord(id);
        assertEquals(204, response.getStatusCodeValue());

        verify(csvHandlerService, times(1)).deleteCSVRecord(id);
        verifyNoMoreInteractions(csvHandlerService);
    }
}