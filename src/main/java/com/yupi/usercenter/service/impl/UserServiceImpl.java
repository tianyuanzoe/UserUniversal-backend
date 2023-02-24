package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.mapper.UserMapper;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yupi.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author moxunhuan
* @description 针对表【user(user)】的数据库操作Service实现
* @createDate 2023-01-24 15:03:32
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    /**
     * salt for encrypt password
     */
    private static final String SALT = "tianyuanzoe";

    @Resource
    UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //authentication
    if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
        return -1;
    }
    if(userAccount.length() < 4){
        return -1;
    }
    if(userPassword.length() < 8 || checkPassword.length() < 8){
        return -1;
    }
    //no special characters
        String regExp = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(regExp).matcher(userAccount);
        if(matcher.find()){
            return -1;
        }
    //no duplicate userAccount
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("userAccount",userAccount);
    long count = this.count(queryWrapper);
    if(count > 0){
        return -1;
    }

    //password and validate password
        if(!userPassword.equals(checkPassword)){
            return -1;
        }

        String encryptPassword =  DigestUtils.md5DigestAsHex((SALT  + userPassword).getBytes());
        System.out.println(encryptPassword);

        //insert user data
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);

        boolean saveResult = this.save(user);
        if(!saveResult){
            return -1;
        }

        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //authentication
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if(userAccount.length() < 4){
            return null;
        }
        if(userPassword.length() < 8 ){
            return null;
        }
        //no special characters
        String regExp = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(regExp).matcher(userAccount);
        if(matcher.find()){
            return null;
        }
        //encrypt password
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT  + userPassword).getBytes());
        //no duplicate userAccount
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //user does not exist
        if(user == null){
            log.info("user login failed,user account can not match");
            return null;
        }
        User safetyUser = getSafetyUser(user);



        //document the user's login status(Session)
        request.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);


        
        return safetyUser;

    }
    @Override
    public User getSafetyUser(User originalUser){
        //用户脱敏(如果不脱敏，前端能看到从数据库中返回的所有用户信息
        User safetyUser = new User();
        safetyUser.setId(originalUser.getId());
        safetyUser.setUsername(originalUser.getUsername());
        safetyUser.setUserAccount(originalUser.getUserAccount());
        safetyUser.setAvatarUrl(originalUser.getAvatarUrl());
        safetyUser.setGender(originalUser.getGender());
        safetyUser.setPhone(originalUser.getPhone());
        safetyUser.setUserStatus(originalUser.getUserStatus());
        safetyUser.setEmail(originalUser.getEmail());
        safetyUser.setCreateTime(originalUser.getCreateTime());
        return safetyUser;

    }

}




