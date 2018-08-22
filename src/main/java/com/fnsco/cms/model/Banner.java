package com.fnsco.cms.model;

import java.io.Serializable;
import java.util.Date;

public class Banner implements Serializable {
    private Integer bannerId;

    private String bannerUrl;

    private Double bannerSize;

    private String bannerDesc;

    private Date bannerUpTime;

    private String bannerUpUser;

    private Date bannerChangeTime;

    private String bannerChangeUser;

    private Date bannerDelTime;

    private Integer bannerState;

    public Integer getBannerId() {
        return bannerId;
    }

    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public Double getBannerSize() {
        return bannerSize;
    }

    public void setBannerSize(Double bannerSize) {
        this.bannerSize = bannerSize;
    }

    public String getBannerDesc() {
        return bannerDesc;
    }

    public void setBannerDesc(String bannerDesc) {
        this.bannerDesc = bannerDesc;
    }

    public Date getBannerUpTime() {
        return bannerUpTime;
    }

    public void setBannerUpTime(Date bannerUpTime) {
        this.bannerUpTime = bannerUpTime;
    }

    public String getBannerUpUser() {
        return bannerUpUser;
    }

    public void setBannerUpUser(String bannerUpUser) {
        this.bannerUpUser = bannerUpUser;
    }

    public Date getBannerChangeTime() {
        return bannerChangeTime;
    }

    public void setBannerChangeTime(Date bannerChangeTime) {
        this.bannerChangeTime = bannerChangeTime;
    }

    public String getBannerChangeUser() {
        return bannerChangeUser;
    }

    public void setBannerChangeUser(String bannerChangeUser) {
        this.bannerChangeUser = bannerChangeUser;
    }

    public Date getBannerDelTime() {
        return bannerDelTime;
    }

    public void setBannerDelTime(Date bannerDelTime) {
        this.bannerDelTime = bannerDelTime;
    }

    public Integer getBannerState() {
        return bannerState;
    }

    public void setBannerState(Integer bannerState) {
        this.bannerState = bannerState;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "bannerId=" + bannerId +
                ", bannerUrl='" + bannerUrl + '\'' +
                ", bannerSize=" + bannerSize +
                ", bannerDesc='" + bannerDesc + '\'' +
                ", bannerUpTime=" + bannerUpTime +
                ", bannerUpUser='" + bannerUpUser + '\'' +
                ", bannerChangeTime=" + bannerChangeTime +
                ", bannerChangeUser='" + bannerChangeUser + '\'' +
                ", bannerDelTime=" + bannerDelTime +
                ", bannerState=" + bannerState +
                '}';
    }
}