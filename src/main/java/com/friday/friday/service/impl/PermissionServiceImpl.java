package com.friday.friday.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.friday.friday.base.result.Results;
import com.friday.friday.dao.PermissionDao;
import com.friday.friday.model.SysPermission;
import com.friday.friday.service.PermissionService;
import com.friday.friday.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 获取所有权限
     * @return
     */
    @Override
    public Results<JSONArray> listAllPermission() {
        log.info(getClass().getName() + ".listAllPermission()");
        List datas = permissionDao.findAll();
        JSONArray array = new JSONArray();
        log.info(getClass().getName() +".setPermissionsTree(?,?,?)");
        TreeUtils.setPermissionsTree(0,datas,array);
        return Results.success(array);
    }

    /**
     * 根据角色id 查询该角色对应的权限
     * @param roleId
     * @return
     */
    @Override
    public Results<SysPermission> listByRoleId(Integer roleId) {
        List<SysPermission> datas = permissionDao.listByRoleId(roleId);
        return Results.success(0,datas);
    }


    /**
     * 获取所有权限，用于展示权限列表
     * @return
     */
    @Override
    public Results<SysPermission> getMenuAll() {
        return Results.success(0,permissionDao.findAll());
    }

    @Override
    public Results<SysPermission> save(SysPermission sysPermission) {
        return (permissionDao.save(sysPermission) > 0 )? Results.success():Results.failure();
    }

    @Override
    public SysPermission getPermissionById(Integer id) {
        return permissionDao.getPermissionById(id);
    }

    @Override
    public Results updateSysPermission(SysPermission sysPermission) {
        return (permissionDao.update(sysPermission) > 0) ? Results.success():Results.failure();
    }

    @Override
    public Results delete(Integer id) {
        permissionDao.deleteById(id);
        permissionDao.deleteByParentId(id);
        return Results.success();

    }

    /**
     * 根据用户id查询权限
     * @param userId
     * @return
     */
    @Override
    public Results getMenu(Long userId) {
        List<SysPermission> datas = permissionDao.listByUserId(userId);
        //过滤菜单里的按钮，只留菜单。jdk流式编程
        datas = datas.stream().filter(p -> p.getType().equals(1)).collect(Collectors.toList());
        JSONArray array = new JSONArray();
        log.info(getClass().getName() +".setPermissionsTree(?,?,?)");
        TreeUtils.setPermissionsTree(0,datas,array);
        return Results.success(array);
    }
}
