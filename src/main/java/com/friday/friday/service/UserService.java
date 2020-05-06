package com.friday.friday.service;

import com.friday.friday.base.result.Results;
import com.friday.friday.dto.UserDto;
import com.friday.friday.model.SysUser;


public interface UserService{

    /**
     * 查询所有用户 根据参数实现分页操作
     * @param offset  从索引开始
     * @param limit  每页多少数据
     * @return
     */
    Results<SysUser> getAllUserByPage(Integer offset, Integer limit);

    /**
     * 添加用户
     * @param user
     * @param roleId
     * @return
     */
    Results save(SysUser user, Integer roleId);

    /**
     * 根据手机查询用户
     * @param telephone
     * @return
     */
    SysUser getUserByPhone(String telephone);


    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    SysUser getUserById(Long id);

    /**
     * 更新用户信息
     * @param userDto
     * @param roleId
     * @return
     */
    Results<SysUser> updateUser(UserDto userDto, Integer roleId);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteUser(Long id);

    /**
     * 模糊查询
     *
     * @param username
     * @param offset
     * @param limit
     * @return
     */
    Results<SysUser> getUserByFuzzyUsername(String username, Integer offset, Integer limit);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    SysUser getUser(String username);

    /**
     * 修改密码
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    Results changePassword(String username, String oldPassword, String newPassword);
}
