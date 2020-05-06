package com.friday.friday.dao;



import com.friday.friday.dto.RoleDto;
import com.friday.friday.model.SysRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleDao {

    @Select("select * from sys_role r")
    List<SysRole> getAllRoles();

    @Select("select count(*) from sys_role r")
    Long counrAllUsers();

    @Select("select * from sys_role r limit #{startPosition},#{limit}")
    List<SysRole> getAllRoleByPage(@Param("startPosition") Integer startPosition, @Param("limit") Integer limit);

    @Select("select count(*) from sys_role t where t.name like '%${roleName}%'")
    Long counrByFuzzyUsername(@Param("roleName") String roleName);

    @Select("select * from sys_role r where r.name like '%${roleName}%' limit #{startPosition},#{limit}")
    List<SysRole> getRoleByFuzzyRolenamePage(@Param("roleName") String roleName, @Param("startPosition") Integer startPosition, @Param("limit") Integer limit);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_role(name,description,createTime,updateTime) values(#{name},#{description},now(),now())")
    int saveRole(SysRole sysRole);

    @Select("select * from sys_role r where r.id = #{id}")
    SysRole getById(Integer id);


    int update(RoleDto roleDto);

    @Delete("delete from sys_role where id = #{id}")
    int delete(Integer id);
}
