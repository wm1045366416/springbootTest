package com.example.springboot1;

import com.example.springboot1.dao.UserDao;
import com.example.springboot1.service.UserRedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot1ApplicationTests {
    @Resource
    private UserRedisService service;
    @Resource
    private UserDao userDao;
    @Test
    public void contextLoads() {
    }
    @Test
    public void test01(){
        Map map = new HashMap();
        map.put("name","2222");
        map.put("code","111");
        List list = userDao.getList(map);
        System.out.println("查询:"+list);
    }
    @Test
    public void test02(){
        String code = "111";
        User user = service.findUserById(code);
        System.out.println("user:"+user.toString());
    }
}
