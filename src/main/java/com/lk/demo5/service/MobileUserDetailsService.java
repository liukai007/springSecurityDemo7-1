package com.lk.demo5.service;

import com.lk.demo5.domain.MobileUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class MobileUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if (MobileUser.map.isEmpty() || !MobileUser.map.keySet().contains(s)) {
            throw new IllegalStateException("用户名不存在");
        }
        String code = MobileUser.map.get(s);
//        Collection<? extends GrantedAuthority> authorities = Collections.EMPTY_LIST;
        //自定义一个权限
        Collection<? extends GrantedAuthority> authorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_r1,/success");
        return new MobileUser(s, code, authorities);
    }
}
