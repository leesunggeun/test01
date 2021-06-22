package com.sseem.www.dao.item;

import com.sseem.www.domain.common.CommentParam;
import com.sseem.www.domain.common.PagingParam;

import com.sseem.www.domain.item.*;

import java.util.List;

/**
 * Created by nobaksan on 2016. 2. 16..
 */
public interface ItemDao {
    List<Item> selectList(ItemParam itemParam, PagingParam pagingParam);
    int selectTotalCnt(ItemParam itemParam);

    List<Item> selectConsoleList(ItemParam itemParam, PagingParam pagingParam);
    int selectTotalConsoleCnt(ItemParam itemParam);


    List<Item> selectSiteList();

    List<Item> getCategory1();
    List<ImageFile> getCategory1Image();

    List<Item> getCategory2(ItemParam itemParam);
    List<ImageFile> getCategory2Image(ItemParam itemParam);

    List<Item> getCategory3(ItemParam itemParam);
    List<ImageFile> getCategory3Image(ItemParam itemParam);

    List<Item> getCategory4(ItemParam itemParam);
    List<ImageFile> getCategory4Image(ItemParam itemParam);

    List<Item> getCategory5(ItemParam itemParam);
    List<ImageFile> getCategory5Image(ItemParam itemParam);

    List<Item> getCategory6(ItemParam itemParam);
    List<ImageFile> getCategory6Image(ItemParam itemParam);


    Item selectItem(Integer itemNo);
    void updateItem(Item item);

    //Alert
    Item selectAlertItem(Integer itemNo, String alertDate);

    Item selectCategoryItem(ItemParam itemParam);

    List<ImageFile> getCategoryAllImage(ItemParam itemParam);

    List<Item> selectItemMenu(ItemParam itemParam);
    List<Item> selectItemBrandMenu(ItemParam itemParam);

    /*
    * front myPage
    * */
    List<Item> selectCategoryTitle(ItemParam itemParam);
    List<Item> selectCategoryBrandTitle(ItemParam itemParam);
    List<Item> selectAccessoriesCategoryList(ItemParam itemParam);
    List<Item> selectCategoryListWithoutAccessories(ItemParam itemParam);
    List<Item> selectAccessoriesCategoryBrandList(ItemParam itemParam);
    List<Item> selectCategoryBrandListWithoutAccessories(ItemParam itemParam);
    List<ItemParam> selectMyPageList(ItemParam itemParam, PagingParam pagingParam);
    Integer selectMyPageTotalCnt(ItemParam itemParam);

    /*
    * front category
    * */
    List<ItemParam> categoryLevel1(ItemParam itemParam);
    List<ItemParam> categoryLevel2(ItemParam itemParam);
    List<ItemParam> categoryLevel3(ItemParam itemParam);

    /*
    * front item use
    * */
    Item select(ItemParam itemParam);
    ItemParam itemDetail(ItemParam itemParam);
    List<ItemResult> selectSimilarList(Item itemParam);
    List<ImageFileParam> itemImageDetail(ItemParam itemParam);
    Integer itemLikesCnt(ItemParam itemParam);
    List<CommentParam> itemCommentDetail(ItemParam itemParam);
    Integer itemCommentCount(ItemParam itemParam);

}
