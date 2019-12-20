package com.cowbeer.offerpost.controller.interceptor;

import com.cowbeer.offerpost.annotation.LoginRequired;
import com.cowbeer.offerpost.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: Diors.Po
 * @Email: 171256175@qq.com
 * @date 2019-12-20 10:28
 */
@Slf4j
@Component
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (hasLoginRequired(handler)){
            log.info("需要认证的接口访问行为");
            if (request.getSession().getAttribute("accountId") == null)
                throw new UnauthorizedException();
            else
                log.info("用户已登录, accountId:"+request.getSession().getAttribute("accountId"));
        }else
            log.info("无需认证的接口访问");
        return true;
    }

    private boolean hasLoginRequired(Object handler) {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            LoginRequired loginRequired = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
            if(loginRequired==null){
                loginRequired = handlerMethod.getBeanType().getAnnotation(LoginRequired.class);
            }
            return !(loginRequired == null);
        }
        return false;
    }
}
