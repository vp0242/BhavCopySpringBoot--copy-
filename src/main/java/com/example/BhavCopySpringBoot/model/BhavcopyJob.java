package com.example.BhavCopySpringBoot.model;

import java.time.LocalDateTime;
import java.util.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.*;

@Entity
@Table(name = "jobq")
public class BhavcopyJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @Column(name = "reqid")
    public UUID reqid;

    @Column(name = "addedat")
    public LocalDateTime addedat;

    @Column(name = "startedat")
    public LocalDateTime startedat;

    @Column(name = "endedat")
    public LocalDateTime endedat;

    @Column(name = "duration")
    public Long duration;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "params", columnDefinition = "jsonb")
    public JsonNode params;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "response", columnDefinition = "jsonb")
    public JsonNode response;

    @Column(name = "status")
    public String status;


    public String getStatus(){
        return status;
    }

}
