package com.example.BhavCopySpringBoot.model;

public class BhavCopyReq {
    
    String operation ;
    String inputParam ;

    BhavCopyReq(String operation,String inputParam){
        this.operation =operation;
        this.inputParam =inputParam;
    }

    public String getOperation(){
        return operation;
    }

    public String getInputParam(){
        return inputParam;
    }
}
