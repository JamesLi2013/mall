package com.james.mall.mapper;

import com.james.mall.bean.Product;
import com.james.mall.bean.ProductDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    int insert(Product product);
    int update(Product product);
    int delete(@Param(value = "id") Long id);
    Product findById(@Param(value = "id") Long id);
    List<ProductDto> findAll();
}
