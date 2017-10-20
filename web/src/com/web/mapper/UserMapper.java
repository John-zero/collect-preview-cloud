package com.web.mapper;

import com.web.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper
{

    List<UserEntity> getAll();

    UserEntity getById(Long id);

    UserEntity getUser(@Param("userName")String userName);

    long insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);

}
