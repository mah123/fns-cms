package com.fnsco.cms.service.impl;


import com.fnsco.cms.constants.AuthConstant;
import com.fnsco.cms.dao.MenuMapper;
import com.fnsco.cms.entity.ResultDTO;
import com.fnsco.cms.entity.ResultPageDTO;
import com.fnsco.cms.model.MenuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService extends BaseService {


    @Autowired
    private MenuMapper menuDAO;

    @Autowired
    private RoleMenuService roleMenuService;

    public ResultPageDTO<MenuDO> pageList(MenuDO menu, Integer page, Integer rows) {
        logger.info("开始分页查询MenuService.page, role=" + menu.toString());
        List<MenuDO> pageList = menuDAO.pageList(menu, page, rows);
        for (MenuDO menuDO : pageList) {
            // 设置父菜单名称
            MenuDO m = menuDAO.getById(menuDO.getParentId());
            if (m == null) {
                //外层目录没有子菜单,修改此处便于与点击上级菜单弹出的菜单界面相同
                if (menuDO.getParentId() == 0) {
                    menuDO.setParentName("总菜单");
                } else {
                    menuDO.setParentName("--");
                }
            } else {
                menuDO.setParentName(m.getName());
            }
        }
        Integer count = this.menuDAO.pageListCount(menu);
        ResultPageDTO<MenuDO> pager = new ResultPageDTO<MenuDO>(count, pageList);
        return pager;
    }

    public ResultDTO<MenuDO> queryNotButtonList() {

        List<MenuDO> menuList = menuDAO.queryNotButtonList();

        // 添加顶级菜单
        MenuDO root = new MenuDO();
        root.setId(0);
        root.setName("总菜单");
        root.setParentId(-1);
        root.setOpen(true);
        menuList.add(root);
        root.setType(0);

        // 将查询到的menuList数据返回
        return ResultDTO.success(menuList);
    }

    @Transactional
    public MenuDO doAdd(MenuDO menu) {

        this.menuDAO.insert(menu);
        return menu;
    }

    @Transactional
    public MenuDO doUpdate(MenuDO menu) {

        // 通过传入菜单ID查找该菜单信息
        MenuDO menuDO = this.menuDAO.getById(menu.getId());

        this.menuDAO.update(menu);

        return menu;
    }

    /**
     * @param menu
     * @return
     */
    @Transactional
    public ResultDTO doDelete(MenuDO menu) {

        // 删除目录，需要查询是否含有菜单以及子目录，有则删除失败；删除菜单，需要同时删除其按钮；删除按钮，直接删除
        // 如果类型为目录类型，查询是否有菜单或目录，如果有菜单，则删除失败(返回失败，提示信息:请先删除其子菜单)
        if (menu.getType() == 0) {
            MenuDO menuDO = new MenuDO();

            // 通过父菜单ID(为当前目录ID)和类型为1菜单来查找子菜单
            menuDO.setParentId(menu.getId());
            menuDO.setType(1);

            // 查询是否有子菜单
            Integer count = this.menuDAO.pageListCount(menuDO);

            // 如果有子菜单
            if (count > 0) {
                logger.info("删除MenuService.doDelete, 当前有子菜单，不能删除");

                // 表示当前目录不能删除，需返回失败(返回失败，提示信息:请先删除其子菜单)
                return ResultDTO.fail(AuthConstant.E_MENU_EXIST);
            }
            // 否则，查询是否含有子目录
            menuDO.setType(0);

            // 查询是否有子目录
            count = this.menuDAO.pageListCount(menuDO);
            // 如果有子目录
            if (count > 0) {
                logger.info("删除MenuService.doDelete, 当前有子目录，不能删除");

                // 表示当前目录不能删除，需返回失败(返回失败，提示信息:请先删除其子目录)
                return ResultDTO.fail(AuthConstant.E_MENU_EXIST);
            }
            // 否则，没有子菜单，则可以直接删除
            // 如果是菜单，则需要将菜单和其相关按钮都删掉
        } else if (menu.getType() == 1) {

            // 通过父菜单ID(为当前目录ID)来删除菜单按钮
            this.menuDAO.deleteByParentId(menu.getId());
        }

        // 删除菜单
        this.menuDAO.deleteById(menu.getId());

        // 删除角色菜单表里面角色对应的菜单，不删除的话，容易造成角色对应空菜单
        this.roleMenuService.deleteByMenuId(menu.getId());

        return ResultDTO.success();
    }


    // 通过用户ID查询用户角色，通过用户角色对应菜单ID，查询对应菜单并返回

    /**
     * 1、用户ID找到角色ID(一对多)， 2、角色ID找到菜单ID(多对多)， 3、菜单ID找到菜单Obj(多对多)
     *
     * @param userId：用户ID
     * @return
     */
    public ResultDTO userMenuist(Integer userId) {

        // admin 用户，返回所有页面
        if (userId == AuthConstant.SUPER_ADMIN) {
            // 查询所有的非按钮List
            List<MenuDO> menuList = menuDAO.queryNotButtonList();
            // 返回成功
            return ResultDTO.success(menuList);
        }

        // 获取用户ID下所有菜单ID(优化DAO里面的语句，改为left join左连接)
        List<Integer> menuIdList = menuDAO.queryAllMenuId(userId);
        if (menuIdList.size() == 0) {
            return ResultDTO.fail();
        }

        // 通过菜单ID的list查询菜单Obj列表(in查询)
        List<MenuDO> menuList = menuDAO.queryAllMenuById(menuIdList);
        System.out.println("menuList.size():" + menuList.size());
        if (menuList.size() == 0) {
            logger.info("通过菜单ID查询菜单Obj为空");
            return ResultDTO.success();
        }

        return ResultDTO.success(menuList);
    }


}
