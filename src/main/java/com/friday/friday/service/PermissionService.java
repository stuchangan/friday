package com.friday.friday.service;

import com.alibaba.fastjson.JSONArray;
import com.friday.friday.base.result.Results;
import com.friday.friday.model.SysPermission;

public interface PermissionService {

    Results<JSONArray> listAllPermission();

    Results<SysPermission> listByRoleId(Integer roleId);

    Results<SysPermission> getMenuAll();

    Results<SysPermission> save(SysPermission sysPermission);

    SysPermission getPermissionById(Integer id);

    Results updateSysPermission(SysPermission sysPermission);

    Results delete(Integer id);


    Results getMenu(Long userId);
}
