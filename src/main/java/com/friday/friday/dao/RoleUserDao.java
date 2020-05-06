package com.friday.friday.dao;


import com.friday.friday.model.SysRoleUser;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface RoleUserDao {


    @Insert("insert into sys_role_user(userId,roleId) values (#{userId},#{roleId})")
    int save(SysRoleUser sysRoleUser);

    @Select("select * from sys_role_user t where t.userId = #{userId}")
    SysRoleUser getBySysRoleUserByUserId(Integer userId);

    int updateSysRoleUser(SysRoleUser sysRoleUser);

    @Delete("delete from sys_role_user where userId = #{userId}")
    int deleteRoleUserByUserId(int userId);

    @Select("select * from sys_role_user where roleId = #{id}")
    List<SysRoleUser> lisyAllSysRoleByRoleid(Integer id);



}
