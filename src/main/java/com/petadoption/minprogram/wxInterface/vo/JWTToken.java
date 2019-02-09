package com.petadoption.minprogram.wxInterface.vo;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 鉴权用的token vo ，实现 AuthenticationToken
 */
public class JWTToken implements AuthenticationToken {

    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
