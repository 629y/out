/*
 * Copyright (c) 2017, 2023, zxy.cn All rights reserved.
 *
 */
package cn.zxy.out;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description:</p>
 * <p>Class:</p>
 * <p>Powered by zxy On 2023/4/28 17:13 </p>
 *
 * @author zxy [zxy06291@163.com]
 * @version 1.0
 * @since 17
 */
@Log4j2
public class JwtDemo {
    @Test
    void m() {
        //生成token
        //String token = JWT.create().sign(Algorithm.HMAC256("webrx"));
        //log.info(token);
        //String tk = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.e30.03BNBX8FwXubNISFD2SJUetYEv5-S86sApLpuJN3cXE";

        //检验token
        //JWTVerifier jv = JWT.require(Algorithm.HMAC256("webrx")).build();
        //System.out.println(jv.verify(tk).getExpiresAt());

        //第一部分 header
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        String tk = JWT.create().withHeader(header)
                .withClaim("name","管理员")
                .withExpiresAt(Instant.now().plusSeconds(300))
                .sign(Algorithm.HMAC256("webrx"));

        log.debug(tk);

        String tt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi566h55CG5ZGYIiwiZXhwIjoxNjgyNjY0NjIxfQ.4NFP4x8DtsiK5HuzRFXe3SjNw26aRoFJVBbiPgBmpJQ";
        tt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi566h55CG5ZGYIiwiZXhwIjoxNjgyNjY1MTgwfQ.hcL_VBI1CLqQN4M6vfHiCwn33Hy6bP-zsqmAkZnymi0";
        tt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5p2O5ZubIiwiYWNjb3VudCI6ImFkbWluIiwiZXhwIjoxNjgyNjcxMzM0fQ.6r7hNWIFmf7b9BnuOcK6ufszmv0oZVtg1Xh9Z9BZ8fI";
        tt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi5p2O5ZubIiwiYWNjb3VudCI6ImFkbWluIiwiZXhwIjoxNjgyNjczNjIzfQ.WG3KgaWuxS6jgly3GYsCx_qXwX7TZCBJzXS28-f9JuY";

        JWTVerifier jv = JWT.require(Algorithm.HMAC256("webrx")).build();
        try{
            DecodedJWT verify = jv.verify(tt);
            System.out.println(verify.getHeader());
            System.out.println(verify.getAlgorithm());
            System.out.printf("%tF %<tT%n",verify.getExpiresAt());
        }catch(TokenExpiredException e){
            System.out.println("token过期了");
        }catch(SignatureVerificationException e){
            System.out.println("签名错误");
        }
    }
}
