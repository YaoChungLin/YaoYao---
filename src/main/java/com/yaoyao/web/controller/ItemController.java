package com.yaoyao.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.yaoyao.manage.pojo.ItemDesc;
import com.yaoyao.web.bean.Item;
import com.yaoyao.web.service.ItemService;

@RequestMapping("item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;
    
    @RequestMapping(value="{itemId}",method=RequestMethod.GET)
    public ModelAndView showDetail(@PathVariable("itemId") Long itemId){
        ModelAndView mv=new ModelAndView("item");
        Item item= this.itemService.queryItemById(itemId);
        mv.addObject("item",item);
        
        ItemDesc itemDesc=this.itemService.queryItemDescByItemId(itemId);
        mv.addObject("itemDesc",itemDesc);
        
        return mv;
    }
}
