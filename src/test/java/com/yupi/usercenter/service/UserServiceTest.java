package com.yupi.usercenter.service;

import com.yupi.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author zoetian
 * @create 2023/1/24
 */
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;
    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("chenqing1");
        user.setUserAccount("chenqingbabe1");
        user.setAvatarUrl("https://www.google.com/imgres?imgurl=https%3A%2F%2Fimg.930tu.com%2F20210223%2F146264f418ce34b36159a3b28b7792d8.jpg&imgrefurl=https%3A%2F%2Fwww.930tu.com%2Ftouxiang%2Fdongman%2F38064.html&tbnid=47dCIKuq1fPezM&vet=12ahUKEwiJzsHns-H8AhUKn3IEHS2ND34QMygEegUIARCwAQ..i&docid=JtcFrMMlQdSgiM&w=400&h=400&q=%E5%A4%B4%E5%83%8F&ved=2ahUKEwiJzsHns-H8AhUKn3IEHS2ND34QMygEegUIARCwAQ");
        user.setGender(0);
        user.setUserPassword("123456789");
        user.setEmail("123111");
        user.setPhone("4561111");
        boolean result = userService.save(user);
        System.out.println(user.getUserPassword());
        System.out.println(user.getId());
        Assertions.assertEquals(true,result);

    }
    @Test
    void userRegister() {
        String userAccount = "tianyuantest1";
        String userPassword = "1234567890";
        String checkPassword = "1234567890";

       long result =  userService.userRegister(userAccount,userPassword,checkPassword);
       Assertions.assertEquals(-1,result);





    }
}

