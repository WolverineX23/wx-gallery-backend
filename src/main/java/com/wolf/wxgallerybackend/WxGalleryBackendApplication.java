package com.wolf.wxgallerybackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wolf.wxgallerybackend.mapper")
public class WxGalleryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxGalleryBackendApplication.class, args);
    }

}
