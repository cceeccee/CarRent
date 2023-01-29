package com.zr.service.shop;


import com.zr.vo.shop.City;

import java.util.List;

public interface CityService {

    List<City> queryCity(City city);

    List<City> listByCityId(City city);

    void add(City city);

    void del(City city);
}
