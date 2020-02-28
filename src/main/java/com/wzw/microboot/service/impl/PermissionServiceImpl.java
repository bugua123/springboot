package com.wzw.microboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzw.microboot.entity.Permission;
import com.wzw.microboot.mapper.PermissionMapper;
import com.wzw.microboot.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public boolean removeById(Serializable id) {

        //根据权限或菜单ID删除权限表各和角色的关系表里面的数据
        PermissionMapper permissionMapper = getBaseMapper();
        permissionMapper.deleteRolePermissionByPid(id);
        return super.removeById(id);
    }
}
