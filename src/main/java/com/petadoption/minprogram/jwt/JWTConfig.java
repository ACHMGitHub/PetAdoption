package com.petadoption.minprogram.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.petadoption.minprogram.dataInterface.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JWTConfig {

    /**
     * JWT 自定义密钥 我这里写死的
     */
//    private static final String SECRET_KEY = "2c7e20f4ecb34f3395350b0e95ddbd0c";

    /**
     * JWT 过期时间值 这里写死为和小程序时间一致 7200 秒，也就是两个小时
     */
    private static long expire_time = 7200;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据微信用户登陆信息创建 token
     * 注 : 这里的token会被缓存到redis中,用作为二次验证
     * redis里面缓存的时间应该和jwt token的过期时间设置相同
     *
     * @return 返回 jwt token
     */
    public String createTokenByWxAccount(Users users) {
        //用户uuid作为key值，防止重复
        String jwtId = users.getUuid();
        //加密算法进行签名得到token
        Algorithm algorithm = Algorithm.HMAC256(users.getUserSalt());
        String token = JWT.create()
                .withClaim("wxOpenId", users.getUserWxOpenId())
                .withClaim("sessionKey", users.getUserSessionKey())
                .withClaim("jwt-id", jwtId)
                .withExpiresAt(new Date(System.currentTimeMillis() + expire_time * 1000))  //JWT 配置过期时间
                .sign(algorithm);
        //Redis缓存JWT, 注 : 请和JWT过期时间一致
        redisTemplate.opsForValue().set("JWT-SESSION-" + jwtId, token, expire_time, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 校验token是否正确
     * 根据token解密，解密出jwt-id , 先从redis中查找出redisToken，匹配是否相同
     * 然后再对redisToken进行解密，解密成功则 继续流程 和 进行token续期
     *
     * @param token 密钥
     * @return 返回是否校验通过
     */
    public boolean verifyToken(String token, String secretKey) {
        try {
            //根据token解密，解密出jwt-id , 先从redis中查找出redisToken，匹配是否相同
            String redisToken = redisTemplate.opsForValue().get("JWT-SESSION-" + getJwtIdByToken(token));
            if (!redisToken.equals(token)) return false;

            //得到算法相同的JWTVerifier
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("wxOpenId", getWxOpenIdByToken(redisToken))
                    .withClaim("sessionKey", getSessionKeyByToken(redisToken))
                    .withClaim("jwt-id", getJwtIdByToken(redisToken))
                    .acceptExpiresAt(System.currentTimeMillis() + expire_time * 1000)  //JWT 正确的配置续期姿势
                    .build();
            //验证token
            verifier.verify(redisToken);
            //Redis缓存JWT续期
            redisTemplate.opsForValue().set("JWT-SESSION-" + getJwtIdByToken(token), redisToken, expire_time, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) { //捕捉到任何异常都视为校验失败
            return false;
        }
    }

    /**
     * 根据Token获取wxOpenId(注意坑点 : 就算token不正确，也有可能解密出wxOpenId,同下)
     */
    public String getWxOpenIdByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaim("wxOpenId").asString();
    }

    /**
     * 根据Token获取sessionKey
     */
    public String getSessionKeyByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaim("sessionKey").asString();
    }

    /**
     * 根据Token 获取jwt-id
     */
    private String getJwtIdByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaim("jwt-id").asString();
    }
}
