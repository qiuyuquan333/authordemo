package com.qyq.authordemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PathController {

    @GetMapping("/index")
    public String indexController(){
        return "login.html";
    }
}
