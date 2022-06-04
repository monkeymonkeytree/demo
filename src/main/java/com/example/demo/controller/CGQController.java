package com.example.demo.controller;

import com.example.demo.bean.CGQ;
import com.example.demo.bean.CGQType;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.apache.ibatis.annotations.Param;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Controller
public class CGQController {

    @Autowired
    MongoTemplate mongoTemplate;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @PutMapping("/insertCGQ")
    @ResponseBody
    public Object insertCGQ(CGQ cgq) {
        String format = simpleDateFormat.format(new Date());
        cgq.setUpdateTime(format);
        System.out.println(cgq);
        mongoTemplate.insert(cgq);
        mongoTemplate.upsert(new Query(Criteria.where("name").is(cgq.getName())), new Update().set("updateTime", format), "cGQType");
        HashMap<Object, Object> map = new HashMap<>();
        map.put("msg", "添加成功！");
        return map;
    }


    @DeleteMapping("/deleteCGQ")
    @ResponseBody
    public Object deleteCGQ(CGQ cgq, @Param("key") String key) {
        HashMap<Object, Object> map = new HashMap<>();

        if (cgq.getValue() == "" && key == "") {
            mongoTemplate.remove(new Query(Criteria.where("name").is(cgq.getName())), "cGQ");
        } else {
            mongoTemplate.remove(new Query(Criteria.where("name").is(cgq.getName()).and(key).is(cgq.getValue())), "cGQ");
        }


        map.put("msg", "删除成功！");
        return map;
    }

    @PutMapping("/addCGQ")
    @ResponseBody
    public Object addCGQ(CGQType cgqType) {
        cgqType.setUpdateTime(simpleDateFormat.format(new Date()));
        cgqType.setId((cgqType.hashCode()));
        if (cgqType.getStatus().equals("是")) {
            cgqType.setStatus("在线");
        } else {
            cgqType.setStatus("离线");
        }
        System.out.println(cgqType);
        HashMap<Object, Object> map = new HashMap<>();

        try {
            mongoTemplate.insert(cgqType);
        } catch (Exception e) {
            map.put("msg", "添加失败");
            return map;
        }

        map.put("msg", "添加成功！");

        return map;
    }

    @PutMapping("/selectCGQ")
    @ResponseBody
    public Object selectCGQ() {

        List<CGQType> all = mongoTemplate.findAll(CGQType.class);
        System.out.println(all);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("CGQS", all);
        return map;
    }

    @PutMapping("/updateCGQ")
    @ResponseBody
    public Object updateCGQ(CGQType cgqType) {
        String format = simpleDateFormat.format(new Date());
        System.out.println(cgqType);
        HashMap<Object, Object> map = new HashMap<>();

        mongoTemplate.upsert(new Query(Criteria.where("_id").is(cgqType.getId())), new Update().set("updateTime", format).set("status", cgqType.getStatus()), "cGQType");
        if (cgqType.getType() != null) {
            mongoTemplate.upsert(new Query(Criteria.where("_id").is(cgqType.getId())), new Update().set("type", cgqType.getType()), "cGQType");
        }
        if (cgqType.getName() != null) {
            mongoTemplate.upsert(new Query(Criteria.where("_id").is(cgqType.getId())), new Update().set("name", cgqType.getName()), "cGQType");
        }
        map.put("msg", "更新成功");
        return map;
    }

    @DeleteMapping("/deleteMyCGQ")
    @ResponseBody
    public Object deleteCGQ(CGQType cgqType) {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            System.out.println(cgqType);
            mongoTemplate.remove(new Query(Criteria.where("_id").is(cgqType.getId())), "cGQType");
            map.put("msg", "删除成功！");
        } catch (Exception e) {
            map.put("msg", "删除失败！");
        }

        return map;

    }

    @PutMapping("/selectData")
    @ResponseBody
    public Object selectData(String name) {
        HashMap<Object, Object> map = new HashMap<>();
        System.out.println(name);
        map.put("msg", "没有数据");
        if (name.equals("全部")) {
            List<CGQ> cGQ = mongoTemplate.findAll(CGQ.class, "cGQ");
            if (cGQ.size() > 0)
                map.put("msg", cGQ);
        } else {
            List<CGQ> cGQ = mongoTemplate.find(new Query(Criteria.where("name").is(name)), CGQ.class);
            if (cGQ.size() > 0)
                map.put("msg", cGQ);
        }
        return map;
    }
}
