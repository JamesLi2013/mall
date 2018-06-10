package com.james.mall.controller;

import com.james.mall.bean.BaseResponseBean;
import com.james.mall.bean.Product;
import com.james.mall.bean.ProductDto;
import com.james.mall.service.ProductService;
import com.james.mall.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("/insert")
    public BaseResponseBean<String> insertProduct(Product product){
        productService.insert(product);
        return ResponseUtil.turnData("");
    }

    @RequestMapping("/update")
    public BaseResponseBean<String> updateProduct(Product product){
        productService.update(product);
        return ResponseUtil.turnData("");
    }

    @RequestMapping("/find")
    public BaseResponseBean<Product> findByIdProduct(Long id){
        return ResponseUtil.turnData(productService.findById(id));
    }

    @RequestMapping("/findAll")
    public BaseResponseBean<List<ProductDto>> findAllProductDto(){
        return ResponseUtil.turnData(productService.findAll());
    }

}
