package com.youjiniot.service.serviceImpl;

import com.youjiniot.dao.RoleDAO;
import com.youjiniot.dao.RoleModuleDao;
import com.youjiniot.domain.Role;
import com.youjiniot.domain.RoleModuleDO;
import com.youjiniot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by youjin on 2017/11/30.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO dao;
    @Autowired
    private RoleModuleDao roleModuleDao;


    @Override
    public List<Role> list() {
        return dao.list();
    }

    @Override
    public Role get(Long id) {
        return dao.get(id);
    }

    @Override
    public int update(Role role) {
        try {
            int r = dao.update(role);
            List<Long> menuIds = role.getModuleIds();
            int roleId = role.getId();
            List<RoleModuleDO> rms = new ArrayList<>();
            for (Long menuId : menuIds) {
                RoleModuleDO rmDo = new RoleModuleDO();
                rmDo.setRoleId(Long.valueOf(roleId));
                rmDo.setMenuId(menuId);
                rms.add(rmDo);
            }
            roleModuleDao.removeByRoleId(Long.valueOf(roleId));
            if (rms.size() > 0) {
                roleModuleDao.batchSave(rms);
            }
            return r;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(Long id) {
        int count = 0;
        try {
            count = dao.delete(id);
            roleModuleDao.removeByRoleId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}
