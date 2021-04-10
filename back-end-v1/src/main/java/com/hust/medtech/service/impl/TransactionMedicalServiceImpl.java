package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.TransactionMedicalDTO;
import com.hust.medtech.data.entity.Account;
import com.hust.medtech.data.entity.TransactionMedical;
import com.hust.medtech.data.entity.TransactionMedicalDetail;
import com.hust.medtech.data.entity.key.TransactionMedicalDetailID;
import com.hust.medtech.repository.AccountRepository;
import com.hust.medtech.repository.IODRepository;
import com.hust.medtech.repository.TransMedDetailRepository;
import com.hust.medtech.repository.TransactionMedicalRepository;
import com.hust.medtech.security.JwtTokenProvider;
import com.hust.medtech.service.TransactionMedicalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionMedicalServiceImpl implements TransactionMedicalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionMedicalServiceImpl.class);
    @Autowired
    TransactionMedicalRepository medicalRepository;

    @Autowired
    TransMedDetailRepository medicalDetailRepository;
    @Autowired
    IODRepository iodRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JwtTokenProvider tokenProvider;

    @Override
    public BaseResponse addTransMedical(TransactionMedicalDTO transMedicalDTO, String token) {
        if (transMedicalDTO != null && transMedicalDTO.getItemOfDepts() != null) {
            String accNameByToken = tokenProvider.getUserNameFromJWT(token);
            Account accCheck = accountRepository.findByUsername(accNameByToken);
            LOGGER.info("Nguoi dung " + accNameByToken + " vua tao phieu kham");
            Account account = Account.builder()
                    .accountId(accCheck.getAccountId()).build();
            TransactionMedical transactionMedical = TransactionMedical.builder()
                    .createDate(new Date())
                    .account(account).build();
            TransactionMedical medical = medicalRepository.save(transactionMedical);

            List<TransactionMedicalDetail> details = new ArrayList<>();
            for (Integer i : transMedicalDTO.getItemOfDepts()) {
                details.add(new TransactionMedicalDetail(new TransactionMedicalDetailID(medical.getTransId(), i)));
            }
            medicalDetailRepository.saveAll(details);

            return new OkResponse(MsgRespone.TAO_PHIEU_KHAM_THANH_CONG);
        } else {
            return new BadResponse(MsgRespone.TAO_PHIEU_KHAM_THAT_BAI);
        }
    }


}
