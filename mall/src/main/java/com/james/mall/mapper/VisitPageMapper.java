package com.james.mall.mapper;

import com.james.mall.bean.VisitPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VisitPageMapper {
    VisitPage findById(@Param("id") Long id);

    List<VisitPage> findAll();

    int insert(VisitPage visitPage);

    int update(VisitPage visitPage);

    int delete(@Param("id") Long id);
}
