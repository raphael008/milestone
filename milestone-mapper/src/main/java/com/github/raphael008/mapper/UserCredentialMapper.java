package com.github.raphael008.mapper;

import com.github.raphael008.model.UserCredential;
import com.github.raphael008.model.UserCredentialExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialMapper extends BaseMapper<UserCredential, UserCredentialExample, Long> {
}