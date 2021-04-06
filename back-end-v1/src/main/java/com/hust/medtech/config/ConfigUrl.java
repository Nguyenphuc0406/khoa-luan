package com.hust.medtech.config;

public interface ConfigUrl {
    //
    String URL_API = "/api";
    String URL_LOGIN = "/login";
    //patient
    String URL_PATIENT = URL_API + "/patient";
    String URL_PATIENT_FORM = URL_PATIENT + "/form";

    //doctor
    String URL_DOCTOR = URL_API + "/doctor";

    //doctor
    String URL_HOSPITAL = URL_API + "/hospital";

    //dept
    String URL_DEPT = URL_API + "/dept";

    //IOD
    String URL_ITEM_OF_DEPT = URL_API + "/item-of-dept";
    String URL_GET_IOD_BY_DEPT = URL_ITEM_OF_DEPT + "/get-by-dept";

}
