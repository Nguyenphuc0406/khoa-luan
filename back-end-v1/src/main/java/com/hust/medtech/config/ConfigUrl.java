package com.hust.medtech.config;

public interface ConfigUrl {
    //
    String URL_API = "/api";
    String URL_LOGIN = "/login";
    //patient
    String URL_PATIENT = URL_API + "/patient";
    String URL_PATIENT_FORM = URL_PATIENT + "/form";
    String URL_DATA_PAYMENT = URL_PATIENT + "/payment";
    String URL_PAYMENT_HIST = URL_DATA_PAYMENT + "/histories";


    //doctor
    String URL_DOCTOR = URL_API + "/doctor";

    //doctor
    String URL_HOSPITAL = URL_API + "/hospital";

    //dept
    String URL_DEPT = URL_API + "/dept";
    String URL_DEPT_ALL = URL_API + "/getDept";

    //IOD
    String URL_ITEM_OF_DEPT = URL_DOCTOR + "/item-of-dept";
    String URL_GET_IOD_BY_DEPT = URL_DOCTOR + "/get-by-dept";

    // phieu kham benh
    String URL_PHIEU_KHAM = URL_API + "/phieu-kham";
    String URL_LICH_KHAM = URL_PATIENT + "/lich-kham";
    //    String URL_GET_IOD_BY_DEPT =  + "/get-by-dept";

    //chi dinh kham
    String URL_CHI_DINH_KHAM = URL_DOCTOR + "/chi-dinh-kham";
    String URL_CHI_DINH_KHAM_GET_ID = URL_PATIENT + "/getTotalPayment";
}
