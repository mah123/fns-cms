package com.fnsco.cms.dao;


import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UserProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // private static final String TABLE_NAME = "fns_cms_user";


    public String queryAllPerms(Map<String, Object> params) {
        Integer userId = (Integer) params.get("userId");
        return new SQL() {
            {
                SELECT(" m.perms from fns_cms_user_role ur LEFT JOIN fns_cms_role_menu rm on ur.role_id = rm.role_id LEFT JOIN fns_cms_sys_menu m on rm.menu_id = m.id ");
                WHERE("ur.user_id = #{userId}");
            }
        }.toString();
    }

}
