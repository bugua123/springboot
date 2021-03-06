package com.wzw.microboot.mapper;

import com.wzw.microboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
public interface UserMapper {

    List<User> selectAll();
    User getOne(String id);
    void delete(String id);
    void insert(User user);
    void update(User user);
    User checkUser(@Param("userName")  String userName,@Param("passWord") String passWord);
}
