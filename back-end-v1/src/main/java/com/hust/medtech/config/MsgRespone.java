package com.hust.medtech.config;

public interface MsgRespone {
    //global
    String GLOBAL_TRUNG_DU_LIEU = "Trung du lieu";
    String GLOBAL_INPUT_INVALID = "Input invalid";
    String GLOBAL_DATA_NOT_FOUND = "Data not found";

    //patient
    String PATIENT_NOT_FOUND = "Patient not found!";
    String PATIENT_ADD_SUCCESS = "Add patient success";
    String PATIENT_ADD_ERROR = "Add patient error";
    String PATIENT_INPUT_INVALID = "Patient input invalid";

    //doctor
    String DOCTOR_ADD_SUCCESS = "Add doctor success";
    String DOCTOR_ADD_ERROR = "Add doctor error";
    String DOCTOR_INPUT_INVALID = "Doctor input invalid";

    //hospital
    String HOSPITAL_ADD_SUCCESS = "Add hospital success";
    String HOSPITAL_ADD_ERROR = "Add hospital error";
    String HOSPITAL_INPUT_INVALID = "Hospital input invalid";
    String HOSPITAL_NOT_FOUND = "Hospital not found!";

    //dept
    String DEPT_ADD_SUCCESS = "Add dept success";
    String DEPT_ADD_ERROR = "Add dept error";
    String DEPT_INPUT_INVALID = "Dept input invalid";
    String DEPT_NOT_FOUND = "Dept not found!";

    //ItemOfDept
    String IOD_ADD_SUCCESS = "ItemOfDept dept success";
    String IOD_ADD_ERROR = "Add ItemOfDept error";
    String IOD_INPUT_INVALID = "ItemOfDept input invalid";
    String IOD_NOT_FOUND = "ItemOfDept not found!";

    //trans medical
    String TAO_PHIEU_KHAM_THANH_CONG = "Tao phieu kham thanh cong";
    String TAO_PHIEU_KHAM_THAT_BAI = "Tao phieu kham that bai";

    //payment
    String DATA_PAYMENT_NOT_FOUNT = "Khong tim thay du lieu phieu kham";
}
