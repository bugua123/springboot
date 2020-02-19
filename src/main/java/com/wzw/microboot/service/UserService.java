package com.wzw.microboot.service;

import com.wzw.microboot.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    List<User> selectAll();
    User getOne(String id);
    void delete(String id);
    void insert(User user);
    void update(User user);
    User checkUser(String userName,String passWord);

}
