package com.zr.service.sys.impl;

import com.zr.mapper.sys.AuthMapper;
import com.zr.service.sys.AuthService;
import com.zr.util.TokenUtil;
import com.zr.vo.sys.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private TokenUtil tokenUtil;

    @Resource
    private AuthMapper authMapper;

    @Override
    public List<Auth> fetchNodeList(Auth auth) {
        return authMapper.fetchNodeList(auth);
    }

    /**
     * 新增权限
     * @param auth
     */
    @Transactional
    @Override
    public void add(Auth auth) {

        auth.setCreateId(tokenUtil.getUserId());
        auth.setCreateTime(new Date());

        authMapper.insert(auth);

    }

    /**
     * 修改权限
     * @param auth
     */
    @Transactional
    @Override
    public void edit(Auth auth) {
        auth.setModifyId(tokenUtil.getUserId());
        auth.setCreateTime(new Date());

        authMapper.updateByPrimaryKey(auth);
    }

    @Override
    public void del(Auth auth) {
        authMapper.deleteByPrimaryKey(auth.getId());
    }
}
