package com.cf.filter;

import com.alibaba.fastjson2.JSON;
import com.cf.common.CommonResult;
import com.cf.common.ResponseCode;
import com.cf.config.JWTProperties;
import com.cf.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Order(1) // 当有多个filter执行时，进行处理排序
// 在不指定UrlPatterns的情况下，默认拦截所有请求。可使用@WebFilter注解或WebMvcConfig声明拦截哪些请求
public class TokenFilter implements Filter {

    @Autowired
    private JWTProperties jwtProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    //过滤方法
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //先将其转成HttpServletRequest
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
//        log.info("拦截到请求：" + requestURI);
        // 如果关闭token检查，则直接放行
        if(!jwtProperties.isEnable()){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // url白名单校验
        String whiteUrls = jwtProperties.getWhiteUrls(); //报名单规则
        if(StringUtils.isNotEmpty(whiteUrls) && isMatch(whiteUrls,requestURI)){ // 如果设置了白名单，则校验是否命中白名单，命中则放行，否则继续走验证
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 验证是否有token信息
        String token = request.getHeader(jwtProperties.getHeader());
        if(StringUtils.isEmpty(token)){ // 认证信息一定要有
            resp(servletResponse);
            return;
        }
        // 验证token是否满足白名单策略
        String tokenWhiteListString = jwtProperties.getWhiteTokens();
        if(StringUtils.isNotEmpty(tokenWhiteListString)){
            if(!tokenWhiteListString.contains(token)){
                resp(servletResponse);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符
     * * 表示一层路径内的任意字符串，不可跨层级
     * ** 表示任意层路径
     *
     * @param urlRule     匹配规则
     * @param urlPath 需要匹配的url
     * @return
     */
    public boolean isMatch(String urlRule, String urlPath) {
        if(StringUtils.isNotEmpty(urlRule)){
            AntPathMatcher matcher = new AntPathMatcher();
            String[] urlTemp = urlRule.split(","); // 当没有逗号时，urlTemp长度为1
            for (String s: urlTemp){
                if(matcher.match(s, urlPath)){ // 只要匹配上，则直接结束
                    return true;
                }
            }
        }
        return false;
    }

    //相应方法封装
    private void resp(ServletResponse servletResponse) throws IOException {
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        ServletOutputStream outputStream = response.getOutputStream();
        response.setStatus(401);
        response.setContentType("application/json;charset:utf-8");
        outputStream.write(JSON.toJSONString(new CommonResult<>(ResponseCode.AuthFail.getCode(), ResponseCode.AuthFail.getMessage(), null)).getBytes(StandardCharsets.UTF_8));
    }
}

