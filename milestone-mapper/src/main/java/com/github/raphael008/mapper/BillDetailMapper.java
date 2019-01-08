package com.github.raphael008.mapper;

import com.github.raphael008.model.BillDetail;
import com.github.raphael008.model.BillDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailMapper extends BaseMapper<BillDetail, BillDetailExample, Long> {
}