package com.practice.boot.dao;

import com.practice.boot.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<UserEntity,Long> {
    UserEntity save(UserEntity userEntity);

    //使用方法名查询
    List<UserEntity> findByAddress(String address);

    //使用方法名查询
    UserEntity findByNameAndAddress(String name, String address);

    //使用Query，按参数绑定
    @Query("select u from UserEntity u where u.name= :name and u.address= :address")
    UserEntity nameAndAddressQuery(@Param("name") String name, @Param("address") String address);

    //使用NamedQuery
    List<UserEntity> nameAndAddressNamedQuery(String name, String address);
}
