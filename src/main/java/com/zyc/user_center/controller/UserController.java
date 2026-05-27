package com.zyc.user_center.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyc.user_center.model.domain.User;
import com.zyc.user_center.model.domain.request.UserLoginRequest;
import com.zyc.user_center.model.domain.request.UserRegisterRequest;
import com.zyc.user_center.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zyc.user_center.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        if (userRegisterRequest == null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)){
            return null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {

        if (userLoginRequest == null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)){
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }

    @PostMapping("/logout")
    public Integer userLogout(HttpServletRequest request) {

        if (request == null){
            return null;
        }
        return userService.userLogout(request);
    }

    @GetMapping("/search")
    public List<User> userSearch(String username, HttpServletRequest request) {
        //鉴权
        if (isNotAdmin(request)){
            return new ArrayList<>();
        }
        if (!StringUtils.isNotBlank(username)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        List<User> userList = userService.list(queryWrapper);
        if (CollectionUtils.isEmpty(userList)){
            return new ArrayList<>();
        }
        return userList.stream()
                .map(user -> userService.getSafetyUser(user))
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public Boolean userDelete(@PathVariable Long id, HttpServletRequest request) {
        //鉴权
        if (isNotAdmin(request)){
            return false;
        }
        if (id == null){
            return false;
        }
        return userService.removeById(id);
    }

    /**鉴权是否为管理员
     * @return  true 不是管理员
     */
    private boolean isNotAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        return user == null || user.getUserRole() != 1;
    }
}
