package com.james.mall.mapper;

import com.james.mall.bean.PropKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PropKeyMapper {
    PropKey findById(@Param("id") Long id);

    List<PropKey> findAll();

//    int insert(@Param("entity") PropKey propKey);
    int insert(PropKey propKey);

    int insertList(List<PropKey> propKeyList);

    int update(PropKey propKey);

    int delete(@Param("id") Long id);

    PropKey findByPName(@Param("pName") String pName);
}
