package com.zr.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.zr.mapper.sys.RoleAuthMapper;
import com.zr.mapper.sys.RoleMapper;
import com.zr.service.sys.RoleService;
import com.zr.util.TokenUtil;
import com.zr.vo.sys.Role;
import com.zr.vo.sys.RoleAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private TokenUtil tokenUtil;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleAuthMapper roleAuthMapper;

    @Override
    public List<Role> selectList(Role role) {
        return roleMapper.selectList();
    }

    private Long[] authKey;

    @Override
    public List<Role> roleList(Role role) {
        //分页插件使用，放在接口方法前，只生效一次
        PageHelper.startPage(role.getPageNum(),role.getPageSize());
        return roleMapper.roleList(role);
    }

    @Transactional
    @Override
    public void add(Role role) {

        authKey = role.getAuthKey();

        role.setCreateId(tokenUtil.getUserId());
        role.setCreateTime(new Date());


        roleMapper.insert(role);

        if (role.getAuthKey() != null){
            for (Long authId : authKey){
                RoleAuth roleAuth = new RoleAuth();
                roleAuth.setRoleId(role.getId());
                roleAuth.setAuthId(authId);
                roleAuthMapper.insert(roleAuth);
            }
        }
    }

    @Transactional
    @Override
    public void edit(Role role) {

        //获取角色权限
        authKey = role.getAuthKey();

        //删除角色所有权限
        roleAuthMapper.delAllByRoleId(role.getId());

        //判断权限是否有修改
        if (role.getAuthKey() != null){
            for (Long authId : authKey){
                RoleAuth roleAuth = new RoleAuth();
                roleAuth.setRoleId(role.getId());
                roleAuth.setAuthId(authId);
                roleAuthMapper.insert(roleAuth);
            }
        }

        role.setModifyId(tokenUtil.getUserId());
        role.setCreateTime(new Date());
        roleMapper.updateByPrimaryKeySelective(role);

    }

    @Transactional
    @Override
    public void del(Long id) {
        //删除角色拥有的权限
        roleAuthMapper.delAllByRoleId(id);

        roleMapper.deleteByPrimaryKey(id);
    }
}
