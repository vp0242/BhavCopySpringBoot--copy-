package com.example.BhavCopySpringBoot.service;

import java.time.*;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.BhavCopySpringBoot.repository.BhavcopyJobRepository;

import org.springframework.stereotype.Service;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class BhavcopyJobService {

    @Autowired
    private BhavcopyJobRepository bhavcopyJobRepository;

    private final ThreadPoolExecutor executor;

    public LocalDateTime addedDate;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public Duration interval;
    public UUID reqid;

    // private ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    // public BhavcopyService() {
    // taskExecutor.setCorePoolSize(5);
    // taskExecutor.setMaxPoolSize(10);
    // taskExecutor.initialize();
    // }

    BhavcopyJobService() {

        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }

    public UUID createJob(String params) {
        reqid = UUID.randomUUID();
        // String reqStr = reqid.toString();
        this.addedDate = LocalDateTime.now();

        int sleepTime = ThreadLocalRandom.current().nextInt(10, 20);
        try {
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.startDate = LocalDateTime.now();
        reqid = bhavcopyJobRepository.createJob(reqid, params, addedDate, startDate);
        // String reqStr = reqid.toString();
        return reqid;

    }

    public void processJob(UUID reqid, String response) {
        
        int sleepTime = ThreadLocalRandom.current().nextInt(10, 20);
        try {
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.submit(() -> {
            try {

                endDate = LocalDateTime.now();
                if (startDate != null && endDate != null) {
                    this.interval = Duration.between(startDate, endDate);
                } else {
                    this.interval = Duration.ZERO;
                }
                Long durationInSeconds = interval.toSeconds();
                bhavcopyJobRepository.updateJobDetailsByReqid(reqid, endDate, durationInSeconds, response);
                System.out.println("Job update completed for reqid: " + reqid);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }

        });
    }

}
