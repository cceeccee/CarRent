package com.zr.controller.lease;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.lease.AgreementService;
import com.zr.util.AjaxResult;
import com.zr.util.RedisUtils;
import com.zr.vo.lease.Agreement;
import com.zr.vo.lease.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/lease/agreement")


public class AgreementController {


    @Autowired
    private AgreementService agreementService;

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/list")
    public String list(@RequestBody Agreement agreement){

        //查询用户列表
        List<Agreement> agreementList = agreementService.list(agreement);

        //封装分页对象
        PageInfo<Agreement> pageInfo = new PageInfo<>(agreementList);

        return JSON.toJSONString(AjaxResult.success("查询成功",pageInfo));

    }

    @RequestMapping("/add")
    public String add(Agreement agreement){
        agreementService.add(agreement);
        return JSON.toJSONString(AjaxResult.success("新增成功"));
    }


    @RequestMapping("/del/")
    public String del(@PathVariable("name") Long name){
        agreementService.del(name);
        return JSON.toJSONString(AjaxResult.success("删除成功"));

    }



}
