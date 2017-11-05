package com.youjiniot.service;

import com.github.pagehelper.Page;
import com.youjiniot.domain.Manager;
import com.youjiniot.domain.Module;

import java.util.List;
import java.util.Set;

/**
 * Created by Y on 2017/11/5.
 */
public interface ManagerService {
    public boolean store(Manager manager);
    public void update(Manager manager,int roleId) throws Exception;
    public void delete(int managerId) throws Exception;
    public Object pagination(Page page);
    public Manager findByAccount(String account);

    public Set<String> findPermissions(String account) ;
    public List<Module> findPermissionsWithArray(String account);
    public List<String> findPermissionUrl(String account);

}
