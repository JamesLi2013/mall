package com.james.mall.controller;

import com.james.mall.base.UserException;
import com.james.mall.bean.BaseResponseBean;
import com.james.mall.bean.User;
import com.james.mall.bean.UserDto;
import com.james.mall.service.UserService;
import com.james.mall.util.Pair;
import com.james.mall.util.ResponseUtil;
import com.james.mall.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController()
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/insert")
    public BaseResponseBean<String> insertUser(User user){

        if(user.getPhone()==null) throw new UserException("手机号码不能为空");
        if(user.getPhone().length()!=11) throw new UserException("手机号码位数不是11位");
        Pair<String, String> passwordPair = StringUtil.encryptPassword(user.getPassword());
        user.setPassword(passwordPair.getFirst());
        user.setSalt(passwordPair.getSecond());
        user.setFirstLogin(true);
        user.setRegisterTime(StringUtil.toSqlDate(System.currentTimeMillis()));
        user.setLastLogin(StringUtil.toSqlDate(System.currentTimeMillis()));
        int result = userService.insert(user);
        return ResponseUtil.turnData(""+result);
    }

    @RequestMapping("/update")
    public BaseResponseBean<String> updateUser(User user){
        userService.update(user);
        return ResponseUtil.turnData("");
    }

    @RequestMapping("/delete")
    public BaseResponseBean<String> deleteUser(Long id){
        userService.delete(id);
        return ResponseUtil.turnData("");
    }

    @RequestMapping("/find")
    public BaseResponseBean<User> findById(Long id){
        System.out.println("findUser");
        return ResponseUtil.turnData( userService.findById(id));
    }

    @RequestMapping("/findAll")
    public BaseResponseBean<List<UserDto>> findAll(){
        return ResponseUtil.turnData( userService.findAll());
    }

    @RequestMapping("/import")
    @Transactional
    public BaseResponseBean<String> importUsers(){
        for (int i = 1000; i < 2000; i++) {
            User user=new User();
            user.setPhone("1701234"+i);
            user.setPassword("123456");
            user.setSalt(StringUtil.encryptPassword("123456").getSecond());
            user.setNickname("lisi"+i);
            user.setDeviceId(UUID.randomUUID().toString().replace("-", ""));
            user.setUsername(user.getPhone());
            Random random=new Random();
            int randomResult = random.nextInt(9000);
            user.setLatitude(22.518345+(randomResult+1000)/10000d);
            user.setLongitude(113.928566+(randomResult+1000)/20000d);
            userService.insert(user);
        }
        return ResponseUtil.turnData("");
    }

/*    @GetMapping("/test")
    public String test(){
        List<String> xx=new ArrayList<>();
        xx.get(2);
        return "test";
    }

    @GetMapping("/null")
    public User returnNull(){

        return null;
    }

    @GetMapping("/string")
    public String returnString(){

        return "hello";
    }
    */

}
