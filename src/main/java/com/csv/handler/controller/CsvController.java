package com.csv.handler.controller;

import com.csv.handler.dtos.CsvDto;
import com.csv.handler.service.CsvHandlerInf;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "/api/csv", produces = MediaType.APPLICATION_JSON_VALUE)
public class CsvController {

    @Autowired
    private CsvHandlerInf csvHandlerService;

    public CsvController(){

    }

    @PostMapping("/upload")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<List<CsvDto>> uploadFile(@RequestParam("csvfile") MultipartFile file) {

        return ResponseEntity.ok(csvHandlerService.uploadCSVFile(file));
    }

    @GetMapping("/")
    @ApiResponse(responseCode = "200")
    public ResponseEntity<CsvDto> getRecord(@RequestParam("primary-key") String id) {
        return ResponseEntity.ok(csvHandlerService.getCSVRecord(id));
    }

    @DeleteMapping("/")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRecord(@RequestParam("primary-key") String id) {
        csvHandlerService.deleteCSVRecord(id);
        return ResponseEntity.noContent().build();
    }

}
