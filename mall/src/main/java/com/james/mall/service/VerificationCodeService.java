package com.james.mall.service;

import com.james.mall.bean.VerificationCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VerificationCodeService {

    int insert(VerificationCode verificationCode);

    int update(VerificationCode verificationCode);

    int delete(Long id);

    VerificationCode findById(Long id);

    VerificationCode findByPhoneOrderByCreatedDesc(String phone);

    List<VerificationCode> findAll();
}
