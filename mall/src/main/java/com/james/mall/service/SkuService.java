package com.james.mall.service;

import com.james.mall.bean.Sku;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SkuService  {

    Sku findById(Long id);

    List<Sku> findByIdIn(List<Long> ids);

    List<Sku> findAll();

    int insert(Sku sku);

    int insertList(List<Sku> skuList);

    int update(Sku sku);

    int delete(Long id);
}
