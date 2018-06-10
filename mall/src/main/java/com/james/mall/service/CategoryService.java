package com.james.mall.service;

import com.james.mall.bean.Category;
import com.james.mall.bean.User;
import com.james.mall.bean.UserDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    Category findById(@Param("id") Long id);

    List<Category> findAll();

    int insert(Category category);

    int update(Category category);

    int delete(@Param("id") Long id);
}
