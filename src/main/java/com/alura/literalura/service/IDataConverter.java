package com.alura.literalura.service;

public interface IDataConverter {
    <T> T convert(String json, Class<T> classType);
}
