package com.fnsco.cms.service.impl;

import com.fnsco.cms.dao.ArticleMapper;
import com.fnsco.cms.model.Article;
import com.fnsco.cms.service.ArticleService;
import net.fnsco.core.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 分页查询
     *
     * @param pages
     * @return
     */
    @Override
    public List<Article> queryPageList(PageDTO<Article> pages) {
        return articleMapper.queryPageList(pages);

    }

    /**
     * 总数
     * @param record
     * @return
     */
    @Override
    public int queryTotalByCondition(Article record) {
        return articleMapper.queryTotalByCondition(record);
    }

    @Override
    public Integer addArticle(Article article) {
        return articleMapper.insertSelective(article);
    }

    //逻辑删除
    @Override
    public Integer removeArticle(Integer articleId) {
        return articleMapper.delete(articleId);
    }

    @Override
    public Integer changeArticle(Article article) {
        return articleMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    public Article getOneArticle(Integer articleId) {
        return articleMapper.selectByPrimaryKey(articleId);
    }


}
