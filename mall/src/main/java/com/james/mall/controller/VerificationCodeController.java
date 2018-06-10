package com.james.mall.controller;

import com.james.mall.bean.BaseResponseBean;
import com.james.mall.bean.VerificationCode;
import com.james.mall.service.VerificationCodeService;
import com.james.mall.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/verificationCode")
public class VerificationCodeController {
    @Autowired
    VerificationCodeService verificationCodeService;

    @RequestMapping("/insert")
    public BaseResponseBean<String> insertVerificationCode(String phone){
        // TODO 生成验证码,并发给第三方接口
        Random random = new Random();
        int code = random.nextInt(899);
        code = (code+100)*1000+random.nextInt(899);
        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setPhone(phone);
        verificationCode.setCode(code);
        verificationCode.setType(1);
        verificationCode.setValid(true);
        return ResponseUtil.turnData(verificationCodeService.insert(verificationCode)+"");
    }

    @RequestMapping("/update")
    public BaseResponseBean<String> updateVerificationCode(VerificationCode verificationCode){

        return ResponseUtil.turnData(verificationCodeService.update(verificationCode)+"");
    }
    @RequestMapping("/delete")
    public BaseResponseBean<String> deleteVerificationCode(Long id){

        return ResponseUtil.turnData(""+verificationCodeService.delete(id));
    }

    @RequestMapping("/find")
    public BaseResponseBean<VerificationCode> findVerificationCode(Long id){

        return ResponseUtil.turnData(verificationCodeService.findById(id));
    }

    @RequestMapping("/findAll")
    public BaseResponseBean<List<VerificationCode>> findAll(){

        return ResponseUtil.turnData(verificationCodeService.findAll());
    }

}
