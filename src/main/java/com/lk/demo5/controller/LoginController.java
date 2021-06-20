package com.lk.demo5.controller;


import com.lk.demo5.jwt1.SecurityConstant;
import com.lk.demo5.utils.HttpResult;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/loginceshi")
public class LoginController {

    @RequestMapping(method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public HttpResult doPost(@RequestBody Object data) throws IOException {
        LinkedHashMap linkedHashMap = (LinkedHashMap) data;
        System.out.println(linkedHashMap.get("mobileNumber"));
        System.out.println(linkedHashMap.get("code"));

//        return "login";

//        String token = SecurityConstant.TOKEN_SPLIT + Jwts.builder()
////        主题 放入用户名
//            .setSubject(linkedHashMap.get("mobileNumber").toString())
////        自定义属性 放入用户拥有请求权限
//            .claim(SecurityConstant.AUTHORITIES, "")
////        失效时间
//                .setExpiration(new Date(System.currentTimeMillis() + 7 * 60 * 1000))
////        签名算法和密钥
//                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
//                .compact();


        return HttpResult.ok("login");
//        return HttpResult.ok(token);
    }
}
