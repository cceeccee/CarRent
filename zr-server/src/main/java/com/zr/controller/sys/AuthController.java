package com.zr.controller.sys;

import com.alibaba.fastjson.JSON;
import com.zr.service.sys.AuthService;
import com.zr.util.AjaxResult;
import com.zr.vo.sys.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sys/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 查询树形权限
     * @param auth
     * @return
     */
    @RequestMapping("/fetchNodeList")
    public String fetchNodeList(Auth auth){
        List<Auth> authList = authService.fetchNodeList(auth);
        return JSON.toJSONString(AjaxResult.success("查询成功",authList));
    }

    /**
     * 新增权限
     * @param auth
     * @return
     */
    @RequestMapping("/add")
    public String add(@RequestBody Auth auth){
        authService.add(auth);
        return JSON.toJSONString(AjaxResult.success("新增成功"));
    }


    /**
     * 新增权限
     * @param auth
     * @return
     */
    @RequestMapping("/edit")
    public String edit(@RequestBody Auth auth){
        authService.edit(auth);
        return JSON.toJSONString(AjaxResult.success("修改成功"));
    }

    /**
     * 删除权限
     */
    @RequestMapping("/del")
    public String del(@RequestBody Auth auth){
        authService.del(auth);
        return JSON.toJSONString(AjaxResult.success("删除成功"));
    }
}

