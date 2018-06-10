package com.james.mall.mapper;

import com.james.mall.bean.VerificationCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VerificationCodeMapper {

    int insert(VerificationCode verificationCode);

    int update(VerificationCode verificationCode);

    int delete(@Param("id") Long id);

    VerificationCode findById(@Param("id") Long id);

    VerificationCode findByPhoneOrderByCreatedDesc(@Param("phone")String phone);

    List<VerificationCode> findAll();
}
