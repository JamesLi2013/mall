package com.james.mall.service.impl;

import com.james.mall.bean.Sku;
import com.james.mall.mapper.SkuMapper;
import com.james.mall.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SkuServiceImpl implements SkuService{
    
    @Autowired
    SkuMapper skuMapper;
    
    @Override
    public Sku findById(Long id) {
        return skuMapper.findById(id);
    }

    @Override
    public List<Sku> findByIdIn(List<Long> ids) {
        return skuMapper.findByIdIn(ids);
    }

    @Override
    public List<Sku> findAll() {
        return skuMapper.findAll();
    }

    @Override
    public int insert(Sku sku) {
        return skuMapper.insert(sku);
    }

    @Override
    public int update(Sku sku) {
        return skuMapper.update(sku);
    }

    @Override
    public int delete(Long id) {
        return skuMapper.delete(id);
    }

    @Override
    public int insertList(List<Sku> skuList) {
        return skuMapper.insertList(skuList);
    }
}
