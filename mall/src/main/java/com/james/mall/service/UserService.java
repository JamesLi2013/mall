package com.james.mall.service;

import com.james.mall.bean.User;
import com.james.mall.bean.UserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    UserDto findUserDtoById( Long id);
    UserDto findUserDtoByUsername(String username);
    List<UserDto> findAll();
    int insert(User user);
    int update(User user);
    int delete(Long id);
}
