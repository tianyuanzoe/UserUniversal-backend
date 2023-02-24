package com.yupi.usercenter.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
* @author moxunhuan
* @description 针对表【user(user)】的数据库操作Service
* @createDate 2023-01-24 15:03:32
*/
public interface UserService extends IService<User> {

    /**
     * user register
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * return user account(after Data masking)
     * @param userAccount
     * @param userPassword
     *
     * @param request
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originalUser
     * @return
     */
    User getSafetyUser(User originalUser);
}
