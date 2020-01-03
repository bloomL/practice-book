package com.practice.boot.controller;

import com.practice.boot.entity.UserEntity;
import com.practice.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/save")
    public UserEntity save(String name,String address,Integer age){
        UserEntity user = userService.save(new UserEntity(null,name,age,address));
        return user;
    }

    @RequestMapping("/queryByAddress")
    public List<UserEntity> queryByAddress(String address){
        List<UserEntity> userEntities = userService.findByAddress(address);
        return userEntities;
    }

    @RequestMapping("/queryByNameAndAddress")
    public UserEntity queryByNameAndAddress(String name,String address){
        UserEntity user = userService.findByNameAndAddress(name,address);
        return user;
    }

    @RequestMapping("/queryNameAndAddressQuery")
    public UserEntity queryNameAndAddressQuery(String name,String address){
        UserEntity user = userService.nameAndAddressQuery(name,address);
        return user;
    }

    @RequestMapping("/queryNameAndAddressNamedQuery")
    public List<UserEntity> queryNameAndAddressNamedQuery(String name,String address){
        List<UserEntity> userEntities =userService.nameAndAddressNamedQuery(name,address);
        return userEntities;
    }

    @RequestMapping("/rollBack")
    public UserEntity rollBack(UserEntity userEntity){
        UserEntity user = userService.saveWithRollBack(userEntity);
        return user;
    }

    @RequestMapping("/noRollBack")
    public UserEntity noRollBack(UserEntity userEntity){
        UserEntity user = userService.saveWithoutRollBack(userEntity);
        return user;
    }

    /*@RequestMapping("/sort")
    public List<UserEntity> sort(){
        List<UserEntity> all = userDao.findAll(new Sort.by(Direction.ASC,"age"));
        return all;
    }

    @RequestMapping("/page")
    public Page<UserEntity> page(){
        Page<UserEntity> all = userDao.findAll(new PageRequest.of(1,2));
        return all;
    }*/
}
