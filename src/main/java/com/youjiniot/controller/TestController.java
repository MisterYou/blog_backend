package com.youjiniot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by youjin on 2017/11/6.
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public  Object test(){
        return "111";
    }
}
