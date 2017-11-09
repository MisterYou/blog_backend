package com.youjiniot.controller;

import com.youjiniot.utils.PageUtils;
import com.youjiniot.utils.Query;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by youjin on 2017/11/9.
 */
@Controller
@RequestMapping("/sys/notify")
public class NotifyController {

    @RequestMapping("/")
    @RequiresPermissions("sys:notify:notify")
    public Object sysNotify(){
        return "sys/notify/notify";
    }


    @RequestMapping("/message")
    @ResponseBody
    public Object message(){
        Map<String, Object> params = new HashMap<>(16);
        params.put("offset", 0);
        params.put("limit", 3);
        Query query = new Query(params);

        return "111";
    }


}
