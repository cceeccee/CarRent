package com.zr.controller.car;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.zr.service.car.CarService;
import com.zr.util.AjaxResult;
import com.zr.util.TokenUtil;
import com.zr.vo.car.Car;
import com.zr.vo.sys.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 车辆管理类
*/
@RestController
@RequestMapping("/car/carcar")
public class CarControler {

    @Autowired
    private CarService carService;

    @Autowired
    private TokenUtil tokenUtil;


    /**
     *车辆删除
     * @return
     */
    @RequestMapping("/del/{id}")
    public String del(@PathVariable("id") Long id){
        carService.del(id);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }


    /**
     *车辆查询列表
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestBody  Car car){
        //查询车两列表
        List<Car> carList = carService.list(car);
        //封装到分页对象中
        PageInfo<Car> pageInfo = new PageInfo<>(carList);
        return JSON.toJSONString(AjaxResult.success("查询成功",pageInfo));
    }

    /**
     * 车辆新增
     * @param car
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestBody Car car){
        car.setCreateId(tokenUtil.getUserId());
        car.setCreateTime(new Date());
        carService.add(car);
        return JSON.toJSONString(AjaxResult.success("新增成功"));
    }
    /**
     * 编辑
     * @param car
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestBody  Car car){
        car.setModifyId(tokenUtil.getUserId());
        car.setModifyTime(new Date());
        carService.edit(car);
        return JSON.toJSONString(AjaxResult.success("编辑成功"));
    }
    /**
     * 编辑
     * @param car
     * @return
     */
    @RequestMapping("/showImg")
    public void showImg(@RequestBody  Car car){
        Map dataMap = new HashMap();
        dataMap.put("base64",carService.loadImgByImgUrl(car.getImgUrl()));
    }
}
