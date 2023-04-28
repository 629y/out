/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.out.controller;

import cn.zxy.out.mapper.DbMapper;
import org.springframework.boot.SpringBootVersion;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/28 16:18 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@RestController
public class IndexController {
    @Resource
    DbMapper dm;

    @Resource
    RedisTemplate redisTemplate;

    @GetMapping
    public String index(@RequestParam(name = "db", required = false, defaultValue = "outdb") String dbname) {
        return SpringBootVersion.getVersion() + String.format("[%s]", dm.tbs(dbname));
    }


    @GetMapping("/ver")
    public String ver() {
        //ValueOperations ops = redisTemplate.opsForValue();    // 首先redisTemplate.opsForValue的目的就是表明是以key，value形式储存到Redis数据库中数据的
        //ops.set("age",88);
        //ops.set("name", "李四");
        //System.out.println(ops.get("name"));

        //将生成的验证码缓存到Redis中，并且设置有效期为5分钟
        redisTemplate.opsForValue().set("13014577033","2323",5, TimeUnit.MINUTES);

        ValueOperations ops = redisTemplate.opsForValue();  // 表明取的是key，value型的数据
        Object o = ops.get("nn");  // 获取Redis数据库中key为address1对应的value数据
        return ops.get("name").toString() + ops.get("age");
    }

}
