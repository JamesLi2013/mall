package com.james.mall.mapper;

import com.james.mall.bean.PropValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PropValueMapper {
    PropValue findById(@Param("id") Long id);

    List<PropValue> findAll();

    int insert(@Param("entity") PropValue propValue);
    
    int insertList(List<PropValue> propValueList);

    int update(PropValue propValue);

    int delete(@Param("id") Long id);

    PropValue findByVName(@Param("vName") String vName);
    List<PropValue> findByPid(@Param("pid") Long pid);
}
