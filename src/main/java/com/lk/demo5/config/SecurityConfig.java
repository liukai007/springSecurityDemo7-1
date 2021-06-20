package com.lk.demo5.config;

import com.lk.demo5.jwt1.JWTAuthenticationFilter;
import com.lk.demo5.jwt1.MyAuthenticationEntryPoint;
import com.lk.demo5.other.Mobile1AuthenticationFailureHandler;
import com.lk.demo5.other.Mobile1AuthenticationSuccessHandler;
import com.lk.demo5.other.MobileAuthenticationProcessingFilter;
import com.lk.demo5.other.MobileAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;
//    @Autowired
//    private MobileAuthenticationProvider mobileAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors();
//        http.csrf().disable();
        //        前后端分离采用JWT 不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //        添加JWT过滤器 除已配置的其它请求都需经过此过滤器
//        http.addFilter(jWTAuthenticationFilter());
        http.addFilterBefore(jWTAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class);
        http.addFilterBefore(mobileAuthenticationProcessingFilter(), AbstractPreAuthenticatedProcessingFilter.class);
        //拦截跪着
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/loginceshi").access("permitAll")
                .antMatchers(HttpMethod.GET, "/failure").permitAll()
                //自定义access方法
                //注意必须使用request,而不是上面的   https://dqcgm.blog.csdn.net/article/details/109023478
                .anyRequest().access("@myAccessServiceImpl.hasPermission(request,authentication)")
                .and().csrf().disable();


//        http.anonymous().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //将多个自定义认证器都注册
        auth.authenticationProvider(mobileAuthenticationProvider());

    }

    @Bean
    public MobileAuthenticationProvider mobileAuthenticationProvider() {
        return new MobileAuthenticationProvider();
    }

    @Bean
    public JWTAuthenticationFilter jWTAuthenticationFilter() throws Exception {
        JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManager(), new MyAuthenticationEntryPoint());
        return filter;
    }


    @Bean
    public MobileAuthenticationProcessingFilter mobileAuthenticationProcessingFilter() {
        MobileAuthenticationProcessingFilter filter = new MobileAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(new Mobile1AuthenticationSuccessHandler("/success"));
        filter.setAuthenticationFailureHandler(new Mobile1AuthenticationFailureHandler("/failure"));
//        filter.setFilterProcessesUrl("/login");
        //处理要进行登录过滤的链接
        filter.setFilterProcessesUrl("/loginceshi");
        return filter;
    }


    /**
     * 忽略拦截url或静态资源文件夹
     * <p>
     * //     * @param web
     *
     * @throws Exception
     */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(HttpMethod.POST,
//                "/login");
//    }
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
