package com.friday.friday.controller;

import com.friday.friday.base.result.PageTableRequest;
import com.friday.friday.base.result.ResponseCode;
import com.friday.friday.base.result.Results;
import com.friday.friday.dto.UserDto;
import com.friday.friday.model.SysUser;
import com.friday.friday.service.UserService;

import com.friday.friday.util.MD5;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;



    @GetMapping(value = {"/list"})
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:query')")//springSecurity认证注解
    //swagger接口文档注解
    @ApiOperation(value = "分页获取用户信息",notes = "分页获取用户信息")//描述
    @ApiImplicitParam(name = "request",value = "分页查询实体类",required = false)
    public Results<SysUser> getUsers(PageTableRequest pageTableRequest){
        log.info("UserController.getUsers():param(pageTableRequest=" + pageTableRequest +")");
        pageTableRequest.countOffSet();
        /*System.out.println(pageTableRequest.getOffset());
        System.out.println(pageTableRequest.getLimit());*/
        return userService.getAllUserByPage(pageTableRequest.getOffset(),pageTableRequest.getLimit());

    }

    @GetMapping(value = {"/add"})
    @PreAuthorize("hasAuthority('sys:user:add')")
    @ApiOperation(value = "用户新增页面", notes = "跳转到新增用户信息页面")//描述
    public String addUser(Model model){
        model.addAttribute(new SysUser());
        return "/user/user-add";
    }

    @PostMapping(value = {"/save"})
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:add')")
    @ApiOperation(value = "保存用户信息", notes = "保存新增的用户信息")//描述
    public Results<SysUser> saveUser(UserDto userDto,Integer roleId){
        SysUser sysUser = null;
        sysUser = userService.getUserByPhone(userDto.getTelephone());
        if (sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());
        }
        userDto.setStatus(1);
        //userDto.setPassword(MD5.crypt(userDto.getPassword()));
        //改变密码加密方式
        userDto.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        System.out.println(userDto);
        return userService.save(userDto,roleId);
    }


    @GetMapping(value = {"/edit"})
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @ApiOperation(value = "用户编辑页面", notes = "跳转到用户信息编辑页面")//描述
    @ApiImplicitParam(name = "user", value = "用户实体类", dataType = "SysUser")
    public String addUser(Model model,SysUser sysUser){
        model.addAttribute(userService.getUserById(sysUser.getId()));
        return "/user/user-edit";
    }

    @PostMapping(value = {"/edit"})
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @ApiOperation(value = "保存用户信息", notes = "保存编辑完的用户信息")//描述
    public Results<SysUser> updateUser(UserDto userDto,Integer roleId){
        SysUser sysUser = null;
        sysUser = userService.getUserByPhone(userDto.getTelephone());
        if (sysUser != null && !(sysUser.getId().equals(userDto.getId()))){
            return Results.failure(ResponseCode.PHONE_REPEAT.getCode(),ResponseCode.PHONE_REPEAT.getMessage());
        }

        return userService.updateUser(userDto,roleId);
    }

    @GetMapping(value = {"/delete"})
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:user:del')")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息")//描述
    @ApiImplicitParam(name = "userDto", value = "用户实体类", required = true, dataType = "UserDto")
    public Results deleteUser(UserDto userDto){
        int count = userService.deleteUser(userDto.getId());
        if(count > 0){
            //System.out.println(Results.success());
            return Results.success();
        }else{
            return Results.failure();
        }

    }

    @GetMapping(value = {"/findUserByFuzzyUsername"})
    @ResponseBody
    @ApiOperation(value = "模糊查询用户信息", notes = "模糊搜索查询用户信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "模糊搜索的用户名", required = true),
    })
    public Results<SysUser> findUserByFuzzyUsername(PageTableRequest pageTableRequest,String username){
        log.info("UserController.findUserByFuzzyUsername():param(username=" + username +",pageTableRequest = "+pageTableRequest+")");
        //System.out.println(pageTableRequest.getOffset());
        pageTableRequest.countOffSet();
        //System.out.println("从索引开始："+pageTableRequest.getOffset());
        /*System.out.println(pageTableRequest.getOffset());
        System.out.println(pageTableRequest.getLimit());*/
        return userService.getUserByFuzzyUsername(username,pageTableRequest.getOffset(),pageTableRequest.getLimit());

    }

    @PostMapping("/changePassword")
    @ResponseBody
    @ApiOperation(value = "修改密码",notes = "修改密码")
    public Results<SysUser> changePassword(String username,String oldPassword,String newPassword){
        return userService.changePassword(username,oldPassword,newPassword);
    }


    String pattern = "yyyy-MM-dd";
    @InitBinder
    public void  initBinder(WebDataBinder binder, WebRequest request){
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat(pattern),true));
    }





}
