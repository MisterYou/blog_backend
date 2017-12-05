package com.youjiniot.service;

import com.youjiniot.domain.Role;

import java.util.List;

/**
 * Created by youjin on 2017/11/30.
 */
public interface RoleService {
    List<Role> list();
    Role get(Long id);

    int save(Role role) throws Exception;

    int update(Role role);
    int delete(Long id);
    int batchremove(Long[] ids);


}
