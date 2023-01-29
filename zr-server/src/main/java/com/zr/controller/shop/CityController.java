package com.zr.controller.shop;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.shop.CityService;
import com.zr.util.AjaxResult;
import com.zr.vo.shop.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping("/queryCity")
    public String queryCity(@RequestBody City city){

        List<City> cityList = cityService.queryCity(city);
        PageInfo<City> pageInfo = new PageInfo<>(cityList);
        return JSON.toJSONString(AjaxResult.success("查询成功",pageInfo));
    }

    /**
     * 供shop通过城市ID查城市和省份,问题：更改为门店名称！！！！！，但是方法名字没改！！！！！
     */
    @RequestMapping("/listByCityId")
    public String listByCityId(City city){

        List<City> cityList = cityService.listByCityId(city);
        return JSON.toJSONString(AjaxResult.success("查询成功",cityList));
    }

    /**
     * 新增城市
     */
    @RequestMapping("/add")
    public String add(@RequestBody City city){
        cityService.add(city);
        return JSON.toJSONString(AjaxResult.success("添加成功"));
    }

    /**
     * 删除城市
     */
    @RequestMapping("/del")
    public String del(City city) {
        cityService.del(city);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }
}
