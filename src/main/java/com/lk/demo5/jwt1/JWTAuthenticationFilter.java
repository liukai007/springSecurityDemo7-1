package com.lk.demo5.jwt1;

import com.lk.demo5.domain.MobileUser;
import com.lk.demo5.other.MobileAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @创建人: LiuKai
 * @方法描述: <!-- JWT 步骤2-->
 * https://www.cnblogs.com/niceyoo/p/10964277.html
 * @参数:
 * @返回值:
 * @创建时间:
 * @修改人和其它信息:
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    protected final Log log = LogFactory.getLog(this.getClass());

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstant.HEADER);
        if (header == null || header.equals("")) {
            header = request.getParameter(SecurityConstant.HEADER);
        }
        Boolean notValid = (header == null || header.equals("")) || (!header.startsWith(SecurityConstant.TOKEN_SPLIT));
        if (notValid) {
            chain.doFilter(request, response);
            return;
        }
        try {
//            UsernamePasswordAuthenticationToken 继承 AbstractAuthenticationToken 实现 Authentication
//            所以当在页面中输入用户名和密码之后首先会进入到 UsernamePasswordAuthenticationToken验证(Authentication)，
            MobileAuthenticationToken authentication = getAuthentication(header, response);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            e.toString();
        }

        chain.doFilter(request, response);
    }

    private MobileAuthenticationToken getAuthentication(String header, HttpServletResponse response) {

//        用户名
        String username = null;
//        权限
        List<GrantedAuthority> authorities = new ArrayList<>();


        try {
//            解析token
            Claims claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(header.replace(SecurityConstant.TOKEN_SPLIT, ""))
                    .getBody();
            logger.info("claims：" + claims);
//            获取用户名
            username = claims.getSubject();
            logger.info("username：" + username);
//            获取权限
            List authority = (List) claims.get(SecurityConstant.AUTHORITIES);
            logger.info("authority：" + authority);
            for (Object object : authority) {
                String value = object.toString();
                value = value.replace("{authority=", "");
                value = value.replace("}", "");
                authorities.add(new SimpleGrantedAuthority(value));
            }

//            if (!StringUtils.isEmpty(authority)) {
//
//            }

        } catch (ExpiredJwtException e) {
            throw new IllegalStateException("登录已失效，请重新登录");
//            ResponseUtil.out(response, ResponseUtil.resultMap(false, 401, "登录已失效，请重新登录"));
        } catch (Exception e) {
            log.error(e.toString());
            throw new IllegalStateException("解析token错误");
//            ResponseUtil.out(response, ResponseUtil.resultMap(false, 500, "解析token错误"));
        }

        if (username != null && !username.equals("")) {
//            踩坑提醒 此处password不能为null
            MobileUser principal = new MobileUser(username, "", authorities);
            return new MobileAuthenticationToken(authorities, principal.getUsername(), "");
        }
        return null;
    }

}
