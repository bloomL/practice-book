package com.practice.boot.service;

import com.practice.boot.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findByAddress(String address);

    UserEntity findByNameAndAddress(String name, String address);

    UserEntity nameAndAddressQuery(String name, String address);

    List<UserEntity> nameAndAddressNamedQuery(String name, String address);

    UserEntity saveWithRollBack(UserEntity userEntity);

    UserEntity saveWithoutRollBack(UserEntity userEntity);

    UserEntity save(UserEntity userEntity);
}
