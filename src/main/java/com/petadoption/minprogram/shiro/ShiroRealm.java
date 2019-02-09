package com.petadoption.minprogram.shiro;

import com.petadoption.minprogram.jwt.JWTConfig;
import com.petadoption.minprogram.wxInterface.vo.JWTToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private JWTConfig jwtConfig;

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return new SimpleAuthorizationInfo();
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        String jwtToken = (String) token.getCredentials();
        String wxOpenId = jwtConfig.getWxOpenIdByToken(jwtToken);
        String sessionKey = jwtConfig.getSessionKeyByToken(jwtToken);
        if (wxOpenId == null || wxOpenId.equals(""))
            throw new AuthenticationException("user account not exits , please check your token");
        if (sessionKey == null || sessionKey.equals(""))
            throw new AuthenticationException("sessionKey is invalid , please check your token");
        if (!jwtConfig.verifyToken(jwtToken))
            throw new AuthenticationException("token is invalid , please check your token");
        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * 告诉shiro如何根据获取到的用户信息中的密码和盐值来校验密码
     * 注意坑点 : 密码校验 , 这里因为是JWT形式,就无需密码校验和加密,直接让其返回为true(如果不设置的话,该值默认为false,即始终验证不通过)
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher((token, info) -> true);
    }
}
