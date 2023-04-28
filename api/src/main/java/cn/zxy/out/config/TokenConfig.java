/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.out.config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Map;

/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/28 17:17 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    private String secret;
    private long expires;
    private String name;
    private String account;
    private Map<String, Object> header;

    public String getToken() {
        return JWT.create().withHeader(header)
                .withClaim("name", name)
                .withClaim("account", account)
                .withExpiresAt(Instant.now().plusSeconds(expires * 60))
                .sign(Algorithm.HMAC256(secret));
    }

    public boolean verify(String token) {
        JWTVerifier jv = JWT.require(Algorithm.HMAC256(secret)).build();
        boolean f = true;
        try {
            DecodedJWT v = jv.verify(token);
        } catch (Exception e) {
            f = false;
        }
        return f;
    }
}
