package com.zr.service.sys;

import com.zr.vo.sys.Auth;

import java.util.List;

public interface AuthService {

    List<Auth> fetchNodeList(Auth auth);

    void add(Auth auth);

    void edit(Auth auth);

    void del(Auth auth);
}
