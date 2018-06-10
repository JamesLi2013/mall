package com.james.mall.service.impl;

import com.james.mall.bean.PropValue;
import com.james.mall.mapper.PropValueMapper;
import com.james.mall.service.PropValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropValueServiceImpl implements PropValueService{

    @Autowired
    PropValueMapper propValueMapper;

    @Override
    public PropValue findById(Long id) {
        return propValueMapper.findById(id);
    }

    @Override
    public List<PropValue> findAll() {
        return propValueMapper.findAll();
    }

    @Override
    public int insert(PropValue propValue) {
        return propValueMapper.insert(propValue);
    }

    @Override
    public int insertList(List<PropValue> propValueList) {
        return propValueMapper.insertList(propValueList);
    }

    @Override
    public int update(PropValue propValue) {
        return propValueMapper.update(propValue);
    }

    @Override
    public int delete(Long id) {
        return propValueMapper.delete(id);
    }

    @Override
    public PropValue findByVName(String vName) {
        return propValueMapper.findByVName(vName);
    }

    @Override
    public List<PropValue> findByPid(Long pid) {
        return propValueMapper.findByPid(pid);
    }
}
