package com.james.mall.controller;


import com.james.mall.bean.*;
import com.james.mall.service.UserService;
import com.james.mall.service.VerificationCodeService;
import com.james.mall.util.JwtTokenUtil;
import com.james.mall.util.Pair;
import com.james.mall.util.ResponseUtil;
import com.james.mall.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 登录相关
 */
@RestController
@RequestMapping("/auth")
public class LoginRelatedController {
    private static final String CODE_METHOD_FROM_REGISTER = "registerAccount";
    private static final String CODE_METHOD_FROM_UPDATE_PASSWORD = "updatePassword";
    private static final int CODE_TYPE_DEFAULT = 0;
    private static final int CODE_TYPE_REGISTER = 1;
    private static final int CODE_TYPE_UPDATE_PASSWORD = 2;
    private static String TEST_PHONE = "13312345678";
    private static int TEST_CODE = 123456;


    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;
    @Autowired
    VerificationCodeService verificationCodeService;

    @GetMapping("/smscode")
    @ResponseBody
    public BaseResponseBean<String> sendVerificationCode(@RequestParam String method, @RequestParam String phone) {
        int type = CODE_TYPE_DEFAULT;
        if (CODE_METHOD_FROM_REGISTER.equals(method)) {
            type = CODE_TYPE_REGISTER;
        } else if (CODE_METHOD_FROM_UPDATE_PASSWORD.equals(method)) {
            type = CODE_TYPE_UPDATE_PASSWORD;
        }
        if (type == CODE_TYPE_DEFAULT) return ResponseUtil.turnFailedData();

        if (StringUtil.isEmpty(phone) || phone.length() != 11) {
            return ResponseUtil.turnFailedData();
        }
        if (!StringUtil.isPhone(phone)) return ResponseUtil.turnFailedData();

        // TODO 生成验证码,并发给第三方接口
        Random random = new Random();
        int code = random.nextInt(899);
        code = (code + 100) * 1000 + random.nextInt(899);
        if (TEST_PHONE.equals(phone)) code = TEST_CODE;

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setType(type);
        verificationCode.setPhone(phone);
        verificationCode.setCode(code);
        verificationCode.setValid(true);
        verificationCodeService.insert(verificationCode);
        return ResponseUtil.turnData("");
    }


    @PostMapping("/register")
    @ResponseBody
    public BaseResponseBean<String> doRegisterByUsername(String username, String password,
                                                         String platform, String deviceId,
                                                         @RequestParam(value = "code", defaultValue = "0") int code,
                                                         String phoneModel, String appVersion, String promoCode) {

        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password) || code == 0
                || username.length() != 11) {
            return ResponseUtil.turnFailedData("用户名或密码为空:" + username + ",psd:" + password + ",code:" + code);
        }
        if (!StringUtil.isPhone(username)) return ResponseUtil.turnFailedData("手机号码格式不对");
        User userDto = userService.findByUsername(username);
        if (userDto != null && userDto.getUsername() != null) return ResponseUtil.turnFailedData("已存在该用户名");

        VerificationCode verificationCode = verificationCodeService.findByPhoneOrderByCreatedDesc(username);
        if (verificationCode == null || verificationCode.getCode() == null)
            return ResponseUtil.turnFailedData("验证码未发送");
        System.out.println(verificationCode.isValid() + "," + verificationCode.getCreated() + "," + verificationCode.getCode());
        if (!verificationCode.isValid()) {
            return ResponseUtil.turnFailedData("验证码已使用");
        }
        if (!StringUtil.isVerificationTime(verificationCode.getCreated())) {
            return ResponseUtil.turnFailedData("验证码已过期");
        }

        if (code != verificationCode.getCode()) {
            return ResponseUtil.turnFailedData("验证码错误");
        }

        Pair<String, String> passwordPair = StringUtil.encryptPassword(password);
        User user = new User();
        user.setUsername(username);
        user.setNickname(username);
        user.setPhone(username);
        user.setPassword(passwordPair.getFirst());
        user.setSalt(passwordPair.getSecond());
        user.setPlatform(platform);
        user.setDeviceId(deviceId);
        user.setPhoneModel(phoneModel);
        user.setVersion(appVersion);
        user.setPromoCode(promoCode);
        user.setRegisterTime(StringUtil.toSqlDate(System.currentTimeMillis()));
        user.setFirstLogin(true);
        userService.insert(user);

        verificationCode.setValid(false);
        verificationCodeService.update(verificationCode);

        //TODO 判断邀请码是否有效及本设备id第一次注册,给邀请者增加奖励

        return ResponseUtil.turnData("");
    }

    @PostMapping("/register/import")
    @ResponseBody
    public BaseResponseBean<String> doRegisterTest(String username, String password,
                                                   String platform, String deviceId,
                                                   @RequestParam(value = "code", defaultValue = "0") int code,
                                                   String phoneModel, String appVersion, String promoCode) {

        List<User> userList = new ArrayList<>();
        for (int i = 100; i < 200; i++) {
            Pair<String, String> passwordPair = StringUtil.encryptPassword(password);
            username = "13312345" + i;
            User user = new User();
            user.setUsername(username);
            user.setNickname(username);
            user.setPhone(username);
            user.setPassword(passwordPair.getFirst());
            user.setSalt(passwordPair.getSecond());
            user.setPlatform(platform);
            user.setDeviceId(deviceId);
            user.setPhoneModel(phoneModel);
            user.setVersion(appVersion);
            user.setPromoCode(promoCode);
            user.setRegisterTime(StringUtil.toSqlDate(System.currentTimeMillis()));
            user.setFirstLogin(true);
            userService.insert(user);
            userList.add(user);
        }

        //TODO 判断邀请码是否有效及本设备id第一次注册,给邀请者增加奖励


        return ResponseUtil.turnData("");
    }


    @RequestMapping("/login")
    @ResponseBody
    public BaseResponseBean<LoginResponse> doLoginByUsername(String username, String password,
                                                             String platform, String deviceId,
                                                             String phoneModel, String appVersion) {
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) return ResponseUtil.turnFailedData();
        User user = userService.findByUsername(username);
        if (user == null || !StringUtil.verifyPassword(password,
                user.getPassword(), user.getSalt())) return ResponseUtil.turnFailedData("账号或密码错误");

        String token = jwtTokenUtil.generateToken(user.getId()+"", JwtTokenUtil.AUDIENCE_MOBILE);
        LoginResponse response = new LoginResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstLogin(user.getFirstLogin());
        response.setHead(user.getHead());
        response.setNickname(user.getNickname());
        response.setPhone(user.getPhone());
        response.setPromoCode(user.getPromoCode());
        response.setSex(user.getSex());
        response.setAccessToken(token);

        user.setPlatform(platform);
        user.setDeviceId(deviceId);
        user.setPhoneModel(phoneModel);
        user.setVersion(appVersion);
        if (user.getFirstLogin()) {
            user.setFirstLogin(false);
        }
        userService.update(user);

        return ResponseUtil.turnData(response);
    }


}
