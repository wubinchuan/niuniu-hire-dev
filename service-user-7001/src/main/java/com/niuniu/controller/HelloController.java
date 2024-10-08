package com.niuniu.controller;

import com.niuniu.pojo.Users;
import com.niuniu.result.GraceJSONResult;
import com.niuniu.result.ResponseStatusEnum;
import com.niuniu.service.UsersService;
import com.niuniu.utils.SMSUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/user")
@Slf4j
public class HelloController {
  @Autowired
  private  UsersService usersService;
  @Autowired
  private SMSUtils smsUtils;
   @GetMapping("/hello")
    public GraceJSONResult Hello(){

      int random=1000+new Random().nextInt(9000);
//      Users user= usersService.getById("1534756548502724610");
      return  GraceJSONResult.ok("hello gateway");
    }
}
