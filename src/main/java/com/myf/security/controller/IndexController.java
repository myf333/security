package com.myf.security.controller;

import com.myf.security.model.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by myf on 2017/10/20.
 */
@Controller
@RequestMapping("/security")
public class IndexController {
    @RequestMapping("/index")
    public String index(Principal principal, Model model){
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        SecurityUser user =  (SecurityUser) auth.getPrincipal();
        model.addAttribute("user",user);
        return  "index";
    }
}
