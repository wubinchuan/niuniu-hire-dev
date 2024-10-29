package com.niuniu.service.impl;

import com.niuniu.mapper.UsersMapper;
import com.niuniu.pojo.Users;
import com.niuniu.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersServiceimpl implements UsersService {

  @Autowired
  private  UsersMapper usersMapper;
    @Override
    public Users userisExsts(String mobile) {
       Users user= usersMapper.getUserExsis(mobile);
       return user;
    }

    @Override
    @Transactional
    public Users Createuser(String mobile) {
        //构建一个Users对象
        Users user= new Users();
        return user;
    }
}
