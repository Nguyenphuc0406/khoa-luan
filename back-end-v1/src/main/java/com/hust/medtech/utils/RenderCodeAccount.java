package com.hust.medtech.utils;

import java.util.regex.Pattern;

public class RenderCodeAccount {
    public String renCode(String role, String codeLatest) {
        String codePatient = "BN";
        String codeDoctor = "BS";
        Pattern pattern = Pattern.compile("^[BN|BS]\\d{3}");


        if (role.equals("ROLE_ADMIN")){

        } else if (role.equals("ROLE_USER")){

        }
        return null;
    }
}
