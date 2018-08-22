package com.fnsco.cms.service;

import com.fnsco.cms.model.Article;
import net.fnsco.core.base.PageDTO;

import java.util.List;

public interface ArticleService {

    List<Article> queryPageList(PageDTO<Article> pages);

    int queryTotalByCondition(Article record);

    Integer addArticle(Article article);

    Integer removeArticle(Integer articleId);

    Integer changeArticle(Article article);

    Article getOneArticle(Integer articleId);


}
