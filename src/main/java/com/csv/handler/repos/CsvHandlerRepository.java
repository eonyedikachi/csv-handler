package com.csv.handler.repos;

import com.csv.handler.entities.CsvEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CsvHandlerRepository extends JpaRepository<CsvEntity, String> {

    boolean existsById(String id);

}
