package com.dhj.springcloud.filter;

import com.alibaba.fastjson.JSON;
import com.dhj.springcloud.dao.entity.SysUser;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 所有通过网关的请求到这里处理
 * 带token的进行解析，解析后发送微服务
 */
public class AuthFilter extends ZuulFilter {
   //请求之前拦截
    @Override
    public String filterType() {
        return "pre";
    }
    //最优先拦截
    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        try {
            return postTokenToServiceByRedis();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * //获取当前用户的身份信息
     * //huoqu当前用户的权限信息
     * //把身份信息和权限信息放在json串中进行base64编码转发给资源服务
     * redis token 转发明文给微服务
     * @return
     */
    private Object postTokenToServiceByRedis() throws UnsupportedEncodingException {
        //获取令牌
        //获取request上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取身份信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof OAuth2Authentication)){//uaa会走这一段代码，其它服务都需要含有认证信息
            // 无token访问资源的情况（认证中心）
            return null;
        }
        OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
        String clientId = auth2Authentication.getOAuth2Request().getClientId();
        Authentication userAuthentication = auth2Authentication.getUserAuthentication();
        Object principal = userAuthentication.getPrincipal();
        //Authentication userAuthentication = ((OAuth2Authentication) authentication).getUserAuthentication();
        String userInfoStr = clientId;
        //取出用户权限信息
        List<String> authorities = new ArrayList<>();
        if(userAuthentication != null){
            //获取用户信息
             userInfoStr = userAuthentication.getName();
            //userInfoStr = JSON.toJSONString(loginUser);
            userAuthentication.getAuthorities().stream().forEach(
                    s->authorities.add(((GrantedAuthority) s).getAuthority())
            );
        }

        // 组装明文token，转发给微服务，放入header，名称问json-token
        //获取请求的信息参数也要一起带着
        OAuth2Request oAuth2Request = auth2Authentication.getOAuth2Request();
        Map<String, String> requestParams = oAuth2Request.getRequestParameters();
        Map<String, Object> jsonToken = new HashMap<>(requestParams);
        jsonToken.put("principal", userInfoStr);
        jsonToken.put("authorities", authorities);
        System.out.println("***************:"+ Base64.getEncoder().encodeToString(JSON.toJSONString(jsonToken).getBytes("utf-8")));
        //将身份信息和权限信息放在请求头中进行转发
        ctx.addZuulRequestHeader("json-token",
                Base64.getEncoder().encodeToString(JSON.toJSONString(jsonToken).getBytes("utf-8"))
        );
        return null;
    }

    /**
     * jwt token 转发明文给微服务
     * @return
     */
    private Object postTokenToServiceByJwt() throws UnsupportedEncodingException {
        //获取令牌
        RequestContext ctx = RequestContext.getCurrentContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof OAuth2Authentication)){
            // 无token访问资源的情况（认证中心）
            return null;
        }
        OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
        OAuth2AuthenticationDetails oAuth2AuthenticationDetails = (OAuth2AuthenticationDetails) auth2Authentication.getDetails();
        String token = oAuth2AuthenticationDetails.getTokenValue();
        String clientId = auth2Authentication.getOAuth2Request().getClientId();
        Authentication userAuthentication = ((OAuth2Authentication) authentication).getUserAuthentication();
        if(userAuthentication == null){
            //通过用户名和密码的认证
            //检查token是否有效
            checkToken(clientId, token);
            return null;
        }
        Long userId = 0L;
        String userInfoStr = clientId;
        if(userAuthentication != null){
            userInfoStr = userAuthentication.getPrincipal().toString();
            SysUser loginUser = JSON.parseObject(userInfoStr, SysUser.class);
            userId = loginUser.getId();
        }
        String key = clientId + "-" + userId;
        //检查token是否有效
        checkToken(key, token);

        // 组装明文token，转发给微服务，放入header，名称问json-token
        List<String> authorities = new ArrayList<>();
        userAuthentication.getAuthorities().stream().forEach(
                s->authorities.add(((GrantedAuthority) s).getAuthority())
        );
        OAuth2Request oAuth2Request = auth2Authentication.getOAuth2Request();
        Map<String, String> requestParams = oAuth2Request.getRequestParameters();
        Map<String, Object> jsonToken = new HashMap<>(requestParams);
        jsonToken.put("principal", userInfoStr);
        jsonToken.put("authorities", authorities);
        ctx.addZuulRequestHeader("json-token",
                Base64.getEncoder().encodeToString(JSON.toJSONString(jsonToken).getBytes("utf-8"))
        );
        return null;
    }

    /**
     * 检查token是否有效
     * @param key
     * @param token
     */
    private void checkToken(String key, String token){
//        String redisToken = accessTokenRedis.get(key);
//        if(!StringUtils.equals(redisToken, token)){
//            throw new RuntimeException(STExceptionCode.INVALID_TOKEN.getMsg());
//        }
    }
}
