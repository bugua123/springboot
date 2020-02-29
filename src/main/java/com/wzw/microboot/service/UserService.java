package com.wzw.microboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzw.microboot.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService  extends IService<User>{

    /**
     * 保存用户和角色之间的关系
     * @param uid
     * @param ids
     */
    void saveUserRole(Integer uid, Integer[] ids);
}
