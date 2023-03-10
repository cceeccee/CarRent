package com.zr.controller.shop;

import com.alibaba.fastjson.JSON;
import com.zr.service.shop.ProvinceService;
import com.zr.util.AjaxResult;
import com.zr.vo.shop.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop/province")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;


    /**
     * 根据id查询省份名称
     */
    public String listById(Province province){

        List<Province> provinceList = provinceService.listById(province);
        return JSON.toJSONString(AjaxResult.success("查询成功",provinceList));

    }

    /**
     * 查询省份名称
     */
    @RequestMapping("/listProvince")
    public String listProvince(@RequestBody Province province){

        List<Province> provinceList = provinceService.listProvince(province);
        return JSON.toJSONString(AjaxResult.success("查询成功",provinceList));
    }

}
