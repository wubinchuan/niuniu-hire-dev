package com.niuniu.interceptor;

import com.niuniu.base.BaseInfoProperties;
import com.niuniu.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@Component
public class SMSinterceptor extends BaseInfoProperties implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求先来到这里
        String userip= IPUtil.getRequestIp(request);
        boolean ipExists= redis.keyIsExist((MOBILE_SMSCODE+":"+userip));

        if(ipExists){
            log.info("短信发送频率太高啦");
           return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //请求访问到controller之后
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //处理完成
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
