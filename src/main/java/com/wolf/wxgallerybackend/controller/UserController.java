package com.wolf.wxgallerybackend.controller;

import com.wolf.wxgallerybackend.common.BaseResponse;
import com.wolf.wxgallerybackend.common.ResultUtils;
import com.wolf.wxgallerybackend.exception.ErrorCode;
import com.wolf.wxgallerybackend.exception.ThrowUtils;
import com.wolf.wxgallerybackend.model.dto.UserRegisterRequest;
import com.wolf.wxgallerybackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long userId = userService.userRegister(userAccount, userPassword, checkPassword);

        return ResultUtils.success(userId);
    }
}
