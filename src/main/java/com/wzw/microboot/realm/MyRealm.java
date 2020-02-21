package com.wzw.microboot.realm;

import com.wzw.microboot.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    @Override
    public String getName() {
        return  this.getClass().getName();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //这个里面取的就是登陆认证时SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, getName());的第一个参数
        Object user = principalCollection.getPrimaryPrincipal();
        //将其转为自己的用户,在从数据库获取到这个用户的角色  权限
        //MenuService menuService = ApplicationContextRegister.getBean(MenuService.class);
        //这里只查询了权限
        //Set<String> perms = menuService.listPerms(userId);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //放进去即可
        //info.setStringPermissions(perms);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //关于JWT的考虑,密码登陆还是要的,但是要多个Reamle同时用,
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        String username = usernamePasswordToken.getUsername();
        System.out.print("根据username查询yoghurt");
        if("none".equals(username)){
            throw new UnknownAccountException("用户不存在");
        }
        //认证的实体信息, 可以放对象,以后可以随时取出来用,  JSP标签也可以直接去这个对象
        Object principal = username;
        //数据库中获取的密码
        Object credentials = "1234567";
        //封装一个带数据的对象,Shiro会拿这个对象和传进来的Token的密码进行对比,验证是否登录成功
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, credentials, getName());
        return simpleAuthenticationInfo;

    }
}
