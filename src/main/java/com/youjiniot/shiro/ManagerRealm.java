package com.youjiniot.shiro;

import com.youjiniot.common.Const;
import com.youjiniot.domain.Manager;
import com.youjiniot.service.ManagerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Y on 2017/11/5.
 */
public class ManagerRealm extends AuthorizingRealm {

    @Autowired
    private ManagerService managerService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //add Permission Resources
        info.setStringPermissions(managerService.findPermissions(username));
        //add Roles String[Set<String> roles]
        //info.setRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upt = (UsernamePasswordToken) token;
        String userName = upt.getUsername();
        Manager manager = managerService.findByAccount(userName);
        String password = new String((char[]) token.getCredentials());

        if (manager == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        //加密盐
        //加密盐放在密码的前面
        ByteSource credentialsSalt = ByteSource.Util.bytes(userName+ Const.PASSWORD_SALT_PART);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName,manager.getPassword(),credentialsSalt,getName());

        return info;
    }
}
