package com.alura.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IDataConverter{
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public <T> T convert(String json, Class<T> classType) {
        try{
            return objectMapper.readValue(json, classType);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
