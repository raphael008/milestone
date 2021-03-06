package com.github.raphael008.controller.impl;

import com.github.raphael008.controller.BaseController;
import com.github.raphael008.service.BaseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public abstract class BaseControllerImpl<T, E, K> implements BaseController<T, E, K> {

    protected abstract BaseService getService();

    @Override
    public long countByExample(E example) {
        return this.getService().countByExample(example);
    }

    @Override
    public int deleteByExample(E example) {
        return this.getService().deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(K primaryKey) {
        return this.getService().deleteByPrimaryKey(primaryKey);
    }

    @Override
    public int insert(T record) {
        return this.getService().insert(record);
    }

    @Override
    public int insertSelective(T record) {
        return this.getService().insertSelective(record);
    }

    @PostMapping(value = "findAll", consumes = "application/json", produces = "application/json")
    @Override
    public List selectByExample(E example) {
        return this.getService().selectByExample(example);
    }

    @PostMapping(value = "findById", consumes = "application/json", produces = "application/json")
    @Override
    public T selectByPrimaryKey(@RequestBody K primaryKey) {
        return (T) this.getService().selectByPrimaryKey(primaryKey);
    }

    @Override
    public int updateByExampleSelective(T record, E example) {
        return this.getService().updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(T record, E example) {
        return this.getService().updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(T record) {
        return this.getService().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(T record) {
        return this.getService().updateByPrimaryKey(record);
    }
}
