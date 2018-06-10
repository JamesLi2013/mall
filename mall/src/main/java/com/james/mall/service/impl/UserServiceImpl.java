package com.james.mall.service.impl;

import com.james.mall.bean.User;
import com.james.mall.bean.UserDto;
import com.james.mall.mapper.UserMapper;
import com.james.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public UserDto findUserDtoById(Long id) {
        return userMapper.findUserDtoById(id);
    }

    @Override
    public UserDto findUserDtoByUsername(String username) {
        return userMapper.findUserDtoByUsername(username);
    }

    @Override
    public List<UserDto> findAll() {
        return userMapper.findAll();
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int delete(Long id) {
        if (id == null || id <= 0) {
            return 0;
        }
        return userMapper.delete(id);
    }
}
