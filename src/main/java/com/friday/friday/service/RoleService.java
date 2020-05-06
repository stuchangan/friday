package com.friday.friday.service;


import com.friday.friday.base.result.Results;
import com.friday.friday.dto.RoleDto;
import com.friday.friday.model.SysRole;

public interface RoleService {
    /**
     * 获取所有角色
     * @return
     */
    Results<SysRole> getAllRoles();

    /**
     * 获取所有角色
     * @return
     */
    Results<SysRole> getAllRoleByPage(Integer offset, Integer limit);

    /**
     * 根据角色名查询角色
     * @return
     */
    Results<SysRole> findRoleByFuzzyRolename(String roleName, Integer offset, Integer limit);

    /**
     * 新增角色
     * @param roleDto
     * @return
     */
    Results<SysRole> save(RoleDto roleDto);

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    SysRole getRoleById(Integer id);

    /**
     * 更新角色
     * @param roleDto
     * @return
     */
    int update(RoleDto roleDto);

    Results<SysRole> delete(Integer id);
}
