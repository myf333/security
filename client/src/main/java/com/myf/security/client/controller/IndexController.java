package com.myf.security.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by myf on 2017/10/27.
 */
@RestController
public class IndexController {

    @RequestMapping("/index")
    public Principal index(Principal user){
        return user;
    }

    @RequestMapping("/login")
    public String login(){
        return "redirect;/#/";
    }
}
