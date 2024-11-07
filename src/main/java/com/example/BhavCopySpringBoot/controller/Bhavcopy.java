package com.example.BhavCopySpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.BhavCopySpringBoot.service.BhavcopyService;
import java.util.*;

import com.example.BhavCopySpringBoot.model.BhavCopyReq;
import com.example.BhavCopySpringBoot.model.BhavcopyJob;
import com.example.BhavCopySpringBoot.model.BhavcopyModel;
import com.example.BhavCopySpringBoot.model.Response;
import com.example.BhavCopySpringBoot.model.requestJob;

@RestController
@RequestMapping("/api/bhavcopy")
public class Bhavcopy {

    @Autowired
    private BhavcopyService bhavcopyService;
    Map<String, Object> response;

    @PostMapping("/uploadcsvfile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile csvfile) {

        try {
            List<BhavcopyModel> list = bhavcopyService.storeDataIntoDb(csvfile);
            if (list == null || list.isEmpty()) {
                Response res = new Response("error", "Error occured While Uploading File", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "File Uploaded SuccessFully", "");
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response res = new Response("error", "Error occured While Uploading File", null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @PostMapping("/bhavcopyOperations")
    public ResponseEntity<?> uploadFile(@RequestBody BhavCopyReq request) {

        try {

            Object output = bhavcopyService.getOutputForBhavcopyOperations(request);
            if (output == null) {
                Response res = new Response("success", "No Records Found fot Given Operation", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "Records Fetched SuccessFully", output);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response res = new Response("error", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @GetMapping("/findRecordByReqid")
    public ResponseEntity<?> uploadFile(@RequestBody requestJob reqid) {

        try {
            List<BhavcopyJob> output = bhavcopyService.getJobRecord(reqid.getReqid().trim());
            if (output == null) {
                Response res = new Response("success", "No Records Found fot Given Operation", "");
                response = res.getResponse();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            Response res = new Response("success", "Records Fetched SuccessFully", output);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response res = new Response("error", e.getMessage(), null);
            response = res.getResponse();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }

    }

}
