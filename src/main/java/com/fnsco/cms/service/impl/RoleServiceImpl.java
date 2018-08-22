package com.fnsco.cms.service.impl;

import com.fnsco.cms.dao.RoleMapper;
import com.fnsco.cms.entity.ResultDTO;
import com.fnsco.cms.entity.ResultPageDTO;
import com.fnsco.cms.model.Role;
import com.fnsco.cms.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuService roleMenuService;


    @Override
    public List<Role> queryRole() {
        //返回根据条件查询的所有记录条数
        List<Role> data = roleMapper.selectAll();
        return data;
    }


    @Override
    public List<Role> queryRoleByUserID(Integer userID) {
        //根据用户id查询所有角色
        List<Role> data = roleMapper.queryRoleByUserID(userID);
        return data;
    }


    @Override
    public ResultPageDTO<Role> pageList(Role role, Integer page, Integer rows) {
        logger.info("开始分页查询RoleService.page, role=" + role.toString() + ",page=" + page + ",rows=" + rows);
        if (page == null || page == 0) {
            page = 1;
        }
        if (rows == null || rows == 0) {
            rows = 20;
        }
        int start = (page - 1) * rows;
        int limit = rows;
        List<Role> pageList = roleMapper.pageList(role, start, limit);

        for (Role roleDO : pageList) {
            //给部门名称赋值
            // Integer deptId = roleDO.getDeptId();
//            if (null != deptId) {
//                DeptDO deptDO = deptService.getById(deptId);
//                if (deptDO == null) {
//                    roleDO.setDeptName("--");
//                } else {
//                    roleDO.setDeptName(deptDO.getName());
//                }
//            }

            //时间格式化yyyy-MM-dd HH:mm:ss
            //String createTimeStr = DateUtils.dateFormatToStr();
            // roleDO.setCreateTimeStr(createTimeStr);roleDO.getRoleCreateTime();

            //将数据权限放入对象
            // List<Integer> deptIdList = roleDeptService.queryByRoleId(roleDO.getRoleId());
            //  roleDO.setDeptIdList(deptIdList);

            //将菜单列表放入对象
            List<Integer> menuList = roleMenuService.queryByRoleId(roleDO.getRoleId());
            roleDO.setMenuIdList(menuList);
        }

        Integer count = this.roleMapper.pageListCount(role);
        ResultPageDTO<Role> pager = new ResultPageDTO<Role>(count, pageList);
        return pager;
    }

    @Override
    public ResultDTO doAdd(Role role) {

        // 保存数据到角色表
        role.setRoleCreateTime(new Date());
        this.roleMapper.insertSelective(role);

        // 保存(角色-菜单)数据到角色菜单表
        Integer maxId = roleMapper.selectMaxId();
        this.roleMenuService.saveOrUpdate(maxId, role.getMenuIdList());
        return ResultDTO.success();
    }


    @Override
    public ResultDTO doUpdate(Role role) {
        if (role.getRoleId() == null) {
            return ResultDTO.fail();
        }
        this.roleMapper.updateByPrimaryKeySelective(role);

        // 保存(角色-菜单)数据到角色菜单表
        this.roleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

        return ResultDTO.success();
    }

    @Override
    public ResultDTO doDelete(Role role) {
        Role rol = new Role();

        Integer roleId = role.getRoleId();
        if (null != roleId) {
            rol.setRoleId(roleId);

            // 删除角色表数据(根据ID删除)
            this.roleMapper.deleteByPrimaryKey(role.getRoleId());

            //删除角色菜单表对应角色数据
            this.roleMenuService.delete(roleId);

            return ResultDTO.success();
        }
        //否则返回失败
        return ResultDTO.fail();
    }


}
