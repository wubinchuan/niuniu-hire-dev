package com.niuniu.controller;

import com.niuniu.base.BaseInfoProperties;
import com.niuniu.bo.RegistLogBo;
import com.niuniu.pojo.Users;
import com.niuniu.result.GraceJSONResult;
import com.niuniu.result.ResponseStatusEnum;
import com.niuniu.service.UsersService;
import com.niuniu.utils.IPUtil;
import com.niuniu.utils.SMSUtils;
import com.niuniu.vo.UsersVO;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/passport")
@Slf4j
public class PassPortController extends BaseInfoProperties {
    @Autowired
    private SMSUtils smsUtils;
    @Autowired
    private UsersService usersService;
    @GetMapping("/getSMSCode")
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
    @PostMapping("/login")
    public GraceJSONResult login(@Valid @RequestBody RegistLogBo registLogBo, HttpServletRequest request) throws Exception {

        String mobile=registLogBo.getMobiile();
        String smscode=registLogBo.getSmsCode();
        String redisCode= redis.get(MOBILE_SMSCODE+":"+mobile);
        //equals区分大小写 equalsIgnoreCase 不区分
        if(StringUtils.isBlank(redisCode) || !redisCode.equalsIgnoreCase(registLogBo.getSmsCode())){
               return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);
        }
        Users users= usersService.userisExsts(mobile);
        if(users == null){
            //创建用户
            users=usersService.Createuser(mobile);
        }
        //存储分布式token
        String utoken= TOKEN_ADMIN_PREFIX  + SYMBOL_DOT+ UUID.randomUUID().toString();
        redis.set(REDIS_USER_TOKEN+":"+users.getId(),utoken);
        redis.del(MOBILE_SMSCODE+":"+mobile);
        UsersVO uservo=new UsersVO();
        BeanUtils.copyProperties(uservo,uservo);
        uservo.setUserToken(utoken);
        return GraceJSONResult.ok(uservo);
    }
    @PostMapping("/logout")
    public GraceJSONResult logout(@RequestParam String userId, HttpServletRequest request) throws Exception {
        redis.del(REDIS_USER_TOKEN+":"+userId);
        return GraceJSONResult.ok();
    }
}
