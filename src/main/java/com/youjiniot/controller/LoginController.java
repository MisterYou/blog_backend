package com.youjiniot.controller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.youjiniot.annotation.Before;
import com.youjiniot.domain.Manager;
import com.youjiniot.service.ManagerService;
import com.youjiniot.vaildator.ManagerValidator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by youjin on 2017/11/6.
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    ManagerService managerService;

    @RequestMapping("/")
    public Object welcome(){
        return "redirect:/login";
    }

    @RequestMapping("/to_login")
    public Object to_login(){
        return "login";
    }


    @RequestMapping("/login")
    @ResponseBody
    @Before(ManagerValidator.class)
    public  Object login(HttpServletRequest request){
        String account=request.getParameter("username").toString();
        String password=request.getParameter("password").toString();
        UsernamePasswordToken upt = new UsernamePasswordToken(account, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(upt);
            //获取用户信息

            Manager manager=managerService.findByAccount(subject.getPrincipal().toString());

//            if(manager.getKey()!=null&&!manager.getKey().isEmpty()){
//                //获取用户的权限列表
//                Set<String> permissions= managerService.findPermissions(account);
//                return ImmutableMap.of("permissions",permissions,"name",manager.getName());
//            }
            return ImmutableMap.of("name",manager.getName(),"code","0");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            //登录失败
            return ImmutableMap.of("code",1,"msg","用户名或密码错误");
        }

    }

    @RequestMapping("index")
    public Object index(Model model){
        Set<String> permissions= managerService.findPermissions(getAccount());

        model.addAttribute("menu",permissions);

        model.addAttribute("name",getAccount());

        return "index";
    }

}
