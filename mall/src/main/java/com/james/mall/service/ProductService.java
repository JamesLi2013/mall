package com.james.mall.service;

import com.james.mall.bean.Product;
import com.james.mall.bean.ProductDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    int insert(Product product);

    int update(Product product);

    int delete(@Param(value = "id") Long id);

    Product findById(@Param(value = "id") Long id);

    List<ProductDto> findAll();
}
