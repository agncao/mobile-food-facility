package com.jm.resume.food.facility.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

public abstract class ClassPathFileReader {

    public static InputStream getCsvFile(String cvsFileName) {
        try {
            return new ClassPathResource(String.format("csv/%s",cvsFileName)).getInputStream();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
