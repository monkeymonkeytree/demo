package com.example.demo;

import com.example.demo.bean.CGQ;
import com.example.demo.bean.CGQType;
import com.example.demo.bean.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.utils.utils.MD5;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.xml.ws.soap.Addressing;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("登录查数据库测试")
    @Test
    void contextLoads() {
        System.out.println(userMapper.selectById(1));
    }


    @DisplayName("注册")
    @Test
    void testInsert() {
        User user = new User();
        user.setUsername("root");
        user.setPassword("root");
        user.setEmail("925902118@qq.com");
        user.setRole("管理员");
      userService.InsertUser(user);
        System.out.println(user);
    }


    @DisplayName("测试redis")
    @Test
    void testRedis() {
        System.out.println(stringRedisTemplate);
        stringRedisTemplate.opsForValue().set("sayhi", "hi");
    }

    @DisplayName("admin修改")
    @Test
    void testMD5() {
        User user = new User();
        user.setId(36);
        user.setUsername("hhhh");
        userMapper.updateUser(user);
    }

    @DisplayName("MD5")
    @Test
    void MD5() {
        System.out.println(MD5.encrypt("admin"));
    }


    @DisplayName("MangoDB")
    @Test
    void MangoDB() {
        System.out.println(mongoTemplate);
//        System.out.println(mongoTemplate.find(new Query(Criteria.where("name").regex("温度传感器")), CGQ.class));
//        mongoTemplate.insert(new CGQ(1, "光照传感器", new Date().toString(), "在线", "数字型"));
//        MongoCollection<Document> collection = mongoTemplate.createCollection(CGQ.class);
//        System.out.println(collection);
//        System.out.println(mongoTemplate.collectionExists(CGQ.class));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        mongoTemplate.insert(new CGQ(3, "烟雾报警器", simpleDateFormat.format(new Date()), "在线", "文本预警消息型"));

//        System.out.println(mongoTemplate.findById(1, CGQ.class));
        MongoCollection<Document> collection = mongoTemplate.createCollection(CGQType.class);
    }

}
