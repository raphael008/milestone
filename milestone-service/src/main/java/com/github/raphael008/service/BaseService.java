package com.github.raphael008.service;

import java.util.List;

public interface BaseService<T, E, K> {
    long countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(K primaryKey);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(E example);

    T selectByPrimaryKey(K primaryKey);

    int updateByExampleSelective(T record, E example);

    int updateByExample(T record, E example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
