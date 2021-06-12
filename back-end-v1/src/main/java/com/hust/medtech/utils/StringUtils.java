package com.hust.medtech.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Random;

public class StringUtils {
    private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String ALPHA_NUMERIC = "0123456789";

    public static String randomCode() {
        String str = "";
        String strNum = "";

        for (int i = 0; i < 4; i++) {
            int index = getRandomNumberInRange(0, ALPHA_STRING.length() - 1);
            int indexNumber = getRandomNumberInRange(0, ALPHA_NUMERIC.length() - 1);
            try {
                str += ALPHA_STRING.subSequence(index, index + 1);
                strNum += ALPHA_NUMERIC.subSequence(indexNumber, indexNumber + 1);
            } catch (Exception e) {
                e.printStackTrace();
                randomCode();
            }

        }

        return str + strNum;
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static  <T> String getBody(final T user) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(user);
    }

    public static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

}
