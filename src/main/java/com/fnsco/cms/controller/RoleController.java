package com.fnsco.cms.controller;

import com.fnsco.cms.entity.ResultDTO;
import com.fnsco.cms.entity.ResultPageDTO;
import com.fnsco.cms.model.Role;
import com.fnsco.cms.service.RoleService;
import com.fnsco.cms.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * @Author: hzh
 * @Date: 2018/8/14 0014 下午 4:37
 * @Description: 角色控制器
 */
@Controller
@RequestMapping("/web/auth/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;


    //分页查询
    @RequestMapping("list")
    @ResponseBody
    @RequiresPermissions(value = {"sys:role:list"})
    public ResultDTO pageList(Role role) {
        logger.info("开始分页查询RoleController.pageList, role=" + role.toString());
        Map<String, Integer> params = super.copyParamsToInteger(new String[]{"currentPageNum", "pageSize"});
        Integer page = params.get("currentPageNum");
        Integer rows = params.get("pageSize");

        ResultPageDTO<Role> pager = this.roleService.pageList(role, page, rows);
        return success(pager);
    }

    //新增角色
    @RequestMapping("add")
    @ResponseBody
    @RequiresPermissions(value = {"sys:role:add"})
    public ResultDTO doAdd(Role role) {
        System.out.println(role.getRoleId() + "!!!!!!!");
        logger.info("开始新增RoleController.doAdd, role=" + role.toString());

        return roleService.doAdd(role);
    }

    //角色修改
    @RequestMapping("update")
    @ResponseBody
    @RequiresPermissions(value = {"sys:role:update"})
    public ResultDTO doUpdate(Role role) {
        logger.info("开始修改RoleController.doUpdate, roleId=" + role.getRoleId());

        if (role == null) {
            return ResultDTO.fail();
        }

        return roleService.doUpdate(role);
    }

    //角色删除
    @RequestMapping("delete")
    @ResponseBody
    @RequiresPermissions(value = {"sys:role:delete"})
    public ResultDTO doDelete(Role role) {
        logger.info("开始新增RoleController.doDelete, roleId=" + role.getRoleId());

        return roleService.doDelete(role);
    }


    //查询单个角色信息
    @RequestMapping(value = "/queryRole", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"sys:role:list"})
    public ResultDTO<Role> queryRole() {
        List<Role> result = roleService.queryRole();

        Integer userID = this.getUserId();

        if (userID != 1) {
            List<Role> result2 = roleService.queryRoleByUserID(userID);
            return success(result2);
        } else {
            return success(result);
        }

    }
}
