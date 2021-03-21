package com.dhj.springcloud.security.resource.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dhj.springcloud.dao.entity.SysUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * 解析从网关发送的token,将认证的用户信息及权限存放在安全上下文中
 */
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("json-token");
        if(token != null){
            String json = new String(Base64.getDecoder().decode(token), "utf-8");
            JSONObject userJson = JSON.parseObject(json);
            //客户端模式没经过用户名密码登录，所以得不到Loginuser信息
            if(!"client_credentials".equals(userJson.getString("grant_type"))){
                String principal = userJson.getString("principal");
                //转换为loginuser
                SysUser s = new SysUser();
                s.setUsername(principal);
                JSONArray authoritiesArray = userJson.getJSONArray("authorities");
                String[]authorities = authoritiesArray.toArray(new String[authoritiesArray.size()]);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(s, null, AuthorityUtils.createAuthorityList(authorities));
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
