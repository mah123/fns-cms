package com.fnsco.cms.model;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private Integer articleId;

    private String articleTitle;

    private String articleContent;

    private String articleDesc;

    private String articleFrom;

    private String articleReadTimes;

    private Integer menuID;

    private String articleAuthor;

    private Date articlePubTime;

    private String articlePubUser;

    private Date articleUpdateTime;

    private String articleUpdateUser;

    private Date articleDelTime;

    private Integer articleState;


    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getArticleFrom() {
        return articleFrom;
    }

    public void setArticleFrom(String articleFrom) {
        this.articleFrom = articleFrom;
    }

    public String getArticleReadTimes() {
        return articleReadTimes;
    }

    public void setArticleReadTimes(String articleReadTimes) {
        this.articleReadTimes = articleReadTimes;
    }

    public Integer getMenuID() {
        return menuID;
    }

    public void setMenuID(Integer menuID) {
        this.menuID = menuID;
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public Date getArticlePubTime() {
        return articlePubTime;
    }

    public void setArticlePubTime(Date articlePubTime) {
        this.articlePubTime = articlePubTime;
    }

    public String getArticlePubUser() {
        return articlePubUser;
    }

    public void setArticlePubUser(String articlePubUser) {
        this.articlePubUser = articlePubUser;
    }

    public Date getArticleUpdateTime() {
        return articleUpdateTime;
    }

    public void setArticleUpdateTime(Date articleUpdateTime) {
        this.articleUpdateTime = articleUpdateTime;
    }

    public String getArticleUpdateUser() {
        return articleUpdateUser;
    }

    public void setArticleUpdateUser(String articleUpdateUser) {
        this.articleUpdateUser = articleUpdateUser;
    }

    public Date getArticleDelTime() {
        return articleDelTime;
    }

    public void setArticleDelTime(Date articleDelTime) {
        this.articleDelTime = articleDelTime;
    }

    public Integer getArticleState() {
        return articleState;
    }

    public void setArticleState(Integer articleState) {
        this.articleState = articleState;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleContent='" + articleContent + '\'' +
                ", articleDesc='" + articleDesc + '\'' +
                ", articleFrom='" + articleFrom + '\'' +
                ", articleReadTimes='" + articleReadTimes + '\'' +
                ", menuID=" + menuID +
                ", articleAuthor='" + articleAuthor + '\'' +
                ", articlePubTime=" + articlePubTime +
                ", articlePubUser='" + articlePubUser + '\'' +
                ", articleUpdateTime=" + articleUpdateTime +
                ", articleUpdateUser='" + articleUpdateUser + '\'' +
                ", articleDelTime=" + articleDelTime +
                ", articleState=" + articleState +
                '}';
    }
}