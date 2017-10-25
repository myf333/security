package com.myf.security.controller;

import com.myf.security.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by myf on 2017/10/20.
 */
@RestController
@RequestMapping("/security")
public class IndexController {
    @RequestMapping("/index")
    public SecurityUser index(Principal principal){
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        return (SecurityUser) auth.getPrincipal();
    }
}
