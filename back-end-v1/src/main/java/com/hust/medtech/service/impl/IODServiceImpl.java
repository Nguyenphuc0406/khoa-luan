package com.hust.medtech.service.impl;

import com.hust.medtech.base.response.BadResponse;
import com.hust.medtech.base.response.BaseResponse;
import com.hust.medtech.base.response.NotFoundResponse;
import com.hust.medtech.base.response.OkResponse;
import com.hust.medtech.config.MsgRespone;
import com.hust.medtech.data.dto.DeptDTO;
import com.hust.medtech.data.dto.ItemOfDeptDTO;
import com.hust.medtech.data.entity.Dept;

import com.hust.medtech.data.entity.ItemOfDept;
import com.hust.medtech.data.request.DeptIdRequest;
import com.hust.medtech.data.request.FindIODName;
import com.hust.medtech.repository.DeptRepository;
import com.hust.medtech.repository.IODRepository;
import com.hust.medtech.service.IODService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IODServiceImpl implements IODService {
    public static final Logger LOGGER = LoggerFactory.getLogger(IODServiceImpl.class);

    @Autowired
    IODRepository iodRepository;

    @Autowired
    DeptRepository deptRepository;


    @Override
    public BaseResponse addItemOfDept(ItemOfDeptDTO itemOfDeptDTO) {
        if (itemOfDeptDTO != null && itemOfDeptDTO.getDept().getDeptId() != null) {
            Dept deptCheck = deptRepository.findByDeptId(itemOfDeptDTO.getDept().getDeptId());
            if (deptCheck != null) {
                Dept dept = Dept.builder()
                        .deptId(itemOfDeptDTO.getDept().getDeptId()).build();
                ItemOfDept itemOfDept = ItemOfDept.builder()
                        .name(itemOfDeptDTO.getName())
                        .consultingRoom(itemOfDeptDTO.getConsultingRoom())
                        .price(itemOfDeptDTO.getPrice())
                        .description(itemOfDeptDTO.getDescription())
                        .code(itemOfDeptDTO.getCode())
                        .dept(dept).build();
                iodRepository.save(itemOfDept);
                return new OkResponse(MsgRespone.IOD_ADD_SUCCESS);
            } else {
                return new NotFoundResponse(MsgRespone.DEPT_NOT_FOUND);
            }
        } else {
            return new BadResponse(MsgRespone.IOD_INPUT_INVALID);
        }


    }

    @Override
    public BaseResponse getAllIodByDeptIdWithHospital(DeptIdRequest deptId, String token) {

        Dept dept = deptRepository.findByDeptId(deptId.getDeptId());
        if (deptId != null || dept == null) {
            List<ItemOfDept> itemOfDepts = iodRepository.findByDept(dept);
            return new OkResponse(itemOfDepts);
        } else {
            return new NotFoundResponse(MsgRespone.GLOBAL_INPUT_INVALID);
        }

    }

    @Override
    public BaseResponse getAllIOD() {
        List<ItemOfDept> itemOfDepts = iodRepository.findAll();
        List<ItemOfDeptDTO> itemOfDeptDTOS = new ArrayList<>();
        for (ItemOfDept item : itemOfDepts) {
            DeptDTO deptDTO = DeptDTO.builder()
                    .deptId(item.getDept().getDeptId()).build();
            ItemOfDeptDTO dto = ItemOfDeptDTO.builder()
                    .iodId(item.getIodId())
                    .name(item.getName())
                    .consultingRoom(item.getConsultingRoom())
                    .price(item.getPrice())
                    .description(item.getDescription())
                    .dept(deptDTO)
                    .code(item.getCode()).build();
            itemOfDeptDTOS.add(dto);
        }

        return new OkResponse(itemOfDeptDTOS);
    }

    @Override
    public BaseResponse getIodById(Integer iodId) {
        if (iodId != null) {
            ItemOfDept itemOfDept = iodRepository.findByIodId(iodId);
            if (itemOfDept != null) {
                LOGGER.error("Tim kiem IOD voi id: " + iodId + ", ten: " + itemOfDept.getName());
                return new OkResponse(itemOfDept);
            } else {
                LOGGER.error("Khong tim thay IOD voi id: " + iodId);
                return new NotFoundResponse(MsgRespone.IOD_NOT_FOUND);
            }
        } else {
            LOGGER.error("Du lieu IOD truyen vao bi loi: " + iodId);
            return new BadResponse(MsgRespone.GLOBAL_INPUT_INVALID);
        }
    }

    @Override
    public BaseResponse findIodByNam(FindIODName findIODName) {
        String name = findIODName.getNameIod();
        if (name != null) {
            List<ItemOfDept> iod = iodRepository.findByNameContaining(name);
            if (!iod.isEmpty()) {
                LOGGER.info("Truy van tim IOD voi name: " + name);
                return new OkResponse(iod);

            } else {
                return new NotFoundResponse(MsgRespone.IOD_NOT_FOUND);
            }
        } else {
            LOGGER.error("Du lieu truyen vao loi voi name: " + name);
            return new NotFoundResponse(MsgRespone.GLOBAL_INPUT_INVALID);
        }

    }
}
