package com.itheima.reggie_take_out.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie_take_out.common.BaseContext;
import com.itheima.reggie_take_out.common.CommonReturn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author UMP90
 * @date 2021/10/12
 */
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
@Slf4j

public class loginCheckFilter implements Filter {
    public static final AntPathMatcher pathMatcher = new AntPathMatcher();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        String[] unfilteredUrl = {
                "/**/js/**/*",
                "/**/images/**/*",
                "/**/api/*.js",
                "/**/plugins/**/*",
                "/**/styles/**/*",
                "/**/login/**/*",
                "/**/login",
                "/**/login.html",
                "/**/fonts/**/*",
                "/**/sendMsg"
        };
        log.info("请求 URI:" + requestURI);
        if (checkUrl(requestURI, unfilteredUrl)) {
            log.info("URI: " + requestURI + " 不需要拦截");
            filterChain.doFilter(request, response);
        } else {
            Long employeeId = (Long) request.getSession().getAttribute("employee");
            Long userId = (Long) request.getSession().getAttribute("user");
            if (employeeId != null) {
                log.info("URI: " + requestURI + "Employee " + employeeId + " 已登录，放行请求");
                Long id = (Long) request.getSession().getAttribute("employee");
                BaseContext.setId(id);
                filterChain.doFilter(request, response);
            } else if (userId != null) {
                log.info("URI: " + requestURI + "User " + userId + " 已登录，放行请求");
                Long id = (Long) request.getSession().getAttribute("user");
                BaseContext.setId(id);
                filterChain.doFilter(request, response);
            } else {
                log.info("URI: " + requestURI + " 未登录，拦截请求");
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().println(JSON.toJSONString(CommonReturn.error("NOTLOGIN")));
            }
        }


    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public boolean checkUrl(String testUrl, String[] urls) {
        System.out.println(testUrl);
        for (String url : urls) {

            if (pathMatcher.match(url, testUrl)) {
                return true;
            }
        }

        return false;
    }
}
