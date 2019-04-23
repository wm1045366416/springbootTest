package com.example.springboot1.service;

import com.example.springboot1.User;
import com.example.springboot1.dao.UserDao;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Service
public class UserRedisService {
    @Resource
    private UserDao userDao;
    private RedisTemplate redisTemplate;

    /*
     * redisTemplate 默认的序列化方式为 jdkSerializeable,
     * StringRedisTemplate的默认序列化方式为StringRedisSerializer
     *
     */
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        this.redisTemplate = redisTemplate;
    }

    public List<User> queryAll() {
        return userDao.queryAll();
    }

    /**
     * 获取用户策略：先从缓存中获取用户，没有则取数据表中 数据，再将数据写入缓存
     */
    public User findUserById(String id) {
        String key = "user_" + id;

        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        System.out.println("key:"+key);
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            long start = System.currentTimeMillis();
            User user = operations.get(key);
            System.out.println("==========从缓存中获得数据=========");
            System.out.println(user.getName());
            System.out.println("==============================");
            long end = System.currentTimeMillis();
            System.out.println("查询redis花费的时间是:" + (end - start)+"s");
            return user;
        } else {
            long start = System.currentTimeMillis();
            User user = userDao.findUserById(id);
            System.out.println("==========从数据表中获得数据=========");
            System.out.println(user.getName());
            System.out.println("==============================");

            // 写入缓存
            operations.set(key, user, 5, TimeUnit.HOURS);
            long end = System.currentTimeMillis();
            System.out.println("查询mysql花费的时间是:" + (end - start)+"s");
            return user;
        }

    }

    /**
     * 更新用户策略：先更新数据表，成功之后，删除原来的缓存，再更新缓存
     */
    /*public int updateUser(User user) {
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        int result = userDao.updateUser(user);
        if (result != 0) {
            String key = "user_" + user.getObjectId();
            boolean haskey = redisTemplate.hasKey(key);
            if (haskey) {
                redisTemplate.delete(key);
                System.out.println("删除缓存中的key=========>" + key);
            }
            // 再将更新后的数据加入缓存
            User userNew = userDao.findUserById(user.getObjectId());
            if (userNew != null) {
                operations.set(key, userNew, 3, TimeUnit.HOURS);
            }
        }
        return result;
    }*/

    /**
     * 删除用户策略：删除数据表中数据，然后删除缓存
     */
    /*public int deleteUserById(String id) {
        int result = userDao.deleteUserById(id);
        String key = "user_" + id;
        if (result != 0) {
            boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey) {
                redisTemplate.delete(key);
                System.out.println("删除了缓存中的key:" + key);
            }
        }
        return result;

    }*/
}