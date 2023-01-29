package com.zr.controller.car;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.zr.service.car.CarService;
import com.zr.service.car.WorkService;
import com.zr.util.AjaxResult;
import com.zr.util.TokenUtil;
import com.zr.vo.car.Car;
import com.zr.vo.car.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * 车辆管理类
 */
@RestController
@RequestMapping("/car/carwork")
public class WorkControler {
    @Autowired
    private WorkService workService;

    @Autowired
    private TokenUtil tokenUtil;


    /**
     *车辆查询列表
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestBody Work work){
        //查询车两列表
        List<Work> workList = workService.list(work);
        //封装到分页对象中
        PageInfo<Work> pageInfo = new PageInfo<>(workList);
        return JSON.toJSONString(AjaxResult.success("查询成功",pageInfo));
    }
    /**
     *详情
     * @return
     */
    @Transactional
    @RequestMapping("/listDetail/{id}")
    public String listDetail(@PathVariable("id") Long id){
        //查询详情
        Work work = workService.listDetail(id);
        return JSON.toJSONString(AjaxResult.success("查询成功",work));
    }
    /**
     *出车详情
     * @return
     */
    @Transactional
    @RequestMapping("/listOutDetail/{id}")
    public String listOutDetail(@PathVariable("id") Long id){
        //查询详情
        Work work = workService.listOutDetail(id);
        return JSON.toJSONString(AjaxResult.success("查询成功",work));
    }
    /**
     *入车详情
     * @return
     */
    @Transactional
    @RequestMapping("/listInDetail/{id}")
    public String listInDetail(@PathVariable("id") Long id){
        //查询详情
        Work work = workService.listInDetail(id);
        return JSON.toJSONString(AjaxResult.success("查询成功",work));
    }
}
