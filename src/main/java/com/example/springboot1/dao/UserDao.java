package com.example.springboot1.dao;

import com.example.springboot1.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
@Mapper
public interface UserDao {
    List getList(Map map);
    int updateUser(Map map);

    List<User> queryAll();
    User findUserById(String id);
    //int updateUser(@Param("user") User user);
    //int deleteUserById(String id);
}
