package com.james.mall.service.impl;

import com.james.mall.bean.VerificationCode;
import com.james.mall.mapper.VerificationCodeMapper;
import com.james.mall.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    VerificationCodeMapper verificationCodeMapper;

    @Override
    public int insert(VerificationCode verificationCode) {
        return verificationCodeMapper.insert(verificationCode);
    }

    @Override
    public int update(VerificationCode verificationCode) {
        return verificationCodeMapper.update(verificationCode);
    }

    @Override
    public int delete(Long id) {
        return verificationCodeMapper.delete(id);
    }

    @Override
    public VerificationCode findById(Long id) {
        return verificationCodeMapper.findById(id);
    }

    @Override
    public List<VerificationCode> findAll() {
        return verificationCodeMapper.findAll();
    }

    @Override
    public VerificationCode findByPhoneOrderByCreatedDesc(String phone) {
        return verificationCodeMapper.findByPhoneOrderByCreatedDesc(phone);
    }
}
