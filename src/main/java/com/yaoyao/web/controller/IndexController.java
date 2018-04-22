package com.yaoyao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yaoyao.web.service.IndexService;

@Controller
public class IndexController {
    
    @Autowired
    private IndexService indexService;
    
    /*
     * 首页
     */
    @RequestMapping(value="index",method=RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv=new ModelAndView("index");
        String indexAd1=this.indexService.queryIndexAD1();
        //添加到模型数据中
        mv.addObject("indexAd1",indexAd1);
        return mv;
    }
}
