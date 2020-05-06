package com.friday.friday.service.impl;


import com.friday.friday.base.result.ResponseCode;
import com.friday.friday.base.result.Results;
import com.friday.friday.dao.RoleDao;

import com.friday.friday.dao.RolePermissionDao;
import com.friday.friday.dao.RoleUserDao;
import com.friday.friday.dto.RoleDto;
import com.friday.friday.model.SysRole;
import com.friday.friday.model.SysRoleUser;
import com.friday.friday.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleUserDao roleUserDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;


    @Override
    public Results<SysRole> getAllRoles() {
        return Results.success(50,roleDao.getAllRoles());
    }

    @Override
    public Results<SysRole> getAllRoleByPage(Integer offset, Integer limit) {
        return Results.success(roleDao.counrAllUsers().intValue(),roleDao.getAllRoleByPage(offset,limit));
    }

    /**
     * 根据角色名称 进行模糊查询
     * @param roleName
     * @param offset
     * @param limit
     * @return
     */
    @Override
    public Results<SysRole> findRoleByFuzzyRolename(String roleName, Integer offset, Integer limit) {
        return Results.success(roleDao.counrByFuzzyUsername(roleName).intValue(),roleDao.getRoleByFuzzyRolenamePage(roleName,offset,limit));
    }

    /**
     * 新增角色
     * @param roleDto
     * @return
     */
    @Override
    public Results<SysRole> save(RoleDto roleDto) {
        //1、先保存角色
        roleDao.saveRole(roleDto);
        List<Long> permissionIds = roleDto.getPermissionIds();
        //移除0 permission id 从1开始
        permissionIds.remove(0L);
        //2、保存角色对应的所有权限
        if(!CollectionUtils.isEmpty(permissionIds)){
            rolePermissionDao.save(roleDto.getId(),permissionIds);

        }
        return Results.success();
    }

    @Override
    public SysRole getRoleById(Integer id) {
        return roleDao.getById(id);
    }

    @Override
    public int update(RoleDto roleDto) {
        List<Long> permissionIds = roleDto.getPermissionIds();
        permissionIds.remove(0L);
        //1、更新角色权限之前删除该角色之前所有的权限
        rolePermissionDao.deleteRolePermission(roleDto.getId());

        //判断该角色是否有赋予权限值，有就添加
        if(!CollectionUtils.isEmpty(permissionIds)){
            rolePermissionDao.save(roleDto.getId(),permissionIds);
        }
        return roleDao.update(roleDto);
    }

    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    @Override
    public Results<SysRole> delete(Integer id) {
        List<SysRoleUser> datas = roleUserDao.lisyAllSysRoleByRoleid(id);
        if(datas.size() <= 0){
            roleDao.delete(id);
            return Results.success();
        }
        return Results.failure(ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getCode(),ResponseCode.USERNAME_REPEAT.USER_ROLE_NO_CLEAR.getMessage());
    }
}
