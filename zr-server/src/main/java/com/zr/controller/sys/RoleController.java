package com.zr.controller.sys;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zr.service.sys.RoleService;
import com.zr.util.AjaxResult;
import com.zr.vo.sys.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;



    /**
     * 获取所有角色列表
     * @return
     */
    @RequestMapping("/selectList")
    public String selectList(Role role){
        List<Role> roleList = roleService.selectList(role);

        return JSON.toJSONString(AjaxResult.success("查询成功", roleList));
    }


    /**
     * 获取角色列表+权限列表
     * @param role
     * @return
     */
    @RequestMapping("/roleList")
    public String roleList(@RequestBody Role role){
        List<Role> roleList = roleService.roleList(role);
        //封装到分页插件中
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);
        return JSON.toJSONString(AjaxResult.success("查询成功", pageInfo));
    }

    /**
     * 新增插入
     * @param role
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestBody Role role){
        roleService.add(role);
        return JSON.toJSONString(AjaxResult.success("新增成功"));
    }


    /**
     * 编辑用户
     * @param role
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestBody Role role){
        roleService.edit(role);
        return JSON.toJSONString(AjaxResult.success("编辑成功"));
    }

    /**
     * 删除用户
     * @param
     * @return
     */
    @RequestMapping("/del/{id}")
    public String del(@PathVariable Long id){
        roleService.del(id);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }

}
