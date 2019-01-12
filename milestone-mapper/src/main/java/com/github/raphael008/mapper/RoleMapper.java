package com.github.raphael008.mapper;

import com.github.raphael008.model.Role;
import com.github.raphael008.model.RoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper extends BaseMapper<Role, RoleExample, Long> {
}