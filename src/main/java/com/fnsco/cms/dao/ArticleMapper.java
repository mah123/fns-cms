package com.fnsco.cms.dao;

import com.fnsco.cms.model.Article;
import net.fnsco.core.base.PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface ArticleMapper {
    /**
     * 分页条件查询
     * @param pages
     * @return
     */
    List<Article> queryPageList(PageDTO<Article> pages);

    /**
     * 分页---总数
     * @param article
     * @return
     */
    int queryTotalByCondition(@Param("article") Article article);

    int deleteByPrimaryKey(Integer articleId);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    //逻辑删除,修改记录状态
    int delete(Integer articleId);

}