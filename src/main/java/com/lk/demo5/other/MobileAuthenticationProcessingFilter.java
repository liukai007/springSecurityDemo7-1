package com.lk.demo5.other;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;


//@Component
public class MobileAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }


    public MobileAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (request.getMethod().equals("POST")) {
            //获取body里面的东西
            String bodyContent = getBodyContent(request);
            Map map = ((Map) (JSON.parse(bodyContent)));
            String mobileNumber = (String) map.get("mobileNumber");
            String code = (String) map.get("code");
            MobileAuthenticationToken authRequest = new MobileAuthenticationToken(mobileNumber, code);
            // TODO: 2021/6/15 这个有啥作用
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        } else {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
    }

    protected void setDetails(HttpServletRequest request, MobileAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    /* 得到body体中的内容*/
    private String getBodyContent(HttpServletRequest request) throws IOException {
        BufferedReader bufferReader = new BufferedReader(request.getReader());
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = bufferReader.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

}
