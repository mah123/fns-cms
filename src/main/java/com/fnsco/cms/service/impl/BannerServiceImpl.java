package com.fnsco.cms.service.impl;

import com.fnsco.cms.dao.BannerMapper;
import com.fnsco.cms.model.Banner;
import com.fnsco.cms.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerMapper bannerMapper;

    @Override
    public Integer addBanner(Banner banner) {
        return bannerMapper.insertSelective(banner);
    }

    @Override
    public Integer removeBanner(Integer bannerId) {
        return bannerMapper.deleteBanner(bannerId);
    }

    @Override
    public Integer changeBanner(Banner banner) {
        return bannerMapper.updateByPrimaryKeySelective(banner);
    }

    @Override
    public Banner getOneBnner(Integer bannerId) {
        return bannerMapper.selectByPrimaryKey(bannerId);
    }

    @Override
    public List<Banner> getAllBanner() {
        return bannerMapper.selectAll();
    }
}
