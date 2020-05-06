package com.friday.friday.security;


import com.friday.friday.security.authentication.MyAuthenticationFailureHandler;
import com.friday.friday.security.authentication.MyAuthenticationSuccessHandler;
import com.friday.friday.security.authentication.MyLogoutSuccessHandler;
import com.friday.friday.security.authentication.RestAuthenticationAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();//配置跨站请求身份伪造 不允许
        //解决'X-Frame-Options' to 'deny'.
        http.headers().frameOptions().sameOrigin();
        //开始请求权限配置()anyRequest().authenticated();任何请求都需要验证
        http.authorizeRequests()
                //配置那些路径下的请求不需要验证（例如静态文件）
                .antMatchers("/login.html",
                        "/my/**",
                        "/static/**",
                        "/treetable-lay/**",
                        "/xadmin/**",
                        "/ztree/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        //指定登录页面
        //配置登录、退出相关内容
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")//指定拦截请求可自定义
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and().logout()
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(myLogoutSuccessHandler);

        //异常处理（返回403状态码）
        http.exceptionHandling().accessDeniedHandler(restAuthenticationAccessDeniedHandler);
    }
}
