package com.james.mall.mapper;

import com.james.mall.bean.Category;
import com.james.mall.bean.User;
import com.james.mall.bean.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    Category findById(@Param("id") Long id);

    List<Category> findAll();

    int insert(Category category);

    int update(Category category);

    int delete(@Param("id") Long id);
}
