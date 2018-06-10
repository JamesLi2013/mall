package com.james.mall.mapper;

import com.james.mall.bean.User;
import com.james.mall.bean.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findById(@Param("id") Long id);
    User findByUsername(@Param("username") String username);
    UserDto findUserDtoById(@Param("id") Long id);
    UserDto findUserDtoByUsername(@Param("username") String username);

    List<UserDto> findAll();

    int insert(User user);

    int update(User user);

    int delete(@Param("id") Long id);
}
