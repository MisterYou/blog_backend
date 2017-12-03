package com.youjiniot.service.serviceImpl;

import com.youjiniot.dao.RoleDAO;
import com.youjiniot.domain.Role;
import com.youjiniot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by youjin on 2017/11/30.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO dao;

    @Override
    public List<Role> list() {
        return dao.list();
    }
}
