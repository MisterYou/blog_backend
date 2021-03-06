package com.youjiniot.service.serviceImpl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.youjiniot.common.Const;
import com.youjiniot.dao.ManagerDAO;
import com.youjiniot.dao.ModuleDAO;
import com.youjiniot.dao.RoleDAO;
import com.youjiniot.domain.Manager;
import com.youjiniot.domain.Module;
import com.youjiniot.domain.Tree;
import com.youjiniot.service.ManagerService;
import com.youjiniot.utils.BuildTree;
import com.youjiniot.utils.EncryptUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;

import java.util.*;

/**
 * Created by Y on 2017/11/5.
 */
@Service
public class ManagerServiceImpl implements ManagerService{
    @Autowired
    private ManagerDAO managerDAO;
    @Autowired
    private ModuleDAO moduleDAO;
    @Autowired
    private RoleDAO roleDAO;

    public boolean store(Manager manager){
        manager.setCreatedAt(new Date());
        manager.setPassword(EncryptUtils.md5(manager.getAccount()+ Const.PASSWORD_SALT_PART+manager.getPassword()));
        try {
            return managerDAO.insert(manager)>0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    @Override
    @Transactional
    public void update(Manager manager,int roleId) throws Exception {
        if(!StringUtils.isBlank(manager.getPassword())){
            //修改密码
            manager.setPassword(EncryptUtils.md5(manager.getAccount()+ Const.PASSWORD_SALT_PART+manager.getPassword()));
        }
        managerDAO.update(manager);
        //检查用户是否有角色
        if(null==roleDAO.findByAccount(manager.getAccount())){
            //新增角色
            managerDAO.setupRole(manager.getId(),roleId);
        }else{
            //修改角色
            managerDAO.updateRole(manager.getId(),roleId);
        }

    }
    @Override
    @Transactional
    public void delete(int managerId) throws Exception {
        managerDAO.delete(managerId);
        //删除角色关系
        managerDAO.deleteRole(managerId);
    }

    @Override
    public Object pagination(Page page){
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<Manager> managers=managerDAO.findAll();
        Map pageData= ImmutableMap.of("rows",((Page) managers).getTotal(),"total",((Page) managers).getPages(),"current",page.getPageNum());
        return ImmutableMap.of("page",pageData,"data",managers);
    }

    /**
     * 根据账号Account查询当前用户
     * @param account
     * @return
     */
    @Override
    public Manager findByAccount(String account) {
        return managerDAO.findByAccount(account);
    }

    /**
     * 获取资源集合
     * @param account
     * @return
     */
    @Override
    public Set<String>  findPermissions(String account) {
        Set<String> set = Sets.newHashSet();
        Manager user = findByAccount(account);

        List<Module> modules = moduleDAO.findModuleByUserId(user.getId());

        for(Module module: modules) {
            if(null!=module){
                set.add(module.getPerms());
            }

        }
        return set;
    }

    /**
     * 获取权限列表
     * @param account
     * @return
     */
    @Override
    public List<Module> findPermissionsWithArray(String account) {
        Set<String> set = Sets.newHashSet();
        Manager user = findByAccount(account);
        return moduleDAO.findModuleByUserId(user.getId());
    }

    /**
     * 获取URL权限
     * @param account
     * @return
     */
    @Override
    public List<String> findPermissionUrl(String account) {
        List<String> list = Lists.newArrayList();
        Manager user = findByAccount(account);
        List<Module> modules = moduleDAO.findModuleByUserId(user.getId());

        for(Module module: modules) {
            if(null!=module&&module.getType() == Module.URL_TYPE) {
                list.add(module.getPath());
            }
        }
        return list;
    }

    @Override
    public List<Tree<Module>> listMenuTree(int id) {
        List<Tree<Module>> trees = new ArrayList<Tree<Module>>();
        List<Module> modules = moduleDAO.listModuleByUserId(id);
        for(Module sysModule:modules){
            Tree<Module> tree = new Tree<>();
            tree.setId(String.valueOf(sysModule.getId()));
            tree.setParentId(String.valueOf(sysModule.getParentId()));
            tree.setText(sysModule.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url",sysModule.getPath());
            attributes.put("icon",sysModule.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
// 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<Module>> list = BuildTree.buildList(trees, "0");

        return list;
    }
}
