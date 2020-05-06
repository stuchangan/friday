package com.friday.friday.dao;


import com.friday.friday.base.result.Results;
import com.friday.friday.model.SysPermission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PermissionDao {

    @Select("select * from sys_permission t")
    List<SysPermission> findAll();

    @Select("select p. * from sys_permission p inner join sys_role_permission rp on p.id = rp.permissionId where rp.roleId = #{roleId} order by p.sort")
    List<SysPermission> listByRoleId(Integer roleId);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into sys_permission(parentId,name,css,href,type,permission,sort) values (#{parentId},#{name},#{css},#{href},#{type},#{permission},#{sort})")
    int save(SysPermission sysPermission);

    @Select("select * from sys_permission t where t.id = #{id}")
    SysPermission getPermissionById(Integer id);

    int update(@Param("sysPermission") SysPermission sysPermission);

    @Delete("delete from sys_permission where id = #{id}")
    int deleteById(Integer id);

    @Delete("delete from sys_permission where parentId = #{parentId}")
    int deleteByParentId(Integer parentId);

    @Select("select sp.* from sys_role_user sru " +
            "inner join sys_role_permission srp on srp.roleId = sru.roleId " +
            "left join sys_permission sp on srp.permissionId = sp.id " +
            "where sru.userId = #{userId}")
    List<SysPermission> listByUserId(@Param("userId") Long userId);
}
