package com.lk.demo5.controller;

import com.lk.demo5.utils.HttpResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/success")
public class SuccessController {
//    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    @PreAuthorize("hasAuthority('/success')")
    @RequestMapping(method = RequestMethod.GET)
    public HttpResult doGet() throws IOException {
        System.out.println("success");

//        return "success page";
        return HttpResult.ok("success page");
    }
}
