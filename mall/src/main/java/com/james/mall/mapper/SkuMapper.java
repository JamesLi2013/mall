package com.james.mall.mapper;

import com.james.mall.bean.Sku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SkuMapper {

    Sku findById(@Param("id") Long id);

    List<Sku> findByIdIn(List<Long> ids);

    List<Sku> findAll();

    int insert(Sku sku);

    int insertList(List<Sku> skuList);

    int update(Sku sku);

    int delete(@Param("id") Long id);
}
