package com.vms.controller;


import com.vms.common.Result;
import com.vms.entity.Menu;
import com.vms.entity.user;
import com.vms.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2023-01-27
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/List")
    @Cacheable(value = "menu",key = "#root.method.name",sync = true)
    public Result findByNo(@RequestParam String roleId){
        List<Menu> list = menuService.lambdaQuery().like(Menu::getMenuright,roleId).list();
        return list.size()>0?Result.success(list,list.size()):Result.fail();
    }

}
