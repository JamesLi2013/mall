package com.james.mall.service.impl;

import com.james.mall.bean.VisitPage;
import com.james.mall.mapper.VisitPageMapper;
import com.james.mall.service.VisitPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VisitPageServiceImpl implements VisitPageService {
    @Autowired
    VisitPageMapper visitPageMapper;

    @Override
    public VisitPage findById(Long id) {
        return visitPageMapper.findById(id);
    }

    @Override
    public List<VisitPage> findAll() {
        return visitPageMapper.findAll();
    }

    @Override
    public int insert(VisitPage visitPage) {
        return visitPageMapper.insert(visitPage);
    }

    @Override
    public int update(VisitPage visitPage) {
        return visitPageMapper.update(visitPage);
    }

    @Override
    public int delete(Long id) {
        return visitPageMapper.delete(id);
    }
}
