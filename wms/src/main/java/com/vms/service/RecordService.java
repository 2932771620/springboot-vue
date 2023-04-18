package com.vms.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vms.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vms.entity.RecordRes;

import java.sql.Wrapper;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2023-01-31
 */
public interface RecordService extends IService<Record> {

    IPage pageCC(IPage<RecordRes> page, QueryWrapper wrapper);
}
