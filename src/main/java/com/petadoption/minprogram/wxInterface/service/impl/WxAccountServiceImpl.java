package com.petadoption.minprogram.wxInterface.service.impl;


import cn.hutool.json.JSONUtil;
import com.petadoption.minprogram.dataInterface.entity.Users;
import com.petadoption.minprogram.jwt.JWTConfig;
import com.petadoption.minprogram.dataInterface.service.IUsersService;
import com.petadoption.minprogram.wxInterface.service.IWxAppletService;
import com.petadoption.minprogram.wxInterface.vo.Code2SessionResponse;
import com.petadoption.minprogram.wxInterface.entity.Token;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.time.LocalDateTime;

/**
 * Created by EalenXie on 2018/11/26 10:50.
 * 实体 行为描述
 */
@Service
public class WxAccountServiceImpl implements IWxAppletService {

    @Resource
    private RestTemplate restTemplate;

    @Value("${wx.applet.appid}")
    private String appid;

    @Value("${wx.applet.appsecret}")
    private String appSecret;

    @Autowired
    private IUsersService usersService;

    @Resource
    private JWTConfig jwtConfig;

    /**
     * 微信的 code2session 接口 获取微信用户信息
     * 官方说明 : https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/code2Session.html
     */
    private String code2Session(String jsCode) {
        String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("appid", appid);
        params.add("secret", appSecret);
        params.add("js_code", jsCode);
        params.add("grant_type", "authorization_code");
        URI code2Session = getURIwithParams(code2SessionUrl, params);
        return restTemplate.exchange(code2Session, HttpMethod.GET, new HttpEntity<String>(new HttpHeaders()), String.class).getBody();
    }


    /**
     * 微信小程序用户登陆，完整流程可参考下面官方地址，本例中是按此流程开发
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html
     *
     * @param code 小程序端 调用 wx.login 获取到的code,用于调用 微信code2session接口
     * @return 返回后端 自定义登陆态 token  基于JWT实现
     */
    @Override
    public Token wxUserLogin(String code) {
        //code2session返回JSON数据
        String resultJson = code2Session(code);
        //解析数据
        Code2SessionResponse response = JSONUtil.toBean(resultJson, Code2SessionResponse.class);
        if (!response.getErrcode().equals("0"))
            throw new AuthenticationException("code2session失败 : " + response.getErrmsg());
        else {
            //先从本地数据库中查找用户是否存在
            Users wxAccount = usersService.getUserByWxOpenId(response.getOpenid());

            //不存在就新建用户
            if (wxAccount == null) {
                wxAccount = new Users();
                wxAccount.setUserWxOpenId(response.getOpenid());
                wxAccount.setUserCreateTime(LocalDateTime.now());
                wxAccount.setUserSalt(RandomStringUtils.randomAlphanumeric(20));
            }

            //更新sessionKey和 登陆时间
            wxAccount.setUserSessionKey(response.getSession_key());
            wxAccount.setUserLastLogin(LocalDateTime.now());
            usersService.saveOrUpdate(wxAccount);

            //JWT 返回自定义登陆态 Token
            String token = jwtConfig.createTokenByWxAccount(wxAccount);
            return new Token(token);
        }
    }

    /**
     * 根据url和请求参数获取URI
     */
    public static URI getURIwithParams(String url, MultiValueMap<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParams(params);
        return builder.build().encode().toUri();
    }
}
