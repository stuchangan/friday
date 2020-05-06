package com.friday.friday.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SecurityController {


    @GetMapping(value = {"/login.html"})
    @ApiOperation(value = "返回登录页面",notes = "返回登录页面")
    public String login(){
        return "login";
    }

    @RequestMapping(value = {"/403.html"},method = RequestMethod.GET)
    @ApiOperation(value = "返回错误页面",notes = "返回错误页面")
    public String noPermission(){
        return "403";
    }
}
