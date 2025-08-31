package com.wolf.wxgallerybackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.wolf.wxgallerybackend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true) // 开启 AOP 代理
public class WxGalleryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxGalleryBackendApplication.class, args);
    }

}
