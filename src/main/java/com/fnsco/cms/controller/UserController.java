package com.fnsco.cms.controller;

import com.fnsco.cms.constants.AuthConstant;
import com.fnsco.cms.entity.ResultDTO;
import com.fnsco.cms.entity.ResultPageDTO;
import com.fnsco.cms.entity.WebUserDTO;
import com.fnsco.cms.model.User;
import com.fnsco.cms.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @Author: hzh
 * @Date: 2018/8/9 0009 上午 11:22
 * @Description: 用户管理的controller
 */
@Controller
@RequestMapping(value = "/web/auth/user")
public class UserController extends BaseController {


    @Autowired
    private UserService userService;


    /**
     * 页面信息查询
     *
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = {"sys:user:list"})
    public ResultDTO<User> query(User user,
                                 @RequestParam("currentPageNum") Integer pageNum,
                                 @RequestParam("pageSize") Integer pageSize) {
        Integer userID = this.getUserId();
        User userDO = userService.getUserById(userID);
        /*if(userDO != null){
            user.setAgentId(userDO.getAgentId());
        }*/
        ResultPageDTO<User> result = userService.queryList(user, pageNum, pageSize);
        return success(result);
    }

    /**
     * 通过id查询删除对象（状态改0）
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/deleteUserById", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"sys:user:delete"})
    public ResultDTO<String> deleteUserById(@RequestParam(value = "id[]") Integer[] id) {
        //获取当前登录的用户
        WebUserDTO adminUser = (WebUserDTO) getSessionUser(request);
        Integer userId = adminUser.getId();
        for (int i = 0; i < id.length; i++) {
            if (id[i] == AuthConstant.SUPER_ADMIN) {
                return ResultDTO.fail(AuthConstant.E_NOT_DELEET_ADMIN);
            }
            if (userId == id[i]) {
                return ResultDTO.fail(AuthConstant.E_NOT_DELEET_ONESELF);
            }
        }
        ResultDTO<String> result = userService.deleteById(id);
        return success(result);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"sys:user:save"})
    public ResultDTO<String> toAdd(User user) {
        userService.doAddUser(user);
        return success(null);
    }

    /**
     * 通过id查询修改对象的数据
     *
     * @param
     * @return
     */
    @RequiresPermissions(value = {"sys:user:list"})
    @RequestMapping(value = "/queryUserById", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO<User> queryUserById(@RequestParam("userId") Integer userId) {
        User result = userService.queryUserById(userId);
        return success(result);
    }

    /**
     * 通过用户名查询是否重复
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/queryUserByName", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"sys:user:list"})
    public boolean queryUserByName(String name) {
        boolean result = userService.queryUserByName(name);
        return result;
    }

    /**
     * 用户信息修改
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/toEdit", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"sys:user:update"})
    public ResultDTO<String> toEdit(User user) {
        System.out.println(user.toString() + "!!!!!!!!!");
        //获取当前登录的用户
        WebUserDTO adminUser = (WebUserDTO) getSessionUser(request);
        Integer userId = adminUser.getId();
        //user.setModifyUserId(userId);
        Integer id = user.getUserId();
        Integer status = user.getUserState();
        //用户状态0删除1正常2禁止
        if (status == 2) {
            if (id == AuthConstant.SUPER_ADMIN) {
                return ResultDTO.fail(AuthConstant.E_NOT_DELEET_ADMIN);
            }
            if (userId == id) {
                return ResultDTO.fail(AuthConstant.E_NOT_DELEET_ONESELF);
            }
        }
        ResultDTO<String> result = userService.toEditDept(user);
        return result;
    }


}
