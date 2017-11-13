package com.youjiniot.sql;

import com.youjiniot.domain.Module;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by train on 17/2/16.
 */
public class ModuleSql {
    public String update(final Module module){
        return new SQL(){
            {
                UPDATE("sys_module");

                //通过条件 判断是否需要更新该字段
                if (StringUtils.isNotBlank(module.getName())) {
                    SET("name = #{name}");
                }

                if (StringUtils.isNotBlank(String.valueOf(module.getParentId()))) {
                    SET("parent_id = #{parentId}");
                }
                if (StringUtils.isNotBlank(String.valueOf(module.getOrderNum()))) {
                    SET("order_num = #{orderNum}");
                }

                if (StringUtils.isNotBlank(module.getPath())) {
                    SET("path = #{path}");
                }
                if (StringUtils.isNotBlank(module.getPerms())) {
                    SET("perms = #{perms}");
                }
                if (StringUtils.isNotBlank(module.getPath())) {
                    SET("path = #{path}");
                }
                if (StringUtils.isNotBlank(module.getModuleKey())) {
                    SET("module_key = #{moduleKey}");
                }

                SET("gmt_modified = #{gmtModified}");
                SET("type = #{type}");
                WHERE("id = #{id}");
            }

        }.toString();
    }
}
