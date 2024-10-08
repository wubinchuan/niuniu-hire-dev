package com.niuniu.controller;

import com.niuniu.base.BaseInfoProperties;
import com.niuniu.result.GraceJSONResult;
import com.niuniu.utils.IPUtil;
import com.niuniu.utils.SMSUtils;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/passport")
@Slf4j
public class PassPortController extends BaseInfoProperties {
    @Autowired
    private SMSUtils smsUtils;
    @GetMapping("/getSMsCode")
    public GraceJSONResult sendCode(String mobile, HttpServletRequest request) throws Exception {
        if(StringUtils.isBlank(mobile)){
            return GraceJSONResult.error();
        }

        //限制用户60秒内只能获取一次
        String userip= IPUtil.getRequestIp(request);
        redis.setnx60s(MOBILE_SMSCODE+":"+userip,mobile);

        String smscode=(int)((Math.random()*9+1)*100000)+"";
        smsUtils.sendSMS(mobile,smscode);
        redis.set(MOBILE_SMSCODE+":"+mobile,smscode,606*30);
        log.info("验证码为：{}",smscode);
        return GraceJSONResult.ok();
    }
}
