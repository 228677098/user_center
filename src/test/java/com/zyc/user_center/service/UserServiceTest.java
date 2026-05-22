package com.zyc.user_center.service;
import java.util.Date;

import com.zyc.user_center.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("123");
        user.setUserAccount("456");
        user.setAvatarUrl("12321");
        user.setUserPassword("123");
        user.setPhone("123");
        user.setEmail("123");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);
    }

    @Test
    void testUserRegister() {
        String userAccount = "zyc";
        String userPassword = "123456";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        System.out.println(result);
        assertEquals(-1, result);
        userAccount = "yu";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);
        userAccount = "zyc%&234";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);
        userAccount = "zyc1";
        userPassword = "12345678";
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);
        userAccount = "zyc123";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);
        userAccount = "zyc12345";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(4, result);
    }
}