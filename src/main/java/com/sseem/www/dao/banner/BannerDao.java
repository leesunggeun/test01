package com.sseem.www.dao.banner;

import com.sseem.www.domain.banner.Banner;
//import com.sseem.www.domain.item.*;
import com.sseem.www.domain.banner.BannerParam;

import com.sseem.www.domain.common.PagingParam;
import com.sseem.www.domain.member.User;
import com.sseem.www.domain.member.UserParam;

import java.util.List;

import java.util.List;

/**
 * Created by jaejin on 16. 2. 12..
 */
public interface BannerDao {
    public int insert(Banner banner) throws Exception;
    public int modifyBanner(Banner banner) throws Exception;
    public Integer selectTotalCnt(Banner banner) throws Exception;
    public List<Banner> selectBannerList(Banner banner, PagingParam pagingParam);
    public Banner selectBanner(Banner banner);
    public Banner select(Banner banner) throws Exception;
    List<Banner> selectAllList();

}