package com.example.springboot1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/Portal")
public class Demo1Controller {
    @RequestMapping(value="/test001")
    @ResponseBody
    public Map gitHubTest(String name){
        Map map = new HashMap();
        map.put("name",name);
        map.put("age",12);
        return map;
    }
}
