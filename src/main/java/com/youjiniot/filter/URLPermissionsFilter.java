package com.youjiniot.filter;

import com.google.common.collect.ImmutableMap;
import com.youjiniot.service.ManagerService;
import com.youjiniot.utils.WebUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by youjin on 2017/11/6.
 */
@Component("urlPermissionsFilter")
public class URLPermissionsFilter extends PermissionsAuthorizationFilter {
    @Autowired
    private ManagerService userService;

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        HttpServletResponse resp = (HttpServletResponse)response;
//        resp.addHeader("Access-Control-Allow-Origin", "http://182.135.64.132:100");
        resp.addHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8082");

        resp.addHeader("Access-Control-Allow-Credentials", "true");

        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        String curUrl = getRequestUrl(request);

        //将url中的参数去掉
        curUrl=curUrl.split("\\?")[0];
        Subject subject = SecurityUtils.getSubject();

        //不需要权限验证的情况
        if(StringUtils.endsWithAny(curUrl, ".js",".css",".html",".css.map",".ico")
                || StringUtils.endsWithAny(curUrl, ".jpg",".png",".gif", ".jpeg")
                || StringUtils.endsWithAny(curUrl, ".woff2",".woff",".ttf")
                || StringUtils.equals(curUrl, "/unauthor")
                || StringUtils.indexOf(curUrl, "/login")>=0
                || StringUtils.indexOf(curUrl, "/to_login")>=0
                || StringUtils.indexOf(curUrl, "/ajax")>=0
                || StringUtils.indexOf(curUrl, "/logout")>=0) {
            return true;
        }

        //未登陆的情况
        if(subject.getPrincipal() == null){
            return false;
        }
        if(StringUtils.indexOf(curUrl, "/info")>=0){
            return true;
        }
        //如果是系统管理员，直接放过
        if(subject.getPrincipal().toString().equalsIgnoreCase("admin")){
            return true;
        }

        List<String> urls = userService.findPermissionUrl(subject.getPrincipal().toString());


        return urls.contains(curUrl);

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Subject subject = getSubject(request, response);

        if (subject.getPrincipal() == null) {
            if (!WebUtils.isAjax(httpRequest)) {

                WebUtils.writeJson(httpResponse, ImmutableMap.of("errcode",302,"errmsg","未登陆"));


            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
        } else {
            if (!WebUtils.isAjax(httpRequest)) {

                WebUtils.writeJson(httpResponse, ImmutableMap.of("errcode",503,"errmsg","无操作权限"));
            } else {
                String unauthorizedUrl = getUnauthorizedUrl();
                if (org.apache.shiro.util.StringUtils.hasText(unauthorizedUrl)) {
                    org.apache.shiro.web.util.WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    org.apache.shiro.web.util.WebUtils.toHttp(response).sendError(401);
                }
            }
        }
        return false;
    }

    /**
     * 获取当前URL+Parameter
     * @author lance
     * @since  2014年12月18日 下午3:09:26
     * @param request	拦截请求request
     * @return			返回完整URL
     */
    private String getRequestUrl(ServletRequest request) {

        HttpServletRequest req = (HttpServletRequest)request;
        String queryString = req.getQueryString();

        queryString = StringUtils.isBlank(queryString)?"": "?"+queryString;
        return req.getServletPath()+queryString;
    }


}
