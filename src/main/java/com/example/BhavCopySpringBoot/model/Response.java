package com.example.BhavCopySpringBoot.model;


import java.util.*;

public class Response {
     String status;
     String message;
     Map<String, Object> response = new LinkedHashMap<>();
    
    public  Response(String status,String message,Object data){
        this.status=status;
        this.message=message;  
        response.put("status", status);
        response.put("message",message);
        response.put("data", data);
        
    }


     public Map<String, Object> getResponse() {
         return response;
     }
     
}
