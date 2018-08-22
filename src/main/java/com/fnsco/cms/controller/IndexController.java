package com.fnsco.cms.controller;

import org.springframework.stereotype.Controller;


@Controller
public class IndexController extends BaseController {

    /**
     * 进入首页
     * @param rep
     * @param res
     * @return
    /* */
    // @RequestMapping(value = "/index", method = RequestMethod.GET)
  /*  public String index(HttpServletRequest rep, HttpServletResponse res) {

        Object obj = getSessionUser(rep);
        if (null == obj) {
            return "forward:/idx";
        }
        return "redirect:/index.html";
    }*/
}
