package com.youjiniot.controller;

import com.youjiniot.domain.Role;
import com.youjiniot.service.RoleService;
import com.youjiniot.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return prefix + "/role";
    }

    @RequiresPermissions("sys:role:role")
    @RequestMapping("/list")
    @ResponseBody()
    Object list() {
        List<Role> list = roleService.list();
        return list;
    }

    /**
     * 添加角色
     * @return
     */
    @RequiresPermissions("sys:role:add")
    @RequestMapping("/add")
    String add() {
        return prefix + "/add";
    }

    /**
     * 编辑角色
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("sys:role:edit")
    @RequestMapping("/edit/{id}")
    String edit(@PathVariable("id") Long id, Model model) {
        Role roleDO = roleService.get(id);
        model.addAttribute("role", roleDO);
        return prefix + "/edit";
    }


    @RequiresPermissions("sys:role:edit")
    @RequestMapping("/update")
    @ResponseBody()
    R update(Role role) {
//        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
//            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
//        }
        if (roleService.update(role) > 0) {
            return R.ok();
        } else {
            return R.error(1, "保存失败");
        }
    }

    @RequiresPermissions("sys:role:remove")
    @PostMapping("/remove")
    @ResponseBody()
    R save(Long id) {
//        if (Constant.DEMO_ACCOUNT.equals(getUsername())) {
//            return R.error(1, "演示系统不允许修改,完整体验请部署程序");
//        }
        if (roleService.delete(id) > 0) {
            return R.ok();
        } else {
            return R.error(1, "删除失败");
        }
    }


}
