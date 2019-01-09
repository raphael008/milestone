package com.github.raphael008.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseMapper<T, E, K> {
    long countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(K primaryKey);

    int insert(T record);

    int insertRange(List<T> records);

    int insertSelective(T record);

    int insertRangeSelective(List<T> records);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(K primaryKey);

    int updateByExampleSelective(@Param("record") T record, @Param("example") E example);

    int updateByExample(@Param("record") T record, @Param("example") E example);

    int updateByPrimaryKeySelective(T record);

    int updateRangeByPrimaryKeySelective(List<T> records);

    int updateByPrimaryKey(T record);

    int updateRangeByPrimaryKey(List<T> records);
}
