package com.youjiniot.controller;

import com.youjiniot.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Y on 2017/11/27.
 */
@RequestMapping("/sys/role")
@Controller
public class RoleController {
    String prefix = "role";

    @Autowired
    private RoleService roleService;

    @RequiresPermissions("sys:role:role")
    @GetMapping()
    String role() {
        return prefix + "/role";
    }



}
