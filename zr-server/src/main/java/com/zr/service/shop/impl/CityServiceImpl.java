package com.zr.service.shop.impl;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.shop.CityMapper;
import com.zr.service.shop.CityService;
import com.zr.util.TokenUtil;
import com.zr.vo.shop.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Resource
    private CityMapper cityMapper;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public List<City> queryCity(City city) {
        //分页插件使用，放在接口方法前，只生效一次
        PageHelper.startPage(city.getPageNum(),city.getPageSize());
        return cityMapper.queryCity(city);
    }

    @Override
    public List<City> listByCityId(City city) {
        return cityMapper.listByCityId(city);
    }

    @Transactional
    @Override
    public void add(City city) {
        city.setCreateId(tokenUtil.getUserId());
        city.setCreateTime(new Date());
        cityMapper.insert(city);
    }

    @Transactional
    @Override
    public void del(City city) {
        cityMapper.deleteByPrimaryKey(city.getId());
    }
}
