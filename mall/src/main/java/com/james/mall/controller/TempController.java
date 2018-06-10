package com.james.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TempController {

    @RequestMapping("/temp/h5")
    public String getH5(){
        return "test";
    }

    @RequestMapping("/temp/mv")
    public ModelAndView getMv(){
        ModelAndView modelAndView =new ModelAndView("test");
        modelAndView.addObject("mvData","hello");
        return modelAndView;
    }

    @RequestMapping("/helloWorld")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello World!");
        return "test";
    }
}
