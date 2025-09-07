package com.wolf.wxgallerybackend.aop;

import com.wolf.wxgallerybackend.annotation.AuthCheck;
import com.wolf.wxgallerybackend.exception.BusinessException;
import com.wolf.wxgallerybackend.exception.ErrorCode;
import com.wolf.wxgallerybackend.exception.ThrowUtils;
import com.wolf.wxgallerybackend.model.entity.User;
import com.wolf.wxgallerybackend.model.enums.UserRoleEnum;
import com.wolf.wxgallerybackend.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * 执行拦截 AuthCheck   -   @Before 也可
     *
     * @param pjp           切入点
     * @param authCheck     权限校验注解
     */
    @Around("@annotation(authCheck)")
    private Object doInterceptor(ProceedingJoinPoint pjp, AuthCheck authCheck) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ThrowUtils.throwIf(requestAttributes == null, ErrorCode.OPERATION_ERROR, "requestAttributes 为空");
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 获取当前登录用户，并验证了用户是否登录
        User loginUser = userService.getLoginUser(request);

        // 若不需要权限，放行
        String mustRole = authCheck.mustRole();
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        if (mustRoleEnum == null) {
            return pjp.proceed();
        }

        // 以下的代码，必须有权限，才能通过
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        ThrowUtils.throwIf(userRoleEnum == null, ErrorCode.NO_AUTH_ERROR);

        // 要求必须有管理员权限，普通用户拒绝访问
        ThrowUtils.throwIf(UserRoleEnum.ADMIN.equals(mustRoleEnum) && !UserRoleEnum.ADMIN.equals(userRoleEnum), ErrorCode.NO_AUTH_ERROR);

        // 通过权限校验，放行
        return pjp.proceed();
    }
}