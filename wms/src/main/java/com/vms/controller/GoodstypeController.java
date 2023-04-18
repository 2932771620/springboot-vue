package com.vms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.cj.util.StringUtils;
import com.vms.common.QueryPageParam;
import com.vms.common.Result;
import com.vms.entity.Goods;
import com.vms.entity.Goodstype;

import com.vms.entity.Menu;
import com.vms.entity.user;
import com.vms.service.GoodstypeService;
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
 * @since 2023-01-29
 */
@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {

    @Autowired
    private GoodstypeService goodstypeService;

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody Goodstype goodstype){
        return goodstypeService.updateById(goodstype);
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype){
        return goodstypeService.save(goodstype)?Result.success(null,0):Result.fail();
    }


    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody Goodstype goodstype){
        return goodstypeService.saveOrUpdate(goodstype);
    }
    //删除
    @GetMapping("/delete")
    public Result delete(Integer id){
        return goodstypeService.removeById(id)?Result.success(null,0):Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goodstype goodstype){
        return goodstypeService.updateById(goodstype)?Result.success(null,0):Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPc(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name =(String) param.get("name");

        Page<Goodstype> userPage = new Page<>();
        userPage.setCurrent(query.getPageNum());
        userPage.setSize(query.getPageSize());

        LambdaQueryWrapper<Goodstype> LambdaQueryWrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isNullOrEmpty(name)&&!"null".equals(name)){
            LambdaQueryWrapper.like(Goodstype::getName,name);
        }
        Page<Goodstype> page = goodstypeService.page(userPage, LambdaQueryWrapper);
        return Result.success(page.getRecords(),page.getTotal());
    }

    @GetMapping("/List")
    public Result findByNo(){
        List<Goodstype> list =goodstypeService.list();
        return list.size()>0?Result.success(list,list.size()):Result.fail();
    }
}
