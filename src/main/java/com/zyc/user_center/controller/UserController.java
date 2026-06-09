package com.zyc.user_center.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyc.user_center.common.BaseResponse;
import com.zyc.user_center.common.ErrorCode;
import com.zyc.user_center.common.ResultUtil;
import com.zyc.user_center.exception.BusinessException;
import com.zyc.user_center.model.domain.User;
import com.zyc.user_center.model.domain.request.UserLoginRequest;
import com.zyc.user_center.model.domain.request.UserRegisterRequest;
import com.zyc.user_center.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.zyc.user_center.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {

        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtil.success(userService.userRegister(userAccount, userPassword, checkPassword, planetCode));
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {

        if (userLoginRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtil.success(userService.userLogin(userAccount, userPassword, request));
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {

        if (request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtil.success(userService.userLogout(request));
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> userSearch(String username, HttpServletRequest request) {
        //鉴权
        if (isNotAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (!StringUtils.isNotBlank(username)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        List<User> userList = userService.list(queryWrapper);
        if (CollectionUtils.isEmpty(userList)){
            return ResultUtil.success(null);
        }
        List<User> resultList = userList.stream()
                .map(user -> userService.getSafetyUser(user))
                .collect(Collectors.toList());
        return ResultUtil.success(resultList);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> userDelete(@PathVariable Long id, HttpServletRequest request) {
        //鉴权
        if (isNotAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtil.success(userService.removeById(id));
    }

    /**鉴权是否为管理员
     * @return  true 不是管理员
     */
    private boolean isNotAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        return user == null || user.getUserRole() != 1;
    }
}
