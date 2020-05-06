package com.friday.friday.service.impl;

import com.friday.friday.base.result.Results;
import com.friday.friday.dao.RoleUserDao;
import com.friday.friday.dao.UserDao;
import com.friday.friday.dto.UserDto;
import com.friday.friday.model.SysRoleUser;
import com.friday.friday.model.SysUser;
import com.friday.friday.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleUserDao roleUserDao;


    @Override
    public Results<SysUser> getAllUserByPage(Integer offset, Integer limit) {
        //返回count，和所有用户
        return Results.success(userDao.counrAllUsers().intValue(),userDao.getAllUserByPage(offset,limit));
    }

    @Override
    public Results save(SysUser user, Integer roleId) {

        if(roleId != null){
            //存储user
            userDao.save(user);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRoleId(roleId);
            sysRoleUser.setUserId(user.getId().intValue());
            //存储roleuser
            roleUserDao.save(sysRoleUser);
            return Results.success();
        }

        return Results.failure();

    }

    @Override
    public SysUser getUserByPhone(String telephone) {

        return userDao.getUserByPhone(telephone);
    }

    @Override
    public SysUser getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public Results<SysUser> updateUser(UserDto userDto, Integer roleId) {
        if(roleId != null){
            //更新sysuser
            userDao.updateUser(userDto);
            //更新sys_role_user
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(userDto.getId().intValue());
            sysRoleUser.setRoleId(roleId);
            if(roleUserDao.getBySysRoleUserByUserId(userDto.getId().intValue()) != null){
                roleUserDao.updateSysRoleUser(sysRoleUser);
            }else{
                roleUserDao.save(sysRoleUser);
            }
            return Results.success();
        }else {
            return Results.failure();
        }


    }

    @Override
    public int deleteUser(Long id) {
        //删除sys_role_user
        roleUserDao.deleteRoleUserByUserId(id.intValue());
        //删除sysUser
        return userDao.deleteUser(id.intValue());
    }

    @Override
    public Results<SysUser> getUserByFuzzyUsername(String username, Integer offset, Integer limit) {
        return Results.success(userDao.counrByFuzzyUsername(username).intValue(),userDao.getUserByFuzzyUsernamePage(username,offset,limit));
    }

    @Override
    public SysUser getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public Results changePassword(String username, String oldPassword, String newPassword) {
        SysUser sysUser = userDao.getUser(username);
        if (sysUser == null){
            return Results.failure(1,"用户不存在");
        }
        //new BCryptPasswordEncoder().matches(oldPassword,sysUser.getPassword())
        //使用.matches(输入密码,数据库中的密码)方法进行两次密码比对
        if(!new BCryptPasswordEncoder().matches(oldPassword,sysUser.getPassword())){

            System.out.println(new BCryptPasswordEncoder().matches(oldPassword,sysUser.getPassword()));
            return Results.failure(1,"旧密码错误");
        }
        userDao.changePassword(sysUser.getId(),new BCryptPasswordEncoder().encode(newPassword));

        return Results.success();
    }
}
