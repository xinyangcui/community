package com.bjfu.community.controller.interceptor;

import com.bjfu.community.annotation.LoginRequired;
import com.bjfu.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.handler.Handler;
import java.lang.reflect.Method;



@Deprecated
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断请求是不是方法，而不是静态资源
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method=handlerMethod.getMethod();
            LoginRequired loginRequired=method.getAnnotation(LoginRequired.class);
            //如果方法是需要登录但是取到请求为空（未登录）返回false,不允许访问
            if(loginRequired != null && hostHolder.getUser() == null){
                //重定向到登录
                response.sendRedirect(request.getContextPath() + "/login");
                return false;

            }

        }
        return true;
    }
}
