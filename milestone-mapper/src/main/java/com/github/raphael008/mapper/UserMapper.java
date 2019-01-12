package com.github.raphael008.mapper;

import com.github.raphael008.model.User;
import com.github.raphael008.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User, UserExample, Long> {
    User findByUsername(@Param("username") String username);
}