package com.practice.boot.service.impl;

import com.practice.boot.dao.UserDao;
import com.practice.boot.entity.UserEntity;
import com.practice.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    //新增的或更新的的数据到缓存
    @CachePut(value = "user",key = "#userEntity.id")
    public UserEntity save(UserEntity userEntity) {
        UserEntity user = userDao.save(userEntity);
        System.out.println("将id为："+user.getId()+"作为key的数据缓存");
        return user;
    }

    @Override
    //从缓存删除key为id值的数据
    @CacheEvict(value = "user")
    public void remove(Long id) {
        System.out.println("删除了key为："+id+"的数据缓存");
        userDao.deleteById(id);
    }

    @Override
    //将key为user.getId数据缓存到user中
    @Cacheable(value = "user",key = "#userEntity.id")
    public UserEntity findOne(UserEntity userEntity) {
        UserEntity user = userDao.getOne(userEntity.getId());
        System.out.println("将id为："+user.getId()+"作为key的数据缓存");
        return user;
    }
}
