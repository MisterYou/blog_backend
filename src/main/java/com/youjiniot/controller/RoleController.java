package com.youjiniot.controller;

import com.youjiniot.domain.Role;
import com.youjiniot.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    @RequestMapping("")
    String role() {
        System.out.println("1111");
        return prefix + "/role";
    }

    @RequiresPermissions("sys:role:role")
    @RequestMapping("/list")
    @ResponseBody()
    Object list() {
        List<Role> list = roleService.list();
        return list;
    }

    @RequiresPermissions("sys:role:add")
    @RequestMapping("/add")
    String add() {
        return prefix + "/add";
    }

    @RequiresPermissions("sys:role:edit")
    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        Role roleDO = roleService.get(id);
        model.addAttribute("role", roleDO);
        return prefix + "/edit";
    }


}
