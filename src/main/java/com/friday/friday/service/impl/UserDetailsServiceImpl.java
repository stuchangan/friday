package com.friday.friday.service.impl;

import com.friday.friday.dao.PermissionDao;
import com.friday.friday.dto.LoginUser;
import com.friday.friday.model.SysPermission;
import com.friday.friday.model.SysUser;
import com.friday.friday.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUser(username);
        if(sysUser == null){
            throw new AuthenticationCredentialsNotFoundException("该用户不存在");
        }else if (sysUser.getStatus() == SysUser.Status.DISABLED){
            throw new LockedException("该用户被锁定，请联系管理员");
        }

        LoginUser loginUser = new LoginUser();
        //将user对象封装为loginuser对象
        BeanUtils.copyProperties(sysUser,loginUser);

        //System.out.println(loginUser);

        List<SysPermission> permissionList = permissionDao.listByUserId(sysUser.getId());
        loginUser.setPermissions(permissionList);
        //System.out.println(loginUser);
        return loginUser;
    }
}
