package com.wolf.wxgallerybackend.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wolf.wxgallerybackend.exception.BusinessException;
import com.wolf.wxgallerybackend.exception.ErrorCode;
import com.wolf.wxgallerybackend.model.entity.User;
import com.wolf.wxgallerybackend.model.enums.UserRoleEnum;
import com.wolf.wxgallerybackend.service.UserService;
import com.wolf.wxgallerybackend.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
* @author 54703
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-09-07 14:27:55
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 用户注册
     *
     * @param userAccount       用户账号
     * @param userPassword      用户密码
     * @param checkPassword     确认密码
     * @return 新用户 ID
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验参数 - 可用 ThrowUtils 优雅封装
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        // ThrowUtils.throwIf(userAccount.length() < 4, ErrorCode.PARAMS_ERROR, "用户账号过短");
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }

        // 2. 检查用户账号是否和数据库中已有的重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已存在");
        }

        // 3. 密码加密
        String encryptedPassword = getEncryptedPassword(userPassword);

        // 4. 数据库新建用户数据
        User user = User.builder()
                .userAccount(userAccount)
                .userPassword(encryptedPassword)
                .userName("Wolf")
                .userRole(UserRoleEnum.USER.getValue())
                .build();
        boolean savaRes = this.save(user);
        if (!savaRes) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }

        return user.getId();    // 主键回填
    }

    /**
     * 密码加密 - 脱敏
     *
     * @param userPassword  用户密码
     * @return  加密后的密码
     */
    @Override
    public String getEncryptedPassword(String userPassword) {
        // 加盐
        final String SALT = "wolf";

        return DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
    }
}




