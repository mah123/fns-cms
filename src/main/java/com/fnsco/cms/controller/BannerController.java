package com.fnsco.cms.controller;

import com.fnsco.cms.model.Banner;
import com.fnsco.cms.service.BannerService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: hzh
 * @Date: 2018/8/2 0002 上午 9:56
 * @Description: bannner控制器测试
 */
@Controller
@RequestMapping("/web/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(ModelAndView modelAndView, HttpServletRequest httpServletRequest) throws JSONException {
        List<Banner> allBanner = bannerService.getAllBanner();
        Map<String, Object> map = new HashMap<>();
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        //map.put("5",allBanner.get(0).getBannerId());
        modelAndView.addObject("map", map);
        modelAndView.addObject("bannerList", allBanner);
        modelAndView.addObject("message", "hhh");
        //  httpServletRequest.getSession().setAttribute("name","zzz");
        modelAndView.addObject("today", LocalDateTime.now());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", allBanner);
        modelAndView.addObject("json", jsonObject);

        modelAndView.setViewName("test");
        return modelAndView;
    }
}
