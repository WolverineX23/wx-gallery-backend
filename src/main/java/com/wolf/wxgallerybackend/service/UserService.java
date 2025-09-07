package com.wolf.wxgallerybackend.service;

import com.wolf.wxgallerybackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 54703
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-09-07 14:27:55
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount       用户账号
     * @param userPassword      用户密码
     * @param checkPassword     确认密码
     * @return  新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 获取加密后的用户密码
     *
     * @param userPassword  用户密码
     * @return  加密后的密码
     */
    String getEncryptedPassword(String userPassword);
}
