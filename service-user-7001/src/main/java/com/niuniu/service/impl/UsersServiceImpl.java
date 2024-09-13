package com.niuniu.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niuniu.mapper.UsersMapper;
import com.niuniu.pojo.Users;
import com.niuniu.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author niuniu
 * @since 2024-09-13
 */
@Service
@Slf4j
public class UsersServiceImpl  implements UsersService {
   @Autowired
   private UsersMapper usersMapper;
    @Override
    @Transactional
    public void save(Users user) {
      int  i = usersMapper.insert(user);
    }
}
