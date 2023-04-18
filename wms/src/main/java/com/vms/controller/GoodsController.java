package com.vms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import com.vms.common.QueryPageParam;
import com.vms.common.Result;
import com.vms.entity.Goods;
import com.vms.entity.Storage;
import com.vms.service.GoodsService;
import com.vms.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-01-30
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {


    @Autowired
    private GoodsService goodsService;

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody Goods goods){
        return goodsService.updateById(goods);
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        return goodsService.save(goods)?Result.success(null,0):Result.fail();
    }
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List<Goods> list = goodsService.lambdaQuery().eq(Goods::getId, no).list();
        return list.size()>0?Result.success(list,list.size()):Result.fail();
    }
    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Goods goods){
        return goodsService.saveOrUpdate(goods);
    }
    //删除
    @GetMapping("/delete")
    public Result delete(Integer id){
        return goodsService.removeById(id)?Result.success(null,0):Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods){
        return goodsService.updateById(goods)?Result.success(null,0):Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPc(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name =(String) param.get("name");
        String goodstype =(String) param.get("goodstype");
        String storage =(String) param.get("storage");
        Page<Goods> userPage = new Page<>();
        userPage.setCurrent(query.getPageNum());
        userPage.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> LambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isNullOrEmpty(name)&&!"null".equals(name)){
            LambdaQueryWrapper.like(Goods::getName,name);
        }
        if(!StringUtils.isNullOrEmpty(goodstype)&&!"null".equals(goodstype)){
            LambdaQueryWrapper.eq(Goods::getGoodstype,goodstype);
        }
        if(!StringUtils.isNullOrEmpty(storage)&&!"null".equals(storage)){
            LambdaQueryWrapper.eq(Goods::getStorage,storage);
        }

        Page<Goods> page = goodsService.page(userPage, LambdaQueryWrapper);
        return Result.success(page.getRecords(),page.getTotal());
    }

}
