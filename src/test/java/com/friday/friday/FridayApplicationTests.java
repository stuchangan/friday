package com.friday.friday;

import com.friday.friday.controller.UserController;
import com.friday.friday.dao.UserDao;
import com.friday.friday.dto.UserDto;
import com.friday.friday.model.SysRoleUser;
import com.friday.friday.model.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FridayApplicationTests {
    SysUser sysUser = new SysUser();
    UserDto userDto = new UserDto();
    @Autowired
    DataSource dataSource;

    @Resource
    UserController userController;

    @Test
    public void contextLoads() {

        userDto.setUsername("test");
        userDto.setPassword("admin");
        userDto.setUpdateTime(new Date());
        userDto.setCreateTime(new Date());


        userController.saveUser(userDto,1);
    }



}
