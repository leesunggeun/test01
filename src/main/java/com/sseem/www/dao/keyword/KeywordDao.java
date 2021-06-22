package com.sseem.www.dao.keyword;

import com.sseem.www.domain.member.Keyword;
import com.sseem.www.domain.member.Style;
import com.sseem.www.domain.member.StyleParam;
import com.sseem.www.domain.common.PagingParam;

import java.util.List;

/**
 * Created by jaejin on 16. 3. 8..
 */
public interface KeywordDao {

    /*
    * login에서 사용
    * */
    List<Keyword> keywordList(Style style) throws Exception;
/*
    public int insert(Style style) throws Exception;
    public int modifyBanner(Style style) throws Exception;
    public Integer selectTotalCnt(Style style) throws Exception;
    public List<Keyword> selectBannerList(Style style, PagingParam pagingParam);
    public Keyword selectBanner(Style style);
*/
}