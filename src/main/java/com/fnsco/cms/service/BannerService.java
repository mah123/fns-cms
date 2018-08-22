package com.fnsco.cms.service;

import com.fnsco.cms.model.Banner;

import java.util.List;

public interface BannerService {

    Integer addBanner(Banner banner);

    Integer removeBanner(Integer bannerId);

    Integer changeBanner(Banner banner);

    Banner getOneBnner(Integer bannerId);

    List<Banner> getAllBanner();


}
