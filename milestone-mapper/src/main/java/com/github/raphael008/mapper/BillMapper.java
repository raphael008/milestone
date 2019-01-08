package com.github.raphael008.mapper;

import com.github.raphael008.model.Bill;
import com.github.raphael008.model.BillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillMapper extends BaseMapper<Bill, BillExample, Long> {
}