package com.myf.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by myf on 2017/10/25.
 */
@RestController
@RequestMapping("/public")
public class PublicController {
    @RequestMapping("/index")
    public String index(){
        return "anyone can see this";
    }

    @RequestMapping("/deny")
    public String deny(){
        return "not allowed";
    }
}
