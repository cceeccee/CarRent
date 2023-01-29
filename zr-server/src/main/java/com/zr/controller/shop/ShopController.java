package com.zr.controller.shop;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.shop.ShopService;
import com.zr.util.AjaxResult;
import com.zr.vo.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop/shop")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @RequestMapping("/queryShop")
    public String queryShop(@RequestBody Shop shop){

        List<Shop> shopList = shopService.queryShop(shop);

        PageInfo<Shop> pageInfo = new PageInfo<>(shopList);
        return JSON.toJSONString(AjaxResult.success("查询成功",pageInfo));
    }
}
