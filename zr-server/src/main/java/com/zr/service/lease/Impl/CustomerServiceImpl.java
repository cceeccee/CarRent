package com.zr.service.lease.Impl;

import com.github.pagehelper.PageHelper;
import com.mysql.cj.util.StringUtils;
import com.zr.mapper.lease.CustomerMapper;
import com.zr.service.lease.CustomerService;
import com.zr.util.Md5Util;
import com.zr.util.TokenUtil;
import com.zr.vo.lease.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private TokenUtil tokenUtil;


    @Resource
    private CustomerMapper customerMapper;


    /**
     * 查询客户
     * @param customer
     * @return
     */
    @Override
    public List<Customer> list(Customer customer) {
        //分页插件使用，放在接口方法前，只生效一次
        PageHelper.startPage(customer.getPageNum(), customer.getPageSize());
        return customerMapper.list(customer);
    }


    /**
     * 新增客户
     * @param customer
     */
    @Transactional
    @Override
    public void add(Customer customer) {
        //对密码进行加密处理
        customer.setPassword(Md5Util.EncoderByMd5(customer.getPassword()));
        customer.setStatus("1");
        customer.setCreateId(tokenUtil.getUserId());
        customer.setCreateTime(new Date());
        customerMapper.insertSelective(customer);
    }

    @Transactional
    @Override
    public void edit(Customer customer) {
        if (!StringUtils.isNullOrEmpty(customer.getPassword())){
            customer.setPassword(Md5Util.EncoderByMd5(customer.getPassword()));
        }else {
            customer.setPassword(null);
        }
        customer.setModifyId(tokenUtil.getUserId());
        customer.setModifyTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    @Transactional
    @Override
    public void del(Long id) {

        customerMapper.deleteByPrimaryKey(id);
    }
}
