package com.fnsco.cms.controller;

import com.fnsco.cms.entity.ResultDTO;
import com.fnsco.cms.entity.ResultPageDTO;
import com.fnsco.cms.model.Article;
import com.fnsco.cms.service.ArticleService;
import net.fnsco.core.base.PageDTO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;


/**
 * @Author: 小白龙
 * @Date: 2018/8/2 0002 下午 5:05
 * @Description: articleController基础控制器
 */
@Controller
@RequestMapping("/web/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 内容页面分页查询
     * @param record
     * @param currentPageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = {"sys:article:list"})
    public ResultPageDTO<Article> query(Article record, Integer currentPageNum, Integer pageSize) {
        record.setArticleState(1);
        PageDTO<Article> pages = new PageDTO<Article>(currentPageNum, pageSize, record);
        List<Article> datas = articleService.queryPageList(pages);
        int total = articleService.queryTotalByCondition(record);
        ResultPageDTO<Article> result = new ResultPageDTO<Article>(total, datas);
        return result;
    }

    @RequestMapping(value = "/queryById", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"sys:article:list"})
    public ResultDTO<Article> queryById(@RequestBody Article record) {
        Article article = articleService.getOneArticle(record.getArticleId());
        return ResultDTO.success(article);
    }

    /**
     * 新增内容
     * @param record
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"sys:article:save"})
    public ResultDTO add(@RequestBody Article record) {
        record.setArticleState(1);
        record.setArticlePubTime(new Date());
        articleService.addArticle(record);
        return ResultDTO.success();
    }

    /**
     * 修改内容
     * @param record
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"sys:article:update"})
    public ResultDTO modify(@RequestBody Article record) {
        record.setArticleUpdateTime(new Date());
        articleService.changeArticle(record);
        return ResultDTO.success();
    }

    /**
     * 删除内容
     * @param record
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"sys:article:delete"})
    public ResultDTO delete(@RequestBody Article record) {
        articleService.removeArticle(record.getArticleId());
        return ResultDTO.success();
    }

}
