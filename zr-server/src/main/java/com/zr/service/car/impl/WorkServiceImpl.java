package com.zr.service.car.impl;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.car.CarMapper;
import com.zr.mapper.car.WorkMapper;
import com.zr.service.car.WorkService;
import com.zr.util.RedisUtils;
import com.zr.vo.car.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {

    @Autowired
    private RedisUtils redisUtils;

    @Resource
    private WorkMapper workMapper;

    /**
     * 车辆查询
     * @param work
     * @return
     */
    @Transactional
    @Override
    public List<Work> list(Work work) {
        //分页插件使用，放在接口方法前，只生效一次
        PageHelper.startPage(work.getPageNum(),work.getPageSize());
        //查询车辆列表
        return workMapper.list(work);
    }
    @Transactional
    @Override
    public Work listDetail(Long id) {
        return workMapper.listDetail(id);
    }
    @Transactional
    @Override
    public Work listOutDetail(Long id) {
        return workMapper.listOutDetail(id);
    }
    @Transactional
    @Override
    public Work listInDetail(Long id) {
        return workMapper.listInDetail(id);
    }
}
