package com.james.mall.service;

import com.james.mall.bean.VisitPage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VisitPageService {
    VisitPage findById(Long id);

    List<VisitPage> findAll();

    int insert(VisitPage visitPage);

    int update(VisitPage visitPage);

    int delete(Long id);
}
