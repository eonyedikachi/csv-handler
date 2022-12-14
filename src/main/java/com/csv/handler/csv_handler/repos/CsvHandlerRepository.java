package com.csv.handler.csv_handler.repos;

import com.csv.handler.csv_handler.entities.CsvEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CsvHandlerRepository extends JpaRepository<CsvEntity, String> {

    boolean existsById(String id);

}
