package com.friday.friday.service.impl;

import com.friday.friday.base.result.Results;
import com.friday.friday.dao.RoleUserDao;
import com.friday.friday.model.SysRoleUser;
import com.friday.friday.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleUserServiceImpl implements RoleUserService {

    @Autowired
    private RoleUserDao roleUserDao;


    @Override
    public Results getBySysRoleUserByUserId(Integer userId) {
        SysRoleUser sysRoleUser = roleUserDao.getBySysRoleUserByUserId(userId);
        if(sysRoleUser != null){
            return Results.success(sysRoleUser);
        }else{
            return Results.success();
        }
    }
}
