package com.yaoyao.web.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.yaoyao.common.service.RedisService;
import com.yaoyao.manage.pojo.ItemDesc;
import com.yaoyao.web.bean.Item;




@Service
public class ItemService {
    
    @Autowired
    private ApiService apiService;
    
    @Autowired
    private RedisService redisService;
    
    @Value("${YAOYAO_MANAGE_URL}")
    private String YAOYAO_MANAGE_URL;
    
    private static final ObjectMapper MAPPER=new ObjectMapper();
    
    private static final String REDIS_KEY="YAOYAO_WEB_ITEM_DETALL_";
    private static final String REDIS_KEY_DESC="YAOYAO_WEB_ITEM_DESC_DETALL_";
    private static final Integer REDIS_TIME=60*60*24;

    public Item queryItemById(Long itemId) {
        
        //从缓存中命中
        String key=REDIS_KEY+itemId;
        try {
            String cacheData=this.redisService.get(key);
            if(StringUtils.isNotEmpty(cacheData)){
                return MAPPER.readValue(cacheData, Item.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            String url=YAOYAO_MANAGE_URL+"/rest/api/item/"+itemId;
            String jsonData=this.apiService.doGet(url);
            if(StringUtils.isEmpty(jsonData)){
                return null;
            }
            
            try {
                //将数据写入缓存中
                this.redisService.set(key, jsonData,REDIS_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return MAPPER.readValue(jsonData, Item.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemDesc queryItemDescByItemId(Long itemId) {
        
      //从缓存中命中
        String key=REDIS_KEY_DESC+itemId;
        try {
            String cacheData=this.redisService.get(key);
            if(StringUtils.isNotEmpty(cacheData)){
                return MAPPER.readValue(cacheData, ItemDesc.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            String url=YAOYAO_MANAGE_URL+"/rest/api/item/desc/"+itemId;
            String jsonData=this.apiService.doGet(url);
            if(StringUtils.isEmpty(jsonData)){
                return null;
            }
            
            try {
                //将数据写入缓存中
                this.redisService.set(key, jsonData,REDIS_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return MAPPER.readValue(jsonData, ItemDesc.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
