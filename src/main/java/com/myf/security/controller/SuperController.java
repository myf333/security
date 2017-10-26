package com.myf.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by myf on 2017/10/26.
 */
@RestController
@RequestMapping("/super")
public class SuperController {
    @RequestMapping("index")
    public String index(){
        return "super can see this";
    }
}
