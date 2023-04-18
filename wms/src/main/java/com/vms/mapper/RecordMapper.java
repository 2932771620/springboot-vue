package com.vms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.vms.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vms.entity.RecordRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Wrapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wms
 * @since 2023-01-31
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    IPage pageCC(IPage<RecordRes> page, @Param(Constants.WRAPPER) QueryWrapper wrapper);
}
