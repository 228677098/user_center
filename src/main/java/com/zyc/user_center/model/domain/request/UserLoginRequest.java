package com.zyc.user_center.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zyc
 * @description 用户登录请求体
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 23423423423423L;
    /**
     * 用户账户
     */
    private String userAccount;
    /**
     * 用户密码
     */
    private String userPassword;
}
