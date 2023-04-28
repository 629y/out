/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.out.controller;

import cn.zxy.out.config.TokenConfig;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/28 16:19 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@RestController
@RequestMapping("/api/admin")
public class LoginController {
    @Autowired
    TokenConfig token;

    @PostMapping("login")
    @CrossOrigin
    public Map<String, Object> login(@RequestBody Map<String, String> m) {
        //@RequestBody Map<String,String> m 接POST请求传来的参数值
        Map<String, Object> map = new HashMap<>();
        String u = m.get("username");
        String p = m.get("password");
        if ("admin".equalsIgnoreCase(u) && "123456".equals(p)) {
            map.put("code", 1);
            map.put("msg", "登录成功");

            map.put("token",token.getToken());
        } else {
            map.put("code", 0);
            map.put("msg", "登录失败，请检查账号密码");
        }
        return map;
    }
}
