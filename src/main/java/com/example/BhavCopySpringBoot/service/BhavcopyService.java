package com.example.BhavCopySpringBoot.service;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.example.BhavCopySpringBoot.model.BhavCopyReq;
import com.example.BhavCopySpringBoot.model.BhavcopyJob;
import com.example.BhavCopySpringBoot.model.BhavcopyModel;
import com.example.BhavCopySpringBoot.repository.BhavcopyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.BhavCopySpringBoot.repository.BhavcopyJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BhavcopyService {

    @Autowired
    private BhavcopyRepository bhavcopyRepository;

    @Autowired
    private BhavcopyJobRepository bhavcopyJobRepository;

    @Autowired
    private BhavcopyJobService bhavcopyJobService;
    private static final Logger logger = LoggerFactory.getLogger(BhavcopyService.class);

    public BhavcopyService(BhavcopyJobService bhavcopyJobService) {
        this.bhavcopyJobService = bhavcopyJobService;

    }

    public List<BhavcopyModel> storeDataIntoDb(MultipartFile file) {

        List<BhavcopyModel> datalist = new ArrayList<>();
        try {
            logger.debug("storeDataIntoDb started");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 13) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }
                try {
                    BhavcopyModel copyData = new BhavcopyModel(
                            fields[0].trim(),
                            fields[1].trim(),
                            parseIntoDouble(fields[2].trim()),
                            parseIntoDouble(fields[3].trim()),
                            parseIntoDouble(fields[4].trim()),
                            parseIntoDouble(fields[5].trim()),
                            parseIntoDouble(fields[6].trim()),
                            parseIntoDouble(fields[7].trim()),
                            parseIntoLong(fields[8].trim()),
                            parseIntoDouble(fields[9].trim()),
                            fields[10].trim(),
                            parseIntoInt(fields[11].trim()),
                            fields[12].trim());

                    datalist.add(copyData);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            reader.close();
            if (!datalist.isEmpty()) {
                bhavcopyRepository.saveAll(datalist);
                bhavcopyRepository.flush();
            }
        } catch (Exception e) {
            System.out.println("Error While Reading Data From BhavCopy CSV..");
        }
        return datalist;
    }

    public Integer parseIntoInt(String data) {
        logger.debug("parseIntoInt started");

        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            int value = Integer.parseInt(data);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public Double parseIntoDouble(String data) {
        logger.debug("parseIntoDouble started");

        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            Double value = Double.parseDouble(data);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public Long parseIntoLong(String data) {
        logger.debug("parseIntoDouble started");

        if (data == null || data.isEmpty()) {
            return null;
        }
        try {
            Long value = Long.parseLong(data);
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public String getOutputForBhavcopyOperations(BhavCopyReq req) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String reqJson = mapper.writeValueAsString(req);
        UUID reqid = bhavcopyJobService.createJob(reqJson);

        String op = req.getOperation();
        String ip = req.getInputParam();

        if (req.getOperation().isEmpty() || req.getOperation() == null) {
            throw new Exception("Invalid Operation");
        }

        if (req.getInputParam().isEmpty() || req.getInputParam() == null) {
            throw new Exception("Invalid IputParam");
        }

        Object result = null;
        Map<String, Object> list = new HashMap<>();
        switch (op) {
            case "RecordBySymbol":
                result = bhavcopyRepository.findBySymbolFunc(ip.trim());
                break;
            case "CountBySeries":
                result = bhavcopyRepository.getCountBySeriesFunc(ip.trim());
                list.put("count", result);
                result = list;
                break;
            case "SymbolWithGain":
                try {
                    Double input = Double.parseDouble(ip.trim());
                    result = bhavcopyRepository.getSymbolWithGainFunc(input);
                } catch (Exception e) {
                    throw new Exception("Unable to Parse String");
                }
                break;
            case "SymbolWithTopBot":
                try {
                    Double input = Double.parseDouble(ip.trim());
                    result = bhavcopyRepository.getSymbolWithTopBotFunc(input);
                } catch (Exception e) {
                    throw new Exception("Unable to Parse String");
                }
                break;
            case "StdWithSeries":
                result = bhavcopyRepository.getSTDevWthSeriesFunc(ip.trim());
                list.put("STDDevCalculation", String.format("%.4f", result));
                result = list;
                break;
            case "SymbolWithMaxGain":
                try {
                    Integer input = Integer.parseInt(ip.trim());
                    result = bhavcopyRepository.getSymbolWithMaxGainFunc(input);
                } catch (Exception e) {
                    throw new Exception("Unable to Parse String");
                }
                break;
            case "SymbolWithMinGain":
                try {
                    Integer input = Integer.parseInt(ip.trim());
                    result = bhavcopyRepository.getSymbolWithMinGainFunc(input);
                } catch (Exception e) {
                    throw new Exception("Unable to Parse String");
                }
                break;
            case "SymbolWithMaxTrades":
                try {
                    Integer input = Integer.parseInt(ip.trim());
                    result = bhavcopyRepository.getSymbolWithMaxTradedFunc(input);
                } catch (Exception e) {
                    throw new Exception("Unable to Parse String");
                }
                break;
            case "SymbolWithMinTrades":
                try {
                    Integer input = Integer.parseInt(ip.trim());
                    result = bhavcopyRepository.getSymbolWithMinTradedFunc(input);
                } catch (Exception e) {
                    throw new Exception("Unable to Parse String");
                }
                break;
            case "SymbolWithMaxMinTradesBySeries":
                String highest = bhavcopyRepository.getSymbolWithMaxTradedBySeriesFunc(ip.trim());
                String lowest = bhavcopyRepository.getSymbolWithMinTradedBySeriesFunc(ip.trim());
                list.put("Highest", highest);
                list.put("Lowest", lowest);
                result = list;
                break;
            default:
                throw new Exception("Invalid Operation: " + op);
        }
        
        String resultJson = mapper.writeValueAsString(result);
        bhavcopyJobService.processJob(reqid, resultJson);
        
        String reqStr = reqid.toString();
        return reqStr;
    }

    public List<BhavcopyJob> getJobRecord(String reqid) throws Exception {
        if (reqid.isEmpty()) {
            throw new Exception("Null reqid");
        }
        UUID id = UUID.fromString(reqid);
        List<BhavcopyJob> result = bhavcopyJobRepository.findByReqid(id);
        if (result.isEmpty()) {
            throw new Exception("reqid is not present in database.");
        }
        for (BhavcopyJob job : result) {
            if ("pending".equalsIgnoreCase(job.getStatus())) {
                throw new Exception("Request is pending, try later.");
            }

        }
        return result;

    }

}
