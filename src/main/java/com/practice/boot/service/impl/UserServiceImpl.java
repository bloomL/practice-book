package com.practice.boot.service.impl;

import com.practice.boot.dao.UserDao;
import com.practice.boot.entity.UserEntity;
import com.practice.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
/*@Transactional*/
public class UserServiceImpl  implements UserService {
    @Autowired
    //Jpa注册bean
    private UserDao userDao;

    @Override
    public List<UserEntity> findByAddress(String address) {
        return userDao.findByAddress(address);
    }

    @Override
    public UserEntity findByNameAndAddress(String name, String address) {
        return userDao.findByNameAndAddress(name,address);
    }

    @Override
    public UserEntity nameAndAddressQuery(String name, String address) {
        return userDao.nameAndAddressQuery(name,address);
    }

    @Override
    public List<UserEntity> nameAndAddressNamedQuery(String name, String address) {
        return userDao.nameAndAddressNamedQuery(name,address);
    }

    @Override
    @Transactional(rollbackFor = {IllegalArgumentException.class})
    public UserEntity saveWithRollBack(UserEntity userEntity) {
        UserEntity user = userDao.save(userEntity);
        if (user.getName().equals("ll")){
            throw new IllegalArgumentException("ll已存在！数据回滚");
        }
        return user;
    }

    @Override
    @Transactional(noRollbackFor =  {IllegalArgumentException.class})
    public UserEntity saveWithoutRollBack(UserEntity userEntity) {
        UserEntity user = userDao.save(userEntity);
        if (user.getName().equals("ll")){
            throw new IllegalArgumentException("ll已存在！数据不回滚");
        }
        return user;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userDao.save(userEntity);
    }
}
