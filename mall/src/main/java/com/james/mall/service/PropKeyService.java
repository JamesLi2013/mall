package com.james.mall.service;

import com.james.mall.bean.PropKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PropKeyService {
    PropKey findById(Long id);

    List<PropKey> findAll();

    int insert(PropKey propKey);

    int update(PropKey propKey);

    int delete(Long id);

    int insertList(List<PropKey> propKeyList);

    PropKey findByPName(String pName);
}
