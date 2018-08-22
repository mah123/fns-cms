package com.fnsco.cms.controller;

import com.fnsco.cms.constants.AuthConstant;
import com.fnsco.cms.constants.WebConstants;
import com.fnsco.cms.entity.ResultDTO;
import com.fnsco.cms.entity.WebUserDTO;
import com.fnsco.cms.model.User;
import com.fnsco.cms.service.UserService;
import com.fnsco.cms.service.impl.UserTokenService;
import com.fnsco.cms.util.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author: hzh
 * @Date: 2018/8/1 0001 上午 9:48
 * @Description: 基础的版本controller，以后功能待修改
 */
@Controller
@RequestMapping("/web")
public class LoginController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private UserService userService;

    @Autowired
    private UserTokenService userTokenService;

    /**
     * 访问入口
     *
     * @param
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "login.html";
    }

    /**
     * 登录方法
     *
     * @return
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO doLogin() {
        //账号和密码
        String username = request.getParameter("userAccount");
        String password = request.getParameter("userPassword");
        if (org.springframework.util.StringUtils.isEmpty(password) || org.springframework.util.StringUtils.isEmpty(username)) {

            return ResultDTO.fail(AuthConstant.WEB_LOGIN_NULL);
        }

        ResultDTO result = userService.doLogin(username, password);
        if (!result.isSuccess()) {
            return ResultDTO.fail(AuthConstant.WEB_LOGIN_FAIL);
        }
        User user = (User) result.getData();
        System.out.println(user.toString() + "!!!!!!!!!!!!");
        WebUserDTO webUser = new WebUserDTO();
        webUser.setId(user.getUserId());
        webUser.setName(user.getUserAccount());
        webUser.setType(user.getUserIsAdmin());
        //webUser.setAgentId(user.getAgentId());

        CookieUtils.addCookie(response, WebConstants.COOKIE_USER_KEY, user.getUserAccount());
        String tokenId = userTokenService.createToken(user.getUserId());

        webUser.setTokenId(tokenId);
        setSessionUser(webUser, webUser.getId());
        return ResultDTO.success();
    }

    /**
     * 登录方法
     *
     * @return
     */
    @RequestMapping(value = "/goLogin", method = RequestMethod.GET)
    public String goLogin() {
        Object cookeiUser = getCookieUser();
        if (null == cookeiUser) {
            return "redirect:/login.html";
        }

        String userName = cookeiUser.toString().substring(cookeiUser.toString().lastIndexOf("#") + 1, cookeiUser.toString().length());
        User sysUser = userService.getUserByAccount(userName);
        if (null == sysUser) {
            return "redirect:/login.html";
        }
        WebUserDTO user = new WebUserDTO();
        System.out.println(user.toString() + "@@@@@@@@@@@@@@@@@@@@@@");
        user.setId(sysUser.getUserId());
        user.setName(sysUser.getUserAccount());
        String tokenId = userTokenService.createToken(user.getId());
        user.setTokenId(tokenId);
        setSessionUser(user, user.getId());
        return "redirect:/index.html";
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        removeSessionUser();
        removeCookieUser();
        return "redirect:/login.html";
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCurrentUser() {
        Map<String, Object> result = new HashMap<>();
        WebUserDTO adminUser = (WebUserDTO) getSessionUser(request);
        result.put("sessionUser", adminUser);
        return result;
    }

}
