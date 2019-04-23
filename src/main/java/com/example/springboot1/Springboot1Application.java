package com.example.springboot1;

import com.example.springboot1.webService.ipml.WebServiceTest001Impl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.xml.ws.Endpoint;


@SpringBootApplication
//@MapperScan("com.example.springboot1.dao")
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Springboot1Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext ctx =SpringApplication.run(Springboot1Application.class, args);
        //读取配置文件信息
        String testUrl = ctx.getEnvironment().getProperty("testUrl");
        System.out.println("testUrl:"+testUrl);
        String url = "http://localhost:18010/portal/webServiceTest";
        Endpoint.publish(url,new WebServiceTest001Impl());
        System.out.println("发布成功了");
    }

}
