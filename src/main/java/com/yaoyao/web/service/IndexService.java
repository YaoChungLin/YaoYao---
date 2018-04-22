package com.yaoyao.web.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;


@Service
public class IndexService {
    
    @Autowired
    private ApiService apiService;
    
    @Value("${YAOYAO_MANAGE_URL}")
    private String YAOYAO_MANAGE_URL;
    
    @Value("${INDEX_AD1_URL}")
    private String INDEX_AD1_URL;
    
    private static final ObjectMapper MAPPER=new ObjectMapper();
    
    
    public String queryIndexAD1() {
      try {
          String url = YAOYAO_MANAGE_URL + INDEX_AD1_URL;
          String jsonData = this.apiService.doGet(url);
          if (StringUtils.isEmpty(jsonData)) {
              return null;
          }
          // 解析json数据，封装成前端所需要的结构
          JsonNode jsonNode = MAPPER.readTree(jsonData);
          ArrayNode rows = (ArrayNode) jsonNode.get("rows");
          List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
          for (JsonNode row : rows) {
              Map<String, Object> map = new LinkedHashMap<String, Object>();
              map.put("srcB", row.get("pic").asText());
              map.put("height", 240);
              map.put("alt", row.get("title").asText());
              map.put("width", 670);
              map.put("src", row.get("pic").asText());
              map.put("widthB", 550);
              map.put("href", row.get("url").asText());
              map.put("heightB", 240);
              result.add(map);
          }
          return MAPPER.writeValueAsString(result);
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
  }
}
