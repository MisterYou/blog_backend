package com.youjiniot.service;

import com.youjiniot.domain.Role;

import java.util.List;

/**
 * Created by youjin on 2017/11/30.
 */
public interface RoleService {
    List<Role> list();
    Role get(Long id);

}
