package com.lk.demo5.service;

import com.lk.demo5.other.MobileAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

@Service
public class MyAccessServiceImpl implements MyAccessService {
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        if (authentication instanceof MobileAuthenticationToken){
          return   authentication.getAuthorities().contains(new SimpleGrantedAuthority(request.getRequestURI()));
        }

//        Object object = authentication.getPrincipal();
//        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (object instanceof UserDetailsService) {
//            UserDetails userDetails = (UserDetails) object;
//            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//            return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
//        }
        return false;
    }
}
