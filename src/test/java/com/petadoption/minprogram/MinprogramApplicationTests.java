package com.petadoption.minprogram;

import com.petadoption.minprogram.dataInterface.entity.Users;
import com.petadoption.minprogram.dataInterface.mapper.UsersMapper;
import com.petadoption.minprogram.dataInterface.service.IUsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MinprogramApplicationTests {

    @Autowired
    private IUsersService usersService;

    @Resource
    private UsersMapper usersMapper;

    @Test
    public void contextLoads() {
//        usersService.getUserByWxOpenId("nsadsajkdnkasndjk");
//        Users user = new Users();
//        user.setUserCreateTime(LocalDateTime.now());
//        user.setUserLastLogin(LocalDateTime.now());
//        user.setUserGeneral("男");
//        user.setUserWxOpenId("dbasjdbjasd");
//        user.setUserAvator("dgsahdgashjd");
//        user.setUserCity("汕头");
//        user.setUserIncome(new BigDecimal(1211545));
//        user.setUserName("sdsadbkajs");
//        user.setUserPhone("1111111111");
//        user.setUserBirthday(LocalDateTime.now());
//        usersService.save(user);
        System.out.println("=============================TEST LOG BEGIN======================================");
        Users user1 = usersMapper.findByUUID("0c23cccc0214ea360f324f320415a23a");
        System.out.println(usersService.list());
        System.out.println("=============================TEST LOG END========================================");
    }

}

