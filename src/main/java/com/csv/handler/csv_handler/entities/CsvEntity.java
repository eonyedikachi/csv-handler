package com.csv.handler.csv_handler.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class CsvEntity {

    @Id
    @Column(name="primary_key" ,nullable = false, updatable = false)
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @LastModifiedDate
    @Column(name="updated_timestamp")
    private LocalDateTime updatedTimestamp;

}
