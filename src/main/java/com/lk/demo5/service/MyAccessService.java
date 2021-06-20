package com.lk.demo5.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface MyAccessService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
