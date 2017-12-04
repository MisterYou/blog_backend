package com.youjiniot.dao;

import com.youjiniot.domain.RoleModuleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 角色与菜单对应关系
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-10-03 11:08:59
 */
@Repository
public interface RoleModuleDao {

	RoleModuleDO get(Long id);
	
	List<RoleModuleDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(RoleModuleDO roleMenu);
	
	int update(RoleModuleDO roleMenu);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	List<Long> listMenuIdByRoleId(Long roleId);

	@Select("DELETE from sys_role_module WHERE role_id = #{roleId}")
	int removeByRoleId(Long roleId);
	
	int batchSave(List<RoleModuleDO> list);
}
