package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.NotFoundResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.AccountDTO;
import com.hust.medtech.data.dto.PaymentDTO;
import com.hust.medtech.data.entity.*;
import com.hust.medtech.data.entity.response.DataPayment;
import com.hust.medtech.data.request.PaymentRequest;
import com.hust.medtech.repository.*;
import com.hust.medtech.security.JwtTokenProvider;
import com.hust.medtech.service.PaymentService;
import com.mservice.pay.models.AppPayResponse;
import com.mservice.pay.processor.notallinone.AppPay;
import com.mservice.shared.constants.Parameter;
import com.mservice.shared.constants.RequestType;
import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.sharedmodels.PartnerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    TransactionMedicalRepository tranMedRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired

    PaymentRepository mPaymentRepository;
    @Autowired
    IODRepository iodRepository;
    @Autowired
    TransMedDetailRepository transMedDetailRepository;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public BaseResponse getDataPayment(String token) {
        // get data patient
        String patientName = tokenProvider.getUserNameFromJWT(token);
        Account accountParent = accountRepository.findByUsername(patientName);
        Patient patient = patientRepository.findByAccount_AccountId(accountParent.getAccountId());
        // tim phieu kham tu BS  ung voi benh nhan va status =0 : chua thanh toan
        TransactionMedical transactionMedical = tranMedRepository.findByPatientAndStatus(patient, 0);
        try {
            List<Integer> iodByPatient = transMedDetailRepository.getListIodByTransId(transactionMedical.getTransId());
            if (iodByPatient != null) {
                List<ItemOfDept> iodepts = new ArrayList<>();
                int totalPrice = 0;
                for (Integer i : iodByPatient) {
                    ItemOfDept itemOfDept = iodRepository.findByIodId(i);
                    if (itemOfDept != null) {
                        iodepts.add(itemOfDept);
                        totalPrice += itemOfDept.getPrice();
                    } else {
                        LOGGER.info("Khong tim thay IOD tuong ung voi patient");
                    }
                }
//                DataPayment dataPayment = DataPayment.builder()
//                        .transMedId(transactionMedical.getTransId())
//                        .namePatient(patientName)
//                        .itemOfDepts(iodepts)
//                        .totalPrice(totalPrice).build();


                return new OkResponse(null);
            } else {
                return new NotFoundResponse(MsgRespone.DATA_PAYMENT_NOT_FOUNT);
            }
        } catch (Exception e) {
            LOGGER.error("Loi tim du lieu thanh toan: " + e);
        }
        return new BadResponse(MsgRespone.GLOBAL_DATA_NOT_FOUND);

    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public BaseResponse payment(PaymentRequest request,String username) {
        if(request == null || request.getTransId() == 0
                || request.getToken() == null
        || request.getPhoneNumber() == null || request.getPrice() == 0 ){
            return new NotFoundResponse("Đầu vào không hợp lệ");
        }
        String patientName = SecurityContextHolder.getContext().getAuthentication().getName();
        Account accountCheck = accountRepository.findByUsername(patientName);
        if(accountCheck == null || accountCheck.getPatient() == null){
            return new NotFoundResponse("Không tìm thấy thông tin người dùng");
        }
        Optional<TransactionMedical> transactionMedical = tranMedRepository.findById(request.getTransId());

        if(!transactionMedical.isPresent()){
            return new NotFoundResponse("Không tìm thấy thông tin giao dịch");
        }
        Payment payment = new Payment();
        payment.setTotalPrice(request.getPrice());
        payment.setOutpatientCost(0);
        payment.setTransactionMedical(transactionMedical.get());
        payment.setPatientPay(accountCheck.getPatient());

        String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAtSkmOBp4OBfHuHBXBK717mF4sp0j6yrawuq0dvRlaeuOG/6rXazFxmrMWlRRuHJkQtsuC/yqgX7RZfOdzkgqomc/PeSTtz3pbjPkk93y+XTeiisvdZC4wNonGiossSUl8ZvksRBaLope18WCyBioEzNYpz7jjFFCEuzsH5GKjx9StOgMSxESQfxif0Y6RswPqWnx/ydkVEqLCbm37qhRKLjDp7ZWWJMKvq/Gy5VTE0i2afDHb7UPtnkRm3WF/aUsmJzZp83QWXWVVCcMuMzoahBoXFFNm+RSxioTC4+An2oMMn6lXHMtKUUPhMvoyzcPEkc3UnRORfKIbhTAfxipgeV8ZH2jR3WbP8dC6ucdGYlOYXTRM0pEjxPt8TSVX2V7QJgdiqlwxhZU3eZZ3a0ZhyXSId4y0oUS0xAgpJSyAmNKczTEap2jau19tfF71wHPSx/0AIG6l1LMoxN6H/wYjpN/e+GGILLdKkEoBiVVNljgOUpQkRlOzNmAQATHUbYgVcjFcSOr+Jc2zYwOEIe13PnOPof+oGUktR4Ozts86tZFHBFtEhkeeg4e9AihMoTRcmyqllXp6lYmpFb2kH8mB5ll95V12YmhCuEcNpsHeAWYfQsbEKuD52UALuem3ZfkoIlEjL1EXZz3NJZU/ML71LXoHiMmui3RzCL66xMu6FMCAwEAAQ==";
        PartnerInfo devInfo = new PartnerInfo("MOMOLB5S20210516",
                "TNWFx9JWayevKPiB8LyTgODiCSrjstXN",
                "OWepCRJMf4Vo7M0eHlcuRI2uKTrZz9Ce");
        //change the endpoint according to the appropriate processes
        Environment e = Environment.selectEnv("dev",
                Environment.ProcessType.APP_IN_APP);
        e.setPartnerInfo(devInfo);
        long amount = request.getPrice();
        String partnerRefId = String.valueOf(System.currentTimeMillis());
        String partnerTransId = "1561046083186";
        String customerNumber = request.getPhoneNumber();
        String partnerName = "1561046083186";
        String storeId = "";
        String storeName = "1561046083186";
        String appData = request.getToken();
        String description = "Thanh toán viện phí";
        try {
            AppPayResponse appProcessResponse = AppPay.process(e,
                    partnerRefId, partnerTransId, amount, partnerName,
                    storeId, storeName, publicKey, customerNumber, appData,
                    description, Parameter.VERSION, Parameter.APP_PAY_TYPE);
            if (appProcessResponse != null){
                mPaymentRepository.save(payment);
                return new OkResponse("Giao dịch thành công!");
            }else {
                return new NotFoundResponse("Giao dịch thất bại!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new NotFoundResponse("Giao dịch thất bại!");
    }
}
