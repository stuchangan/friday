package com.friday.friday.controller;


import com.friday.friday.base.result.PageTableRequest;
import com.friday.friday.base.result.Results;
import com.friday.friday.dto.RoleDto;
import com.friday.friday.model.SysRole;
import com.friday.friday.service.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"/role"})
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;


    @GetMapping(value = {"/all"})
    @ResponseBody
    @ApiOperation(value = "获取所有角色", notes = "获取所有角色信息")//描述
    public Results<SysRole> getRole(){
        log.info("RoleController.getRole()");
        //System.out.println(roleService.getAllRoles());
        return roleService.getAllRoles();
    }

    @GetMapping(value = {"/list"})
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:query')")
    @ApiOperation(value = "分页获取角色", notes = "用户分页获取角色信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", required = true,dataType = "Integer"),
            @ApiImplicitParam(name = "limit", required = true,dataType = "Integer"),
    })
    public Results<SysRole> getRoles(PageTableRequest pageTableRequest){
        log.info("RoleController.getRoles():param(pageTableRequest=" + pageTableRequest +")");
        pageTableRequest.countOffSet();
        /*System.out.println(pageTableRequest.getOffset());
        System.out.println(pageTableRequest.getLimit());*/
        return roleService.getAllRoleByPage(pageTableRequest.getOffset(),pageTableRequest.getLimit());

    }

    @GetMapping(value = {"/findRoleByFuzzyRolename"})
    @ResponseBody
    @ApiOperation(value = "模糊查询角色信息", notes = "模糊搜索查询角色信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName",value = "模糊搜索的角色名", required = true),
    })
    public Results<SysRole> findRoleByFuzzyRolename(PageTableRequest pageTableRequest, String roleName){
        log.info("UserController.findUserByFuzzyUsername():param(username=" + roleName +",pageTableRequest = "+pageTableRequest+")");
        pageTableRequest.countOffSet();
        return roleService.findRoleByFuzzyRolename(roleName,pageTableRequest.getOffset(),pageTableRequest.getLimit());

    }

    @GetMapping(value = {"/add"})
    @PreAuthorize("hasAuthority('sys:role:add')")
    @ApiOperation(value = "新增角色信息页面", notes = "跳转到角色信息新增页面")//描述
    public String addRole(Model model){
        model.addAttribute(new SysRole());
        return "/role/role-add";
    }

    @PostMapping(value = {"/add"})
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:add')")
    @ApiOperation(value = "保存角色信息", notes = "保存新增的角色信息")//描述
    @ApiImplicitParam(name = "roleDto",value = "角色信息实体类", required = true,dataType = "RoleDto")
    public Results saveRole(@RequestBody RoleDto roleDto){
        System.out.println(roleDto);
        return roleService.save(roleDto);
    }

    @GetMapping(value = {"/edit"})
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @ApiOperation(value = "编辑角色信息页面", notes = "跳转到角色信息编辑页面")//描述
    @ApiImplicitParam(name = "role",value = "角色信息实体类", required = true,dataType = "SysRole")
    public String editRole(Model model,SysRole sysRole){
        model.addAttribute("sysRole",roleService.getRoleById(sysRole.getId()));
        return "/role/role-edit";
    }
    @PostMapping(value = {"/edit"})
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:edit')")
    @ApiOperation(value = "保存角色信息", notes = "保存被编辑的角色信息")//描述
    @ApiImplicitParam(name = "roleDto",value = "角色信息实体类", required = true,dataType = "RoleDto")
    public Results<SysRole> updateRole(@RequestBody RoleDto roleDto){
        int count = roleService.update(roleDto);
        return Results.success();
    }

    @GetMapping(value = {"/delete"})
    @ResponseBody
    @PreAuthorize("hasAuthority('sys:role:del')")
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")//描述
    public Results<SysRole> deleteRole(RoleDto roleDto){
        return roleService.delete(roleDto.getId());
    }



}
