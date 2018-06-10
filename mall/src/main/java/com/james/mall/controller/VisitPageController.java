package com.james.mall.controller;

import com.james.mall.bean.BaseResponseBean;
import com.james.mall.bean.VisitPage;
import com.james.mall.service.VisitPageService;
import com.james.mall.util.ResponseUtil;
import com.james.mall.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/analysis")
public class VisitPageController {
    @Autowired
    VisitPageService visitPageService;

    @RequestMapping("/import")
    @Transactional
    public BaseResponseBean<String> importData(){
        String[] citys={"深圳","长沙","广州"};
        String[] pageNames={"首页","产品搜索","产品详情","支付成功"};
        String[] keywords={"苹果","香蕉","水果","笔记本","电脑","台式电脑"};
        long before=System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            final VisitPage visitPage=new VisitPage();
            Random random=new Random();
            int randomResult = random.nextInt(10000);
            visitPage.setUid(randomResult);
            visitPage.setCity(citys[randomResult%3]);
            visitPage.setPageName(pageNames[randomResult%4]);
            if(visitPage.getPageName().equals("产品搜索")) visitPage.setKeyword(keywords[randomResult%6]);
            visitPage.setCreated(StringUtil.toSqlDate(System.currentTimeMillis()-randomResult*1000*777));
            visitPageService.insert(visitPage);
        }
        return ResponseUtil.turnData("耗费时间:"+(System.currentTimeMillis()-before)/1000);
    }
}
