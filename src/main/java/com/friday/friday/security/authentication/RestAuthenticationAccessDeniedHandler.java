package com.friday.friday.security.authentication;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //设置403错误状态码httpServletResponse.SC_FORBIDDEN
        httpServletResponse.setStatus(httpServletResponse.SC_FORBIDDEN);
        //设置编码集
        httpServletResponse.setCharacterEncoding("utf-8");
        //设置重定向
        httpServletResponse.sendRedirect("/403.html");

    }
}
