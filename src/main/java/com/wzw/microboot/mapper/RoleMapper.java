package com.wzw.microboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzw.microboot.entity.Role;

import java.io.Serializable;

public interface RoleMapper extends BaseMapper<Role> {

    //根据角色ID删除sys_role_permission
    void deleteRolePermissionByRid(Serializable id);
    //根据角色ID删除sys_role_user
    void deleteRoleUserByRid(Serializable id);
}
