package com.zsy.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManualJacksonSerializationService {

    private final ObjectMapper objectMapper;

    public ManualJacksonSerializationService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String serializeObject(Object object) {
        try{
            return objectMapper.writeValueAsString(object);
        }catch (JsonProcessingException e){
            return null;
        }
    }

    public <T> T deserializeObject(String json, Class<T> valueType){
        try{
            return objectMapper.readValue(json, valueType);
        }catch (JsonProcessingException e){
            return null;
        }
    }

    //返回json数据响应
    public void writeJsonToResponse(Object data, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        try{
            objectMapper.writeValue(response.getOutputStream(),data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
