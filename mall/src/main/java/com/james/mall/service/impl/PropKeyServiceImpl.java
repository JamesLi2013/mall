package com.james.mall.service.impl;

import com.james.mall.bean.PropKey;
import com.james.mall.mapper.PropKeyMapper;
import com.james.mall.service.PropKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropKeyServiceImpl implements PropKeyService {

    @Autowired
    PropKeyMapper propKeyMapper;

    @Override
    public PropKey findById(Long id) {
        return propKeyMapper.findById(id);
    }

    @Override
    public List<PropKey> findAll() {
        return propKeyMapper.findAll();
    }

    @Override
    public int insert(PropKey propKey) {
        return propKeyMapper.insert(propKey);
    }

    @Override
    public int update(PropKey propKey) {
        return propKeyMapper.update(propKey);
    }

    @Override
    public int delete(Long id) {
        return propKeyMapper.delete(id);
    }

    @Override
    public int insertList(List<PropKey> propKeyList) {
        return propKeyMapper.insertList(propKeyList);
    }

    @Override
    public PropKey findByPName(String pName) {
        return propKeyMapper.findByPName(pName);
    }
}
