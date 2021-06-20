package com.lk.demo5.domain;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/*
* 可以参考这个连接里面的类
* https://www.cnblogs.com/zwqh/p/11934880.html
* */
public class MobileUser extends User {
    public final static Map<String, String> map = new HashMap<>();

    static {
        map.put("admin", "123456");
        map.put("zhangsan", "234567");
        map.put("lisi", "345678");
    }

    public MobileUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
