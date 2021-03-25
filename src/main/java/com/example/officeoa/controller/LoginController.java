package com.example.officeoa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: LiXinYe
 * @Description:
 * @Date: Create in 20:07 2021/3/7
 */
@Controller
public class LoginController {

    @RequestMapping("/index")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/login")
    public String toLogin(){
        return "login";
    }
}
