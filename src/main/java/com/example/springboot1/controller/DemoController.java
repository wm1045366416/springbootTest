package com.example.springboot1.controller;

import com.example.springboot1.User;
import com.example.springboot1.service.UserRedisService;
import com.example.springboot1.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  wang
 *
 */
@Controller
public class DemoController {
    //@Autowired
    //private JdbcTemplate jdbcTemplate;
    @Resource
    private UserService userService;
    @Resource
    private UserRedisService redisService;
    @Value("${testUrl}")
    private String url;
    @RequestMapping(value = "/test001")
    @ResponseBody
    public Map test001() {
        Map map = new HashMap();
        map.put("id", "21738914");
        map.put("name", "先");
        map.put("url",url);
        System.out.println("输出:" + map);
        return map;
    }
    @RequestMapping(value="/test002")
    @ResponseBody
    public Map test002(){
        Map map = new HashMap();
        map.put("code","111");
        List list = userService.getList(map);
        if(list!=null&&list.size()>0){
            System.out.println("--11-"+list+"--22-");
        }else{
            System.out.println("未查询到记录");
        }
        //String sql = "select name from ot_user where code='Administrator' ";
        //jdbcTemplate.execute(sql);
        //List list = jdbcTemplate.queryForList(sql);
        System.out.println("list:"+list);
        return map;
    }
    @RequestMapping(value="/test003")
    @ResponseBody
    public void test003(){
        Map map = new HashMap();
        map.put("name","lisi");
        map.put("code","111");
        boolean b = userService.updateUser(map);
        System.out.println("flag:"+b);
    }
    @RequestMapping(value="/test004")
    @ResponseBody
    public void test004(){
        User user = redisService.findUserById("333");
        System.out.println("SUCCESS");
    }

    @RequestMapping(value="/test005")
    @ResponseBody
    public String test005(String name){
        String str = name+":hello";
        System.out.println("SUCCESS1111");        return str;
    }
}
