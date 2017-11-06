package com.youjiniot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by youjin on 2017/11/6.
 */
@Controller
public class LoginController extends BaseController {

    @RequestMapping("/")
    String welcome(){
        return "redirect:/login";
    }

    @RequestMapping("/to_login")
    String to_login(){
        return "login";
    }
    @RequestMapping("/login")
    @ResponseBody
    String login(){
        return "login";
    }

}
