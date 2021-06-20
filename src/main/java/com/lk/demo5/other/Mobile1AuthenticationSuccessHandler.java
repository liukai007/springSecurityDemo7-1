package com.lk.demo5.other;

import com.lk.demo5.jwt1.SecurityConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class Mobile1AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private String forwardUrl;

    public Mobile1AuthenticationSuccessHandler(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        httpServletRequest.getRequestDispatcher(forwardUrl).forward(httpServletRequest, httpServletResponse);
        String token = createToken(authentication);
        httpServletRequest.setAttribute(SecurityConstant.HEADER, token);
        httpServletResponse.setHeader(SecurityConstant.HEADER, token);
        httpServletResponse.sendRedirect(forwardUrl);
//        httpServletRequest.getRequestDispatcher(forwardUrl).forward(httpServletRequest, httpServletResponse);
    }

    private String createToken(Authentication authentication) {
        String token = SecurityConstant.TOKEN_SPLIT+" " + Jwts.builder()
//        主题 放入用户名
                .setSubject(authentication.getName())
//        自定义属性 放入用户拥有请求权限
                .claim(SecurityConstant.AUTHORITIES, authentication.getAuthorities())
//        失效时间
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 60 * 1000))
//        签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.JWT_SIGN_KEY)
                .compact();
        System.out.println(token);
        System.out.println(token);
        System.out.println(token);
        return token;
    }
}
