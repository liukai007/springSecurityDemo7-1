package com.lk.demo5.other;

import com.lk.demo5.domain.MobileUser;
import com.lk.demo5.service.MobileUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class MobileAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MobileUserDetailsService mobileUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String mobileNumber = authentication.getName();
        String code = (String) authentication.getCredentials();
        System.out.println("MobileAuthenticationProvider   " + mobileNumber);
        System.out.println("MobileAuthenticationProvider   " + code);

        MobileUser mobileUser = (MobileUser) mobileUserDetailsService.loadUserByUsername(mobileNumber);
        if (!mobileUser.getPassword().equals(code)) {
            throw new IllegalStateException("用户code不一致");
        }

        return new MobileAuthenticationToken(mobileUser.getAuthorities(), mobileNumber, code);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return MobileAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
