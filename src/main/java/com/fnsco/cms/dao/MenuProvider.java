package com.fnsco.cms.dao;


import com.fnsco.cms.model.MenuDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MenuProvider {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String TABLE_NAME = "fns_cms_sys_menu";

    public String update(Map<String, Object> params) {
        MenuDO menu = (MenuDO) params.get("menu");
        return new SQL() {
            {
                UPDATE(TABLE_NAME);
                if (menu.getParentId() != null) {
                    SET("parent_id=#{menu.parentId}");
                }
                if (StringUtils.isNotBlank(menu.getName())) {
                    SET("name=#{menu.name}");
                }
                if (StringUtils.isNotBlank(menu.getUrl())) {
                    SET("url=#{menu.url}");
                }
                if (StringUtils.isNotBlank(menu.getPerms())) {
                    SET("perms=#{menu.perms}");
                }
                if (menu.getType() != null) {
                    SET("type=#{menu.type}");
                }
                if (StringUtils.isNotBlank(menu.getIcon())) {
                    SET("icon=#{menu.icon}");
                }
                if (menu.getOrderNum() != null) {
                    SET("order_num=#{menu.orderNum}");
                }
                WHERE("id = #{menu.id}");
            }
        }.toString();
    }

    public String pageList(Map<String, Object> params) {
        MenuDO menu = (MenuDO) params.get("menu");
        Integer pageNum = (Integer) params.get("pageNum");
        Integer pageSize = (Integer) params.get("pageSize");
        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 200;
        }
        int start = (pageNum - 1) * pageSize;
        int limit = pageSize;
        return new SQL() {
            {
                SELECT("*");
                FROM(TABLE_NAME);
                if (menu.getId() != null) {
                    WHERE("id=#{menu.id}");
                }
                if (menu.getParentId() != null) {
                    WHERE("parent_id=#{menu.parentId}");
                }
                if (StringUtils.isNotBlank(menu.getName())) {
                    WHERE("name=#{menu.name}");
                }
                if (StringUtils.isNotBlank(menu.getUrl())) {
                    WHERE("url=#{menu.url}");
                }
                if (StringUtils.isNotBlank(menu.getPerms())) {
                    WHERE("perms=#{menu.perms}");
                }
                if (menu.getType() != null) {
                    WHERE("type=#{menu.type}");
                }
                if (StringUtils.isNotBlank(menu.getIcon())) {
                    WHERE("icon=#{menu.icon}");
                }
                if (menu.getOrderNum() != null) {
                    WHERE("order_num=#{menu.orderNum}");
                }
                ORDER_BY("type,id,order_num asc limit " + start + ", " + limit);
            }
        }.toString();
    }

    public String pageListCount(Map<String, Object> params) {
        MenuDO menu = (MenuDO) params.get("menu");
        return new SQL() {
            {
                SELECT("count(1)");
                FROM(TABLE_NAME);
                if (menu.getId() != null) {
                    WHERE("id=#{menu.id}");
                }
                if (menu.getParentId() != null) {
                    WHERE("parent_id=#{menu.parentId}");
                }
                if (StringUtils.isNotBlank(menu.getName())) {
                    WHERE("name=#{menu.name}");
                }
                if (StringUtils.isNotBlank(menu.getUrl())) {
                    WHERE("url=#{menu.url}");
                }
                if (StringUtils.isNotBlank(menu.getPerms())) {
                    WHERE("perms=#{menu.perms}");
                }
                if (menu.getType() != null) {
                    WHERE("type=#{menu.type}");
                }
                if (StringUtils.isNotBlank(menu.getIcon())) {
                    WHERE("icon=#{menu.icon}");
                }
                if (menu.getOrderNum() != null) {
                    WHERE("order_num=#{menu.orderNum}");
                }
            }
        }.toString();
    }

    public String queryList(Map<String, Object> params) {
        MenuDO menu = (MenuDO) params.get("menu");

        return new SQL() {
            {
                SELECT(" m.*,(select p.name from fns_cms_sys_menu p where p.id = m.parent_id) as parentName");
                FROM(TABLE_NAME + " m ");
                if (menu.getId() != null) {
                    WHERE("id=#{menu.id}");
                }
                if (menu.getParentId() != null) {
                    WHERE("parent_id=#{menu.parentId}");
                }
                if (StringUtils.isNotBlank(menu.getName())) {
                    WHERE("name=#{menu.name}");
                }
                if (StringUtils.isNotBlank(menu.getUrl())) {
                    WHERE("url=#{menu.url}");
                }
                if (StringUtils.isNotBlank(menu.getPerms())) {
                    WHERE("perms=#{menu.perms}");
                }
                if (menu.getType() != null) {
                    WHERE("type=#{menu.type}");
                }
                if (StringUtils.isNotBlank(menu.getIcon())) {
                    WHERE("icon=#{menu.icon}");
                }
                if (menu.getOrderNum() != null) {
                    WHERE("order_num=#{menu.orderNum}");
                }
                ORDER_BY("order_num asc ");

            }
        }.toString();
    }
}
