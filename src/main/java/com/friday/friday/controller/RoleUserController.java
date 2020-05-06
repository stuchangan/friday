package com.friday.friday.controller;


import com.friday.friday.base.result.Results;
import com.friday.friday.service.RoleUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = {"/roleUser"})
@Slf4j
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;

    @PostMapping(value = {"/getRoleUserByUserId"})
    @ResponseBody
    @ApiOperation(value = "根据用户id查询角色id",notes = "获取当前用户角色")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true)
    public Results getRoleUserByUserId(Integer userId){
        log.info("RoleUserController.RoleUserController() param = " + userId);
        System.out.println(roleUserService.getBySysRoleUserByUserId(userId));
        return roleUserService.getBySysRoleUserByUserId(userId);
    }

}
