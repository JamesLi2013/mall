package com.james.mall.service;

import com.james.mall.bean.PropValue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PropValueService {
    PropValue findById(Long id);

    List<PropValue> findAll();

    int insert(PropValue propValue);

    int insertList(List<PropValue> propValueList);

    int update(PropValue propValue);

    int delete(Long id);

    PropValue findByVName(String vName);

    List<PropValue> findByPid( Long pid);
}
