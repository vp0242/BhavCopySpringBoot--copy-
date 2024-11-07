package com.example.BhavCopySpringBoot.repository;

import com.example.BhavCopySpringBoot.model.BhavcopyJob;


import java.util.*;
import java.time.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BhavcopyJobRepository extends JpaRepository<BhavcopyJob, Long> {

    @Query("SELECT b FROM BhavcopyJob b WHERE b.reqid = ?1")
    List<BhavcopyJob> findByReqid(UUID reqid);

    @Query(value = "INSERT INTO jobq (reqid, params, addedat, startedat, status) VALUES (?1, CAST(?2 AS jsonb), ?3, ?4, 'pending')RETURNING reqid", nativeQuery = true)
    UUID createJob(UUID reqid, String params, LocalDateTime addedat, LocalDateTime startedat);
    

    @Query(value="UPDATE jobq  SET status = 'done', endedat = :endedat, duration = :duration, response =  CAST(:response AS jsonb) WHERE reqid = :reqid",nativeQuery=true)
    Integer updateJobDetailsByReqid(@Param("reqid") UUID reqid, @Param("endedat") LocalDateTime endedat, @Param("duration") long duration, @Param("response") String response);

}
