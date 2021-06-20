package com.lk.demo5.controller;

import com.lk.demo5.utils.HttpResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/failure")
public class FailureController {
    @RequestMapping(method = RequestMethod.GET)
    public HttpResult doGet() throws IOException {
        System.out.println("failure");

//        return "failure page";
        return HttpResult.ok("failure page");
    }
}
