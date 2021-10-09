package com.zhangzhuorui.framework.rbacsystem.config;

import com.alibaba.fastjson.JSON;
import com.zhangzhuorui.framework.rbacsystem.entity.ZtUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class ZtJwtTokenUtil implements Serializable, BeanPostProcessor {

    @Autowired
    protected HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public final static List<String> IGNORE_URLS = new ArrayList<>();

    static {
        IGNORE_URLS.add("/ZtIndex/login");
        IGNORE_URLS.add("/ZtIndex/getUserInfoAfterLogin");
    }

    private final static String CLAIMS_USER_ID = "userId";

    private final static String CLAIMS_ISSUER = "ZtFramework";

    private final static String DEFAULT_JWT_SECRET = "6dJ.(U~az]:ose3";
    //默认一天
    private final static Long DEFAULT_JWT_EXPIRATION = 24 * 60 * 60 * 1000L;

    @Value("${jwt.ignoreUrls:#{null}}")
    private String ignoreUrls;

    //    @Value("#{'${jwt.ignoreUrl:}'.split(',')}")
    private List<String> ignoreUrlLists = new LinkedList<>();

    public List<String> getIgnoreUrlLists() {
        return ignoreUrlLists;
    }

    //        @Value("#{T(io.jsonwebtoken.impl.TextCodec).BASE64.encode('${jwt.secret:xxx}')}")
    @Value("${jwt.secret:#{null}}")
    private String secret;
    private String realSecret;

    public String getSecret() {
        return realSecret;
    }

    @Value("${jwt.expiration:#{null}}")
    private Long expiration;
    private Long realExpiration;

    public Long getExpiration() {
        return realExpiration;
    }

    @Value("${tokenName:token}")
    private String tokenHeader;

    public String getTokenHeader() {
        return tokenHeader;
    }

    @PostConstruct
    void init() {
        if (secret == null) {
            secret = DEFAULT_JWT_SECRET;
        }
        realSecret = TextCodec.BASE64.encode(secret);
        if (expiration == null) {
            expiration = DEFAULT_JWT_EXPIRATION;
        }
        realExpiration = expiration;
        if (ignoreUrls != null) {
            ignoreUrlLists.addAll(Arrays.asList(ignoreUrls.split(",")));
        }
    }

    public ZtUserInfo getSimpleUserInfoFromToken() {
        String token = getRequest().getHeader(getTokenHeader());
        ZtUserInfo userInfoFromToken = getUserInfoFromToken(token);
        return userInfoFromToken;
    }

    public String generateToken(ZtUserInfo ztUserInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_USER_ID, ztUserInfo.getId());
        ZtUserInfo subject = new ZtUserInfo();
        subject.setId(ztUserInfo.getId());
        subject.setUserCode(ztUserInfo.getUserCode());
        subject.setAscFlag(null);
        return doGenerateToken(claims, JSON.toJSONString(subject));
    }

    public ZtUserInfo getUserInfoFromToken(String token) {
        Claims allClaimsFromToken = getAllClaimsFromToken(token);
        String subject = allClaimsFromToken.getSubject();
        //这个类是个通用类，可以放在通用的模块里，这里不要注入查询数据库的service，只能用缓存
        ZtUserInfo ztUserInfo = JSON.parseObject(subject, ZtUserInfo.class);
        return ztUserInfo;
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        return Jwts.builder()
                .setId(String.valueOf(claims.get(CLAIMS_USER_ID)))
                .setAudience("user")
                // 建议不要修改默认的claims
                // .setClaims(claims)
                .setSubject(subject)
                .setIssuer(CLAIMS_ISSUER)
                .setIssuedAt(createdDate)
                .setNotBefore(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, getSecret())
                .compact();
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + getExpiration());
    }

    public Boolean validateToken(String token) {
        return isTokenExpired(token);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser().setSigningKey(getSecret()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw e;
        }
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean needNewToken(String token) {
        //当前时间
        long now = (new Date()).getTime();
        //失效时间
        long expiration = getClaimFromToken(token, Claims::getExpiration).getTime();
        //失效时间一定要比当前时间晚，否则就过期了 remain
        long remain = expiration - now;
        //失效时间小于一个小时的时候更新token
        return remain < 60 * 60 * 1000L;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /*
    public static void main(String[] args) {
        ZtUserInfo ztUserInfo = new ZtUserInfo();
        ztUserInfo.setId(2L);
        ztUserInfo.setUserCode("zhangtao");
        ztUserInfo.setAscFlag(null);
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_USER_ID, ztUserInfo.getId());
        final Date createdDate = new Date();
        final Date expirationDate = new Date(createdDate.getTime() + DEFAULT_JWT_EXPIRATION);

        String token = Jwts.builder()
                .setId(String.valueOf(ztUserInfo.getId()))
                .setAudience("user")
                // .setClaims(claims)
                .setSubject(JSON.toJSONString(ztUserInfo))
                .setIssuer(CLAIMS_ISSUER)
                .setIssuedAt(createdDate)
                .setNotBefore(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, DEFAULT_JWT_SECRET)
                .compact();
        System.out.println(token);

        Claims body = Jwts.parser()
                .setSigningKey(DEFAULT_JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        //.setClaims(claims)会导致body.getId()结果为null。看getId源码key:jti
        String id = body.getId();
        String subject = body.getSubject();
        ZtUserInfo ztUserInfo1 = JSON.parseObject(subject, ZtUserInfo.class);
        System.out.println();
    }
     */

}
