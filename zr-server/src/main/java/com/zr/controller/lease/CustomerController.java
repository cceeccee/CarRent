package com.zr.controller.lease;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.lease.CustomerService;
import com.zr.util.AjaxResult;
import com.zr.util.RedisUtils;
import com.zr.vo.lease.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lease/customer")
public class CustomerController {

//    @Autowired
//    private CustomerService customerService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping("/list")
    public String list(@RequestBody Customer customer){

        //查询客户列表
        List<Customer> customerList = customerService.list(customer);
        //封装到分页对象中
        PageInfo<Customer> pageInfo = new PageInfo<>(customerList);
        return JSON.toJSONString(AjaxResult.success("查询成功",pageInfo));

    }


    @RequestMapping("/add")
    public String add(Customer customer){
        customerService.add(customer);
        return JSON.toJSONString(AjaxResult.success("新增成功"));

    }

    /**
     * 编辑用户
     * @param customer
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestBody Customer customer){
        customerService.edit(customer);
        return JSON.toJSONString(AjaxResult.success("编辑成功"));
    }

    /**
     * 删除用户
     * @param
     * @return
     */
    @RequestMapping("/del/{id}")
    public String del(@PathVariable Long id){
        customerService.del(id);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }
}
