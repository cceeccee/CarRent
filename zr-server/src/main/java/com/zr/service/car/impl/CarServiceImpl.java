package com.zr.service.car.impl;

import com.github.pagehelper.PageHelper;

import com.mysql.cj.util.StringUtils;
import com.zr.mapper.car.CarMapper;
import com.zr.service.car.CarService;
import com.zr.util.RedisUtils;
import com.zr.vo.car.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {


    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private CarMapper carMapper;

    /**
     * 车辆查询
     * @param car
     * @return
     */
    @Override
    public List<Car> list(Car car) {
        //分页插件使用，放在接口方法前，只生效一次
        PageHelper.startPage(car.getPageNum(),car.getPageSize());
        //查询车辆列表
        return carMapper.list(car);
    }

    /**
     * 新增车辆方法
     * @param car
     */
    @Override
    public void add(Car car) {
        carMapper.insertSelective(car);
    }

    /**
     * 编辑车辆
     * @param car
     */
    @Override
    public void edit(Car car) {
        carMapper.updateByPrimaryKeySelective(car);
    }

    /**
     * 删除车辆
     * @param id
     */
    @Override
    public void del(Long id) {
        carMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据文件地址生成base64编码
     * @param imgUrl
     * @return
     */
    @Override
    public String loadImgByImgUrl(String imgUrl) {
        if(StringUtils.isNullOrEmpty(imgUrl)) return "";
        byte[] b = new byte[0];
        File file = new File(imgUrl);
        try(FileInputStream fis = new FileInputStream(file)){
            b =  new byte[(int)file.length()];
            fis.read(b);
        }catch (Exception e){}
        return Base64.getEncoder().encodeToString(b);
    }




}
