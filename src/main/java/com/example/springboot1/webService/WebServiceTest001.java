package com.example.springboot1.webService;

import javax.jws.WebMethod;

@javax.jws.WebService
public interface WebServiceTest001 {
    @WebMethod
    String sayHello(String name);
}
