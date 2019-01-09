package com.github.raphael008.service.impl;

import com.github.raphael008.mapper.BaseMapper;
import com.github.raphael008.service.BaseService;

import java.util.List;

public abstract class BaseServiceImpl<T, E, K> implements BaseService<T, E, K> {

    protected abstract BaseMapper<T, E, K> getMapper();

    @Override
    public long countByExample(E example) {
        return this.getMapper().countByExample(example);
    }

    @Override
    public int deleteByExample(E example) {
        return this.getMapper().deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(K primaryKey) {
        return this.getMapper().deleteByPrimaryKey(primaryKey);
    }

    @Override
    public int insert(T record) {
        return this.getMapper().insert(record);
    }

    @Override
    public int insertRange(List<T> records) {
        return this.getMapper().insertRange(records);
    }

    @Override
    public int insertSelective(T record) {
        return this.getMapper().insertSelective(record);
    }

    @Override
    public int insertRangeSelective(List<T> records) {
        return this.getMapper().insertRangeSelective(records);
    }

    @Override
    public List selectByExample(E example) {
        return this.getMapper().selectByExample(example);
    }

    @Override
    public T selectByPrimaryKey(K primaryKey) {
        return (T) this.getMapper().selectByPrimaryKey(primaryKey);
    }

    @Override
    public int updateByExampleSelective(T record, E example) {
        return this.getMapper().updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(T record, E example) {
        return this.getMapper().updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return this.getMapper().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateRangeByPrimaryKeySelective(List<T> records) {
        return this.getMapper().updateRangeByPrimaryKeySelective(records);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return this.getMapper().updateByPrimaryKey(record);
    }

    @Override
    public int updateRangeByPrimaryKey(List<T> records) {
        return this.updateRangeByPrimaryKey(records);
    }
}
