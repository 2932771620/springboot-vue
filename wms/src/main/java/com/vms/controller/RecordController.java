package com.vms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import com.vms.common.QueryPageParam;
import com.vms.common.Result;
import com.vms.entity.Goods;
import com.vms.entity.Record;
import com.vms.entity.RecordRes;
import com.vms.service.GoodsService;
import com.vms.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-01-31
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private GoodsService goodsService;


    //新增
    @PostMapping("/save")
    @CacheEvict(value = "menu",key = "'listPc'")
    public Result save(@RequestBody Record record){
        Goods goods = goodsService.getById(record.getGoods());
        Integer count = record.getCount();
        //出库
        if(record.getAction().equals("2")){
            count=-count;
            record.setCount(count);
        }
        Integer count1 = goods.getCount()+count;
        goods.setCount(count1);

        goodsService.updateById(goods);
        return recordService.save(record)?Result.success(null,0):Result.fail();
    }


    @PostMapping("/listPage")
    @Cacheable(value = "menu",key = "#root.method.name",sync = true)
    public Result listPc(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name =(String) param.get("name");
        String goodstype =(String) param.get("goodstype");
        String storage =(String) param.get("storage");
        Page<RecordRes> userPage = new Page<>();
        userPage.setCurrent(query.getPageNum());
        userPage.setSize(query.getPageSize());

        QueryWrapper<RecordRes> LambdaQueryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper.apply("a.goods=b.id and b.storage=c.id and b.goodstype=d.id");
        if(!StringUtils.isNullOrEmpty(name)&&!"null".equals(name)){
            LambdaQueryWrapper.like("b.name",name);
        }
        if(!StringUtils.isNullOrEmpty(goodstype)&&!"null".equals(goodstype)){
            LambdaQueryWrapper.eq("d.id",goodstype);
        }
        if(!StringUtils.isNullOrEmpty(storage)&&!"null".equals(storage)){
            LambdaQueryWrapper.eq("c.id",storage);
        }

        IPage page = recordService.pageCC(userPage, LambdaQueryWrapper);
        return Result.success(page.getRecords(),page.getTotal());
    }

}
