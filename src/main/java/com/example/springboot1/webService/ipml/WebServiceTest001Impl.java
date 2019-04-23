package com.example.springboot1.webService.ipml;

import com.example.springboot1.webService.WebServiceTest001;
@javax.jws.WebService
public class WebServiceTest001Impl implements WebServiceTest001 {
    @Override
    public String sayHello(String name) {
        name="zhangsan";
        String str =  name+" say:"+"Hello";
        System.out.println("say:"+name+":"+"Hello");
        return str;
    }
}
