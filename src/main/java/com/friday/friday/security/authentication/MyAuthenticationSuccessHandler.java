package com.friday.friday.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component//@component （把普通pojo实例化到spring容器中，相当于配置文件中的）
// 泛指各种组件，就是说当我们的类不属于各种归类的时候
// （不属于@Controller、@Services等的时候），我们就可以使用@Component来标注这个类。
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        log.info("登录成功");
        //表明当前页面可以跨域访问。默认是不允许的。
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        /* 星号表示所有的域都可以接受， */
        httpServletResponse.setHeader("Access-Control-Allow-Methods","*");
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpStatus.OK.value());
        //System.out.println(objectMapper.writeValueAsString(authentication));
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authentication));
    }
}
