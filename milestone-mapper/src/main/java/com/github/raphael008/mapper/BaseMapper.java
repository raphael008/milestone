package com.github.raphael008.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T, E, K> {
    long countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(K primaryKey);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(K primaryKey);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
