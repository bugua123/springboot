package com.wzw.microboot.service.impl;

import com.wzw.microboot.entity.User;
import com.wzw.microboot.mapper.UserMapper;
import com.wzw.microboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User getOne(String id) {
      return  userMapper.getOne(id);
    }

    @Override
    public void delete(String id) {
        userMapper.delete(id);
    }

    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
     userMapper.update(user);
    }
}
