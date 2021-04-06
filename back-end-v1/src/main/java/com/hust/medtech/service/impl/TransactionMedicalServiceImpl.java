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
import com.hust.medtech.service.TransactionMedicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionMedicalServiceImpl implements TransactionMedicalService {
    @Autowired
    TransactionMedicalRepository medicalRepository;

    @Autowired
    TransMedDetailRepository medicalDetailRepository;

    @Autowired
    IODRepository iodRepository;
    @Autowired
    AccountRepository accountRepository;

    @Override
    public BaseResponse addTransMedical(TransactionMedicalDTO transMedicalDTO) {
        if (transMedicalDTO != null && transMedicalDTO.getItemOfDepts() != null) {
            Account account = Account.builder()
                    .accountId(transMedicalDTO.getAccount().getAccountId()).build();
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
