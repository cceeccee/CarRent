package com.zr.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.shop.ShopMapper;
import com.zr.service.shop.ShopService;
import com.zr.vo.shop.Shop;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Resource
    private ShopMapper shopMapper;

    @Override
    public List<Shop> queryShop(Shop shop) {
        PageHelper.startPage(shop.getPageNum(),shop.getPageSize());
        return shopMapper.queryShop(shop);
    }
}
