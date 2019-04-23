package com.example.springboot1.service;

import com.example.springboot1.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Resource
    private UserDao userDao;
    public List getList(Map map){
        List list = userDao.getList(map);
        return list;
    }
    /**
     * 修改用户信息
     *
     */

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
    public boolean updateUser(Map map){
        int row = userDao.updateUser(map);
        if(row>0){
            return true;
        }else {
            return false;
        }
    }
}

