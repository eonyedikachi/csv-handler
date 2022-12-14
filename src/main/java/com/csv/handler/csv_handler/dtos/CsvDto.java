package com.csv.handler.csv_handler.dtos;

import com.opencsv.bean.CsvBindByName;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class CsvDto {

    @Size(max = 255)
    @CsvBindByName(column = "PRIMARY_KEY")
    private String primaryKey;

    @Size(max = 255)
    @CsvBindByName(column = "NAME")
    private String name;

    @CsvBindByName(column = "DESCRIPTION")
    private String description;

    @Size(max = 255)
    @CsvBindByName(column = "UPDATED_TIMESTAMP")
    private String updatedTimestamp;

    @Override
    public String toString() {
        return "csv{" +
                "primary key='" + primaryKey + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", updated Timestamp='" + updatedTimestamp + '\'' +
                '}';
    }
}
