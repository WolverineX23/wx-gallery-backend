package com.wolf.wxgallerybackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wolf.wxgallerybackend.model.dto.user.UserQueryRequest;
import com.wolf.wxgallerybackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wolf.wxgallerybackend.model.vo.LoginUserVO;
import com.wolf.wxgallerybackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request      session
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取脱敏后的登录用户信息
     *
     * @param user User
     * @return LoginUserVo
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏后的用户信息
     * -  区别 getLoginUserVo，查询自己登录的账号信息和查询别人的账号信息。
     *
     * @param user User
     * @return UserVo
     */
    UserVO getUserVO(User user);


    /**
     * 获取脱敏后的用户信息列表
     *
     * @param userList List<User>
     * @return UserVo
     */
    List<UserVO> getUserVOList(List<User> userList);


    /**
     * 获取当前登录用户
     *
     * @param request session
     * @return User
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户登出
     *
     * @param request session
     * @return
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);
}
