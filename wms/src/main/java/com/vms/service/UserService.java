package com.vms.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vms.entity.user;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2023-01-18
 */
public interface UserService extends IService<user> {

    Page pageCC(Page<user> page, Wrapper Wrapper);
}
