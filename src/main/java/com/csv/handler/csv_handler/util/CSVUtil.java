package com.csv.handler.csv_handler.util;

import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CSVUtil {

    public static String TYPE = "text/csv";

    public static boolean isValidISODateTime(String date) {
        try {
            Instant.from(DateTimeFormatter.ISO_INSTANT.parse(date));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }

    }

    public static boolean hasCSVFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }
}
