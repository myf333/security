package com.myf.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by myf on 2017/10/25.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("/index")
    public String index(){
        return "admin can see this";
    }
}
