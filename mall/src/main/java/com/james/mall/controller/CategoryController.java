package com.james.mall.controller;

import com.james.mall.base.UserException;
import com.james.mall.bean.Category;
import com.james.mall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/insert")
    public String insertCategory(Category category){
        String result="新增分类成功";
        if(categoryService.insert(category)==0) throw new UserException("新增分类失败");
        return result;
    }

    @RequestMapping("/update")
    public String updateCategory(Category category){
        return categoryService.update(category)+"";
    }

    @RequestMapping("/delete")
    public String deleteCategory(Long id){
        return categoryService.delete(id)+"";
    }

    @RequestMapping("/find")
    public Category findByIdCategory(Long id){
        return categoryService.findById(id);
    }

    @RequestMapping("/findAll")
    public List<Category> findAllCategory(){
        return categoryService.findAll();
    }
}
