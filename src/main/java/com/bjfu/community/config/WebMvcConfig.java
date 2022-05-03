package com.bjfu.community.config;

import com.bjfu.community.annotation.LoginRequired;
import com.bjfu.community.controller.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * 拦截器配置类
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

    //不用自己写的拦截器进行登录检查
//    @Autowired
//    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    private MessageInterceptor messageInterceptor;

    @Autowired
    private DataInterceptor dataInterceptor;


    // 对除静态资源外所有路径进行拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/css/**", "/js/**", "/img/**", "/editor-md/**", "/editor-md-upload/**");

        registry.addInterceptor(messageInterceptor)
                .excludePathPatterns("/css/**", "/js/**", "/img/**", "/editor-md/**", "/editor-md-upload/**");

        registry.addInterceptor(dataInterceptor)
                .excludePathPatterns("/css/**", "/js/**", "/img/**", "/editor-md/**", "/editor-md-upload/**");

    }
//     配置虚拟路径映射访问 如果存在本地服务器，springboot会保护系统不爆露真是物理地址，需要配置本地虚拟路径
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry){
//        // System.getProperty("user.dir") 获取程序的当前路径
//        String path = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\editor-md-upload\\";
//        registry.addResourceHandler("/editor-md-upload/**").addResourceLocations("file:" + path);
//    }
}
