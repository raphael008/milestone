package com.github.raphael008.service.impl;

import com.github.raphael008.mapper.BaseMapper;
import com.github.raphael008.mapper.RoleMapper;
import com.github.raphael008.model.Role;
import com.github.raphael008.model.RoleExample;
import com.github.raphael008.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleExample, Long> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    protected BaseMapper getMapper() {
        return roleMapper;
    }
}