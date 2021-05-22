package com.hust.medtech;

import com.google.gson.Gson;
import com.hust.medtech.data.dto.DoctorDTO;
import com.hust.medtech.data.entity.Account;
import com.hust.medtech.repository.AccountRepository;
import com.hust.medtech.service.DoctorService;
import com.hust.medtech.service.impl.DoctorServiceImpl;
import com.hust.medtech.utils.Encoder;
import com.mservice.pay.models.AppPayResponse;
import com.mservice.pay.models.POSPayResponse;
import com.mservice.pay.processor.notallinone.AppPay;
import com.mservice.pay.processor.notallinone.POSPay;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class MedtechApplication {
    public static final String CUSTOMER_NUMBER = "customerNumber";
    public static final String PARTNER_REF_ID = "partnerRefId";
    public static final String PARTNER_TRANS_ID = "partnerTransId";
    public static final String USERNAME = "userName";
    public static final String PARTNER_NAME = "partnerName";
    public static final String STORE_ID = "storeId";
    public static final String STORE_NAME = "storeName";

    public static String PARTNER_CODE = "partnerCode";
    public static String ACCESS_KEY = "accessKey";
    public static String REQUEST_ID = "requestId";
    public static String AMOUNT = "amount";
    public static String ORDER_ID = "orderId";
    public static String ORDER_INFO = "orderInfo";
    public static String RETURN_URL = "returnUrl";
    public static String NOTIFY_URL = "notifyUrl";
    public static String REQUEST_TYPE = "requestType";
    public static String EXTRA_DATA = "extraData";
    public static String BANK_CODE = "bankCode";
    public static String TRANS_ID = "transId";
    public static String PAY_TRANS_ID = "transid";
    public static String MESSAGE = "message";
    public static String LOCAL_MESSAGE = "localMessage";
    public static String DESCRIPTION = "description";
    public static String PAY_URL = "payUrl";
    public static String DEEP_LINK = "deeplink";
    public static String QR_CODE = "qrCode";
    public static String ERROR_CODE = "errorCode";
    public static String STATUS = "status";
    public static String PAY_TYPE = "payType";
    public static String TRANS_TYPE = "transType";
    public static String ORDER_TYPE = "orderType";
    public static String MOMO_TRANS_ID = "momoTransId";
    public static String PAYMENT_CODE = "paymentCode";
    public static String DATE = "responseTime";

    public static Double VERSION = 2.0;
    public static Integer APP_PAY_TYPE = 3;


    public static void main(String[] args) {
        SpringApplication.run(MedtechApplication.class, args);


    }

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }

    public static String generateRSA(String phoneNumber,
                                     String billId,
                                     String transId,
                                     String username,
                                     String partnerCode,
                                     long amount,
                                     String publicKey) throws Exception {
        // current version of Parameter key name is 2.0
        Map<String, Object> rawData = new HashMap<String, Object>();
        rawData.put(CUSTOMER_NUMBER, phoneNumber);
        rawData.put(PARTNER_REF_ID, billId);
        rawData.put(PARTNER_TRANS_ID, transId);
        rawData.put(USERNAME, username);
        rawData.put(PARTNER_CODE, partnerCode);
        rawData.put(AMOUNT, amount);

        Gson gson = new Gson();
        String jsonStr = gson.toJson(rawData);
        byte[] testByte = jsonStr.getBytes(StandardCharsets.UTF_8);
        String hashRSA = Encoder.encryptRSA(testByte, publicKey);

        return hashRSA;
    }
}
