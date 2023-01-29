package com.zr.service.lease.Impl;

import com.github.pagehelper.PageHelper;

import com.zr.mapper.lease.AgreementMapper;
import com.zr.service.lease.AgreementService;
import com.zr.util.Md5Util;
import com.zr.util.TokenUtil;
import com.zr.vo.lease.Agreement;
import com.zr.vo.sys.UserRole;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class AgreementServiceImpl implements AgreementService {

    @Autowired
    private TokenUtil tokenUtil;


    @Resource
    private AgreementMapper agreementMapper;

    @Override
    public List<Agreement> list(Agreement agreement) {
        //分页插件使用
        PageHelper.startPage(agreement.getPageNum(),agreement.getPageSize());
        return agreementMapper.list(agreement);
    }

    @Override
    public void add(Agreement agreement) {
        agreementMapper.insertSelective(agreement);

    }

    @Transactional
    @Override
    public void edit(Agreement agreement) {


    }


    @Transactional
    @Override
    public void del(Long name) {
        agreementMapper.deleteByPrimaryKey(name);

    }
}
