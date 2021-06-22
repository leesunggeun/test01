package com.sseem.www.dao.item;

import com.google.common.base.Strings;
import com.sseem.www.domain.common.CommentParam;
import com.sseem.www.domain.common.PagingParam;
import com.sseem.www.domain.item.*;
import org.hibernate.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Subselect;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.FetchType;
import java.util.List;

/**
 * Created by nobaksan on 2016. 2. 16..
 */
@Repository
public class ItemDaoImpl implements ItemDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Item> selectList(ItemParam itemParam, PagingParam pagingParam) {
       Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
               .setFetchMode("it.imageFile", FetchMode.JOIN)
               .createAlias("it.imageFile", "if", JoinType.INNER_JOIN)
               .setFetchMode("it.brand", FetchMode.JOIN)
               .createAlias("it.brand", "br", JoinType.INNER_JOIN)
               .add(Restrictions.eq("if.isDel","N"))
               .add(Restrictions.eq("if.represent","Y"))
               .add(Restrictions.eq("br.isDel","N"))
//               .setMaxResults(pagingParam.getCountPerPage())
               .setMaxResults(12)
               .setFirstResult((pagingParam.getNowPage() - 1) * 12)
               .setProjection(Projections.projectionList()
                       .add(Projections.property("it.itemNo"), "itemNo")
                       .add(Projections.property("itemName"), "itemName")
                       .add(Projections.property("price"), "price")
                       .add(Projections.property("saleprice"), "saleprice")
                       .add(Projections.property("discount"), "discount")
                       .add(Projections.property("description"), "description")
                       .add(Projections.property("siteName"), "siteName")
                       .add(Projections.property("ranking"), "ranking")
                       .add(Projections.property("etc"), "etc")
                       .add(Projections.property("cat1"), "cat1")
                       .add(Projections.property("cat2"), "cat2")
                       .add(Projections.property("cat3"), "cat3")
                       .add(Projections.property("cat4"), "cat4")
                       .add(Projections.property("cat5"), "cat5")
                       .add(Projections.property("cat6"), "cat6")
                       .add(Projections.property("cat7"), "cat7")
                       .add(Projections.property("it.modifyDate"), "modifyDate")
                       .add(Projections.property("if.originName"), "originName")
                       .add(Projections.property("if.originPath"), "originPath")
                       .add(Projections.property("br.brandName"), "brandName")
                       .add(Projections.property("br.promote"), "promote")
                       .add(Projections.property("link"), "link")
               ).setResultTransformer(Transformers.aliasToBean(ItemResult.class))
               .addOrder(Order.desc("it.itemNo"))
               .addOrder(Order.asc("it.orderly"));

        if(StringUtils.isEmpty(itemParam.getSiteName())==false){
            criteria.add(Restrictions.in("siteName",itemParam.getSiteName().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getDiscount())==false){
            criteria.add(Restrictions.in("discount",itemParam.getDiscount().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getModName())==false){
            String[] splModName = itemParam.getModName().split(",");
            if(splModName.length == 1){
                if("Y".equals(splModName[0])) {
                    criteria.add(Restrictions.eq("orderly",1));
                } else if("N".equals(splModName[0])){
                    criteria.add(Restrictions.eq("orderly",0));
                }
            }
        }

        if(StringUtils.isEmpty(itemParam.getBrandNo())==false) {
            criteria.add(Restrictions.eq("brandNo", itemParam.getBrandNo()));
            criteria.add(Restrictions.isNotNull("brandNo"));
        }

        if(StringUtils.isEmpty(itemParam.getBrandName())==false){
            criteria.add(Restrictions.in("br.brandName",itemParam.getBrandName().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getPrice())==false){
            String[] splPrice = itemParam.getPrice().split(",");
            Disjunction disjunction = Restrictions.disjunction();
            for(String price : splPrice){
                if("0".equals(price)){
                    disjunction.add(Restrictions.between("price",0,30000));
                }else if("3".equals(price)){
                    disjunction.add(Restrictions.between("price",30000,50000));
                }else if("5".equals(price)){
                    disjunction.add(Restrictions.between("price",50000,100000));
                }else if("10".equals(price)){
                    disjunction.add(Restrictions.between("price",100000,200000));
                }else if("20".equals(price)){
                    disjunction.add(Restrictions.between("price",200000,300000));
                }else if("30".equals(price)){
                    disjunction.add(Restrictions.between("price",300000,500000));
                }else if("50".equals(price)){
                    disjunction.add(Restrictions.between("price",500000,800000));
                }else if("80".equals(price)){
                    disjunction.add(Restrictions.between("price",800000,1000000));
                }else if("100".equals(price)){
                    disjunction.add(Restrictions.between("price",1000000,1000000000));
                }
            }
            criteria.add(disjunction);
        }

        // 가격범위
        if(itemParam.getMinPrice()!=null && itemParam.getMaxPrice()!=null){
            criteria.add(Restrictions.between("price", itemParam.getMinPrice(), itemParam.getMaxPrice()));
        }

        if(StringUtils.isEmpty(itemParam.getCat1())==false){
            criteria.add(Restrictions.in("cat1",itemParam.getCat1().split(",")));
        }


        if(StringUtils.isEmpty(itemParam.getCat2())==false){
            criteria.add(Restrictions.in("cat2",itemParam.getCat2().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat3())==false){
            criteria.add(Restrictions.in("cat3",itemParam.getCat3().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat4())==false){
            criteria.add(Restrictions.in("cat4",itemParam.getCat4().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat5())==false){
            criteria.add(Restrictions.in("cat5",itemParam.getCat5().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat6())==false){
            criteria.add(Restrictions.in("cat6",itemParam.getCat6().split(",")));
        }
        if(StringUtils.isEmpty(itemParam.getCat7())==false){
            criteria.add(Restrictions.in("cat7",itemParam.getCat7().split(",")));
        }


        if(StringUtils.isEmpty(itemParam.getSearchWord())==false){
            criteria.add(
                    Restrictions.or(
                            Restrictions.like("itemName",itemParam.getSearchWord(),MatchMode.ANYWHERE),
                            Restrictions.like("description",itemParam.getSearchWord(),MatchMode.ANYWHERE)
                    )
            );
        }
        return criteria.list();
    }

    public List<Item> selectConsoleList(ItemParam itemParam, PagingParam pagingParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", JoinType.INNER_JOIN)
                .setFetchMode("it.brand", FetchMode.JOIN)
                .createAlias("it.brand", "br", JoinType.INNER_JOIN)
                .add(Restrictions.eq("if.isDel","N"))
                .add(Restrictions.eq("if.represent","Y"))
                .add(Restrictions.eq("br.isDel","N"))
                .setMaxResults(pagingParam.getCountPerPage())
                .setFirstResult((pagingParam.getNowPage() - 1) * pagingParam.getCountPerPage())
                .setProjection(Projections.projectionList()
                        .add(Projections.property("it.itemNo"), "itemNo")
                        .add(Projections.property("itemName"), "itemName")
                        .add(Projections.property("price"), "price")
                        .add(Projections.property("saleprice"), "saleprice")
                        .add(Projections.property("discount"), "discount")
                        .add(Projections.property("description"), "description")
                        .add(Projections.property("siteName"), "siteName")
                        .add(Projections.property("ranking"), "ranking")
                        .add(Projections.property("etc"), "etc")
                        .add(Projections.property("cat1"), "cat1")
                        .add(Projections.property("cat2"), "cat2")
                        .add(Projections.property("cat3"), "cat3")
                        .add(Projections.property("cat4"), "cat4")
                        .add(Projections.property("cat5"), "cat5")
                        .add(Projections.property("cat6"), "cat6")
                        .add(Projections.property("cat7"), "cat7")
                        .add(Projections.property("it.modifyDate"), "modifyDate")
                        .add(Projections.property("if.originName"), "originName")
                        .add(Projections.property("if.originPath"), "originPath")
                        .add(Projections.property("br.brandName"), "brandName")
                        .add(Projections.property("br.promote"), "promote")
                        .add(Projections.property("link"), "link")
                ).setResultTransformer(Transformers.aliasToBean(ItemResult.class))
                .addOrder(Order.desc("it.itemNo"))
                .addOrder(Order.asc("it.orderly"));

        if(StringUtils.isEmpty(itemParam.getSiteName())==false){
            criteria.add(Restrictions.in("siteName",itemParam.getSiteName().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getDiscount())==false){
            criteria.add(Restrictions.in("discount",itemParam.getDiscount().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getModName())==false){
            String[] splModName = itemParam.getModName().split(",");
            if(splModName.length == 1){
                if("Y".equals(splModName[0])) {
                    criteria.add(Restrictions.eq("orderly",1));
                }else if("N".equals(splModName[0])){
                    criteria.add(Restrictions.eq("orderly",0));
                }
            }
        }

        if(StringUtils.isEmpty(itemParam.getBrandNo())==false){
            criteria.add(Restrictions.eq("brandNo",itemParam.getBrandNo()));
        }
        if(StringUtils.isEmpty(itemParam.getBrandName())==false){
            criteria.add(Restrictions.in("br.brandName",itemParam.getBrandName().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getPrice())==false){
            String[] splPrice = itemParam.getPrice().split(",");
            Disjunction disjunction = Restrictions.disjunction();
            for(String price : splPrice){
                if("0".equals(price)){
                    disjunction.add(Restrictions.between("price",0,30000));
                }else if("3".equals(price)){
                    disjunction.add(Restrictions.between("price",30000,50000));
                }else if("5".equals(price)){
                    disjunction.add(Restrictions.between("price",50000,100000));
                }else if("10".equals(price)){
                    disjunction.add(Restrictions.between("price",100000,200000));
                }else if("20".equals(price)){
                    disjunction.add(Restrictions.between("price",200000,300000));
                }else if("30".equals(price)){
                    disjunction.add(Restrictions.between("price",300000,500000));
                }else if("50".equals(price)){
                    disjunction.add(Restrictions.between("price",500000,800000));
                }else if("80".equals(price)){
                    disjunction.add(Restrictions.between("price",800000,1000000));
                }else if("100".equals(price)){
                    disjunction.add(Restrictions.between("price",1000000,1000000000));
                }
            }
            criteria.add(disjunction);
        }

        if(StringUtils.isEmpty(itemParam.getCat1())==true &&
                StringUtils.isEmpty(itemParam.getCat2())==true &&
                StringUtils.isEmpty(itemParam.getCat3())==true &&
                StringUtils.isEmpty(itemParam.getCat4())==true &&
                StringUtils.isEmpty(itemParam.getCat5())==true &&
                StringUtils.isEmpty(itemParam.getCat6())==true &&
                StringUtils.isEmpty(itemParam.getCat7())==true ) {
            criteria.add(Restrictions.isNull("cat1"));
            criteria.add(Restrictions.isNull("cat2"));
        }
        else
        {
            /*
            if (StringUtils.isEmpty(itemParam.getCat1()) == false) {
                criteria.add(Restrictions.in("cat1", itemParam.getCat1().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat2()) == false) {
                criteria.add(Restrictions.in("cat2", itemParam.getCat2().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat3()) == false) {
                criteria.add(Restrictions.in("cat3", itemParam.getCat3().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat4()) == false) {
                criteria.add(Restrictions.in("cat4", itemParam.getCat4().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat5()) == false) {
                criteria.add(Restrictions.in("cat5", itemParam.getCat5().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat6()) == false) {
                criteria.add(Restrictions.in("cat6", itemParam.getCat6().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat7()) == false) {
                criteria.add(Restrictions.in("cat7", itemParam.getCat7().split(",")));
            }
            */

            if(StringUtils.isEmpty(itemParam.getCat1())==false){
                criteria.add(Restrictions.in("cat1",itemParam.getCat1().split(",")));
            }
            if(StringUtils.isEmpty(itemParam.getCat2())==false){
                criteria.add(Restrictions.in("cat2",itemParam.getCat2().split(",")));
            }
            else if(StringUtils.isEmpty(itemParam.getCat1())==false && StringUtils.isEmpty(itemParam.getCat2())==true && StringUtils.isEmpty(itemParam.getCat3())==true) {
                criteria.add(Restrictions.isNull("cat2"));
            }
            if(StringUtils.isEmpty(itemParam.getCat3())==false){
                criteria.add(Restrictions.in("cat3",itemParam.getCat3().split(",")));
            }
            else if(StringUtils.isEmpty(itemParam.getCat2())==false && StringUtils.isEmpty(itemParam.getCat3())==true && StringUtils.isEmpty(itemParam.getCat4())==true) {

                if (StringUtils.isEmpty(itemParam.getNull_tp()) == false)
                    criteria.add(Restrictions.isNull("cat3"));
                else
                {

                }
            }
            if(StringUtils.isEmpty(itemParam.getCat4())==false){
                criteria.add(Restrictions.in("cat4",itemParam.getCat4().split(",")));
            }
            else if(StringUtils.isEmpty(itemParam.getCat3())==false && StringUtils.isEmpty(itemParam.getCat4())==true && StringUtils.isEmpty(itemParam.getCat5())==true) {
                if (StringUtils.isEmpty(itemParam.getNull_tp()) == false)
                    criteria.add(Restrictions.isNull("cat4"));
                else
                {

                }
            }
            if(StringUtils.isEmpty(itemParam.getCat5())==false){
                criteria.add(Restrictions.in("cat5",itemParam.getCat5().split(",")));
            }
            else if(StringUtils.isEmpty(itemParam.getCat4())==false && StringUtils.isEmpty(itemParam.getCat5())==true && StringUtils.isEmpty(itemParam.getCat6())==true) {
                if (StringUtils.isEmpty(itemParam.getNull_tp()) == false)
                    criteria.add(Restrictions.isNull("cat5"));
                else
                {

                }
            }
            if(StringUtils.isEmpty(itemParam.getCat6())==false){
                criteria.add(Restrictions.in("cat6",itemParam.getCat6().split(",")));
            }
            else if(StringUtils.isEmpty(itemParam.getCat5())==false && StringUtils.isEmpty(itemParam.getCat6())==true) {
                if (StringUtils.isEmpty(itemParam.getNull_tp()) == false)
                    criteria.add(Restrictions.isNull("cat6"));
                else
                {

                }
            }
            if (StringUtils.isEmpty(itemParam.getCat7()) == false) {
                criteria.add(Restrictions.in("cat7", itemParam.getCat7().split(",")));
            }
        }
        // 가격범위
        if (itemParam.getMinPrice() != null && itemParam.getMaxPrice() != null) {
            criteria.add(Restrictions.between("price", itemParam.getMinPrice(), itemParam.getMaxPrice()));
        }

        /*
        if(StringUtils.isEmpty(itemParam.getCat1())==false){
            criteria.add(Restrictions.in("cat1",itemParam.getCat1().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat2())==false){
            criteria.add(Restrictions.in("cat2",itemParam.getCat2().split(",")));
        }
        else if(StringUtils.isEmpty(itemParam.getCat1())==false && StringUtils.isEmpty(itemParam.getCat2())==true && StringUtils.isEmpty(itemParam.getCat3())==true) {
            criteria.add(Restrictions.isNull("cat2"));
        }

        if(StringUtils.isEmpty(itemParam.getCat3())==false){
            criteria.add(Restrictions.in("cat3",itemParam.getCat3().split(",")));
        }
        else if(StringUtils.isEmpty(itemParam.getCat2())==false && StringUtils.isEmpty(itemParam.getCat3())==true && StringUtils.isEmpty(itemParam.getCat4())==true) {
            criteria.add(Restrictions.isNull("cat3"));
        }

        if(StringUtils.isEmpty(itemParam.getCat4())==false){
            criteria.add(Restrictions.in("cat4",itemParam.getCat4().split(",")));
        }
        else if(StringUtils.isEmpty(itemParam.getCat3())==false && StringUtils.isEmpty(itemParam.getCat4())==true && StringUtils.isEmpty(itemParam.getCat5())==true) {
            criteria.add(Restrictions.isNull("cat4"));
        }

        if(StringUtils.isEmpty(itemParam.getCat5())==false){
            criteria.add(Restrictions.in("cat5",itemParam.getCat5().split(",")));
        }
        else if(StringUtils.isEmpty(itemParam.getCat4())==false && StringUtils.isEmpty(itemParam.getCat5())==true && StringUtils.isEmpty(itemParam.getCat6())==true) {
            criteria.add(Restrictions.isNull("cat5"));
        }

        if(StringUtils.isEmpty(itemParam.getCat6())==false){
            criteria.add(Restrictions.in("cat6",itemParam.getCat6().split(",")));
        }
        else if(StringUtils.isEmpty(itemParam.getCat5())==false && StringUtils.isEmpty(itemParam.getCat6())==true) {
            criteria.add(Restrictions.isNull("cat6"));
        }
*/

        if(StringUtils.isEmpty(itemParam.getSearchWord())==false){
            criteria.add(
                    Restrictions.or(
                            Restrictions.like("itemName",itemParam.getSearchWord(),MatchMode.ANYWHERE),
                            Restrictions.like("description",itemParam.getSearchWord(),MatchMode.ANYWHERE)
                    )
            );
        }
        return criteria.list();
    }


    @Override
    public int selectTotalCnt(ItemParam itemParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", JoinType.INNER_JOIN)
                .setFetchMode("it.brand", FetchMode.JOIN)
                .createAlias("it.brand", "br", JoinType.INNER_JOIN)
                .add(Restrictions.eq("if.isDel","N"))
                .add(Restrictions.eq("if.represent","Y"))
                .add(Restrictions.eq("br.isDel","N"))
                .setProjection(Projections.rowCount());

        if(StringUtils.isEmpty(itemParam.getSiteName())==false){
            criteria.add(Restrictions.in("siteName",itemParam.getSiteName().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getDiscount())==false){
            criteria.add(Restrictions.in("discount",itemParam.getDiscount().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getModName())==false){
            String[] splModName = itemParam.getModName().split(",");
            if(splModName.length == 1){
                if("Y".equals(splModName[0])) {
                    criteria.add(Restrictions.eq("orderly",1));
                }else if("N".equals(splModName[0])){
                    criteria.add(Restrictions.eq("orderly",0));
                }
            }
        }

        if(StringUtils.isEmpty(itemParam.getBrandName())==false){
            criteria.add(Restrictions.in("br.brandName",itemParam.getBrandName().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getPrice())==false){
            String[] splPrice = itemParam.getPrice().split(",");
            Disjunction disjunction = Restrictions.disjunction();
            for(String price : splPrice){
                if("0".equals(price)){
                    disjunction.add(Restrictions.between("price",0,30000));
                }else if("3".equals(price)){
                    disjunction.add(Restrictions.between("price",30000,50000));
                }else if("5".equals(price)){
                    disjunction.add(Restrictions.between("price",50000,100000));
                }else if("10".equals(price)){
                    disjunction.add(Restrictions.between("price",100000,200000));
                }else if("20".equals(price)){
                    disjunction.add(Restrictions.between("price",200000,300000));
                }else if("30".equals(price)){
                    disjunction.add(Restrictions.between("price",300000,500000));
                }else if("50".equals(price)){
                    disjunction.add(Restrictions.between("price",500000,800000));
                }else if("80".equals(price)){
                    disjunction.add(Restrictions.between("price",800000,1000000));
                }else if("100".equals(price)){
                    disjunction.add(Restrictions.between("price",1000000,1000000000));
                }
            }
            criteria.add(disjunction);
        }

        if(StringUtils.isEmpty(itemParam.getCat1())==false){
            criteria.add(Restrictions.in("cat1",itemParam.getCat1().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat2())==false){
            criteria.add(Restrictions.in("cat2",itemParam.getCat2().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat3())==false){
            criteria.add(Restrictions.in("cat3",itemParam.getCat3().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat4())==false){
            criteria.add(Restrictions.in("cat4",itemParam.getCat4().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat5())==false){
            criteria.add(Restrictions.in("cat5",itemParam.getCat5().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat6())==false){
            criteria.add(Restrictions.in("cat6",itemParam.getCat6().split(",")));
        }
        if(StringUtils.isEmpty(itemParam.getCat7())==false){
            criteria.add(Restrictions.in("cat7",itemParam.getCat7().split(",")));
        }


        if(StringUtils.isEmpty(itemParam.getSearchWord())==false){
            criteria.add(
                    Restrictions.or(
                            Restrictions.like("itemName",itemParam.getSearchWord(),MatchMode.ANYWHERE),
                            Restrictions.like("description",itemParam.getSearchWord(),MatchMode.ANYWHERE)
                    )
            );
        }

        Long totalCount = (Long) criteria.uniqueResult();
        Integer nTotalCount = totalCount.intValue();
        return nTotalCount;

    }

    @Override
    public int selectTotalConsoleCnt(ItemParam itemParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", JoinType.INNER_JOIN)
                .setFetchMode("it.brand", FetchMode.JOIN)
                .createAlias("it.brand", "br", JoinType.INNER_JOIN)
                .add(Restrictions.eq("if.isDel","N"))
                .add(Restrictions.eq("if.represent","Y"))
                .add(Restrictions.eq("br.isDel","N"))
                .setProjection(Projections.rowCount());

        if(StringUtils.isEmpty(itemParam.getSiteName())==false){
            criteria.add(Restrictions.in("siteName",itemParam.getSiteName().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getDiscount())==false){
            criteria.add(Restrictions.in("discount",itemParam.getDiscount().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getModName())==false){
            String[] splModName = itemParam.getModName().split(",");
            if(splModName.length == 1){
                if("Y".equals(splModName[0])) {
                    criteria.add(Restrictions.eq("orderly",1));
                }else if("N".equals(splModName[0])){
                    criteria.add(Restrictions.eq("orderly",0));
                }
            }
        }

        if(StringUtils.isEmpty(itemParam.getBrandName())==false){
            criteria.add(Restrictions.in("br.brandName",itemParam.getBrandName().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getPrice())==false){
            String[] splPrice = itemParam.getPrice().split(",");
            Disjunction disjunction = Restrictions.disjunction();
            for(String price : splPrice){
                if("0".equals(price)){
                    disjunction.add(Restrictions.between("price",0,30000));
                }else if("3".equals(price)){
                    disjunction.add(Restrictions.between("price",30000,50000));
                }else if("5".equals(price)){
                    disjunction.add(Restrictions.between("price",50000,100000));
                }else if("10".equals(price)){
                    disjunction.add(Restrictions.between("price",100000,200000));
                }else if("20".equals(price)){
                    disjunction.add(Restrictions.between("price",200000,300000));
                }else if("30".equals(price)){
                    disjunction.add(Restrictions.between("price",300000,500000));
                }else if("50".equals(price)){
                    disjunction.add(Restrictions.between("price",500000,800000));
                }else if("80".equals(price)){
                    disjunction.add(Restrictions.between("price",800000,1000000));
                }else if("100".equals(price)){
                    disjunction.add(Restrictions.between("price",1000000,1000000000));
                }
            }
            criteria.add(disjunction);
        }


        if(StringUtils.isEmpty(itemParam.getCat1())==true &&
                StringUtils.isEmpty(itemParam.getCat2())==true &&
                StringUtils.isEmpty(itemParam.getCat3())==true &&
                StringUtils.isEmpty(itemParam.getCat4())==true &&
                StringUtils.isEmpty(itemParam.getCat5())==true &&
                StringUtils.isEmpty(itemParam.getCat6())==true &&
                StringUtils.isEmpty(itemParam.getCat7())==true ) {
            criteria.add(Restrictions.isNull("cat1"));
            criteria.add(Restrictions.isNull("cat2"));
        }
        else
        {
            /*
            if (StringUtils.isEmpty(itemParam.getCat1()) == false) {
                criteria.add(Restrictions.in("cat1", itemParam.getCat1().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat2()) == false) {
                criteria.add(Restrictions.in("cat2", itemParam.getCat2().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat3()) == false) {
                criteria.add(Restrictions.in("cat3", itemParam.getCat3().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat4()) == false) {
                criteria.add(Restrictions.in("cat4", itemParam.getCat4().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat5()) == false) {
                criteria.add(Restrictions.in("cat5", itemParam.getCat5().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat6()) == false) {
                criteria.add(Restrictions.in("cat6", itemParam.getCat6().split(",")));
            }
            if (StringUtils.isEmpty(itemParam.getCat7()) == false) {
                criteria.add(Restrictions.in("cat7", itemParam.getCat7().split(",")));
            }
            */

            if(StringUtils.isEmpty(itemParam.getCat1())==false){
                criteria.add(Restrictions.in("cat1",itemParam.getCat1().split(",")));
            }
            if(StringUtils.isEmpty(itemParam.getCat2())==false){
                criteria.add(Restrictions.in("cat2",itemParam.getCat2().split(",")));
            }
            else if(StringUtils.isEmpty(itemParam.getCat1())==false && StringUtils.isEmpty(itemParam.getCat2())==true && StringUtils.isEmpty(itemParam.getCat3())==true) {
                criteria.add(Restrictions.isNull("cat2"));
            }
            if(StringUtils.isEmpty(itemParam.getCat3())==false){
                criteria.add(Restrictions.in("cat3",itemParam.getCat3().split(",")));
            }
            else if(StringUtils.isEmpty(itemParam.getCat2())==false && StringUtils.isEmpty(itemParam.getCat3())==true && StringUtils.isEmpty(itemParam.getCat4())==true) {

                if (StringUtils.isEmpty(itemParam.getNull_tp()) == false)
                    criteria.add(Restrictions.isNull("cat3"));
                else
                {

                }
            }
            if(StringUtils.isEmpty(itemParam.getCat4())==false){
                criteria.add(Restrictions.in("cat4",itemParam.getCat4().split(",")));
            }
            else if(StringUtils.isEmpty(itemParam.getCat3())==false && StringUtils.isEmpty(itemParam.getCat4())==true && StringUtils.isEmpty(itemParam.getCat5())==true) {
                if (StringUtils.isEmpty(itemParam.getNull_tp()) == false)
                    criteria.add(Restrictions.isNull("cat4"));
                else
                {

                }
            }
            if(StringUtils.isEmpty(itemParam.getCat5())==false){
                criteria.add(Restrictions.in("cat5",itemParam.getCat5().split(",")));
            }
            else if(StringUtils.isEmpty(itemParam.getCat4())==false && StringUtils.isEmpty(itemParam.getCat5())==true && StringUtils.isEmpty(itemParam.getCat6())==true) {
                if (StringUtils.isEmpty(itemParam.getNull_tp()) == false)
                    criteria.add(Restrictions.isNull("cat5"));
                else
                {

                }
            }
            if(StringUtils.isEmpty(itemParam.getCat6())==false){
                criteria.add(Restrictions.in("cat6",itemParam.getCat6().split(",")));
            }
            else if(StringUtils.isEmpty(itemParam.getCat5())==false && StringUtils.isEmpty(itemParam.getCat6())==true) {
                if (StringUtils.isEmpty(itemParam.getNull_tp()) == false)
                    criteria.add(Restrictions.isNull("cat6"));
                else
                {

                }
            }
            if (StringUtils.isEmpty(itemParam.getCat7()) == false) {
                criteria.add(Restrictions.in("cat7", itemParam.getCat7().split(",")));
            }
        }
        // 가격범위
        if (itemParam.getMinPrice() != null && itemParam.getMaxPrice() != null) {
            criteria.add(Restrictions.between("price", itemParam.getMinPrice(), itemParam.getMaxPrice()));
        }

        /*
        if(StringUtils.isEmpty(itemParam.getCat1())==false){
            criteria.add(Restrictions.in("cat1",itemParam.getCat1().split(",")));
        }

        if(StringUtils.isEmpty(itemParam.getCat2())==false){
            criteria.add(Restrictions.in("cat2",itemParam.getCat2().split(",")));
        }
        else if(StringUtils.isEmpty(itemParam.getCat1())==false && StringUtils.isEmpty(itemParam.getCat2())==true && StringUtils.isEmpty(itemParam.getCat3())==true) {
            criteria.add(Restrictions.isNull("cat2"));
        }

        if(StringUtils.isEmpty(itemParam.getCat3())==false){
            criteria.add(Restrictions.in("cat3",itemParam.getCat3().split(",")));
        }
        else if(StringUtils.isEmpty(itemParam.getCat2())==false && StringUtils.isEmpty(itemParam.getCat3())==true && StringUtils.isEmpty(itemParam.getCat4())==true) {
            criteria.add(Restrictions.isNull("cat3"));
        }

        if(StringUtils.isEmpty(itemParam.getCat4())==false){
            criteria.add(Restrictions.in("cat4",itemParam.getCat4().split(",")));
        }
        else if(StringUtils.isEmpty(itemParam.getCat3())==false && StringUtils.isEmpty(itemParam.getCat4())==true && StringUtils.isEmpty(itemParam.getCat5())==true) {
            criteria.add(Restrictions.isNull("cat4"));
        }

        if(StringUtils.isEmpty(itemParam.getCat5())==false){
            criteria.add(Restrictions.in("cat5",itemParam.getCat5().split(",")));
        }
        else if(StringUtils.isEmpty(itemParam.getCat4())==false && StringUtils.isEmpty(itemParam.getCat5())==true && StringUtils.isEmpty(itemParam.getCat6())==true) {
            criteria.add(Restrictions.isNull("cat5"));
        }

        if(StringUtils.isEmpty(itemParam.getCat6())==false){
            criteria.add(Restrictions.in("cat6",itemParam.getCat6().split(",")));
        }
        else if(StringUtils.isEmpty(itemParam.getCat5())==false && StringUtils.isEmpty(itemParam.getCat6())==true) {
            criteria.add(Restrictions.isNull("cat6"));
        }
*/

        if(StringUtils.isEmpty(itemParam.getSearchWord())==false){
            criteria.add(
                    Restrictions.or(
                            Restrictions.like("itemName",itemParam.getSearchWord(),MatchMode.ANYWHERE),
                            Restrictions.like("description",itemParam.getSearchWord(),MatchMode.ANYWHERE)
                    )
            );
        }


        Long totalCount = (Long) criteria.uniqueResult();
        Integer nTotalCount = totalCount.intValue();
        return nTotalCount;


    }

    @Override
    public List<Item> selectSiteList() {
        List<Item> siteList = getSessionFactory().getCurrentSession().createCriteria(Item.class)
                .add(Restrictions.eq("isDel","N"))
                .add(Restrictions.isNotNull("siteName"))
                .setProjection(Projections.distinct(Projections.property("siteName")))
                .addOrder(Order.asc("siteName"))
                .list();
        return siteList;
    }

    @Override
    public List<Item> getCategory1() {
        List<Item> result = getSessionFactory().getCurrentSession().createCriteria(Item.class)
                .add(Restrictions.eq("isDel","N"))
                .setProjection(Projections.distinct(Projections.property("cat1")))
                .addOrder(Order.asc("cat1"))
                .list();

        return result;
    }

    @Override
    public List<ImageFile> getCategory1Image() {
        List<ImageFile> result = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", Criteria.LEFT_JOIN)
                .setProjection(Projections.projectionList()
                        .add(Projections.property("if.originName"), "originName")
                        .add(Projections.property("if.originPath"), "originPath")
                ).setResultTransformer(Transformers.aliasToBean(ImageFile.class))
                .setMaxResults(3)
                .list();
        return result;
    }

    @Override
    public List<Item> getCategory2(ItemParam itemParam) {
        List<Item> result = getSessionFactory().getCurrentSession().createCriteria(Item.class)
                .add(Restrictions.eq("isDel","N"))
                .add(Restrictions.in("cat1",itemParam.getCat1().split(",")))
                .setProjection(Projections.distinct(Projections.property("cat2")))
                .addOrder(Order.asc("cat2"))
                .list();
        return result;
    }

    @Override
    public List<ImageFile> getCategory2Image(ItemParam itemParam) {
       List<ImageFile> result = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", Criteria.LEFT_JOIN)
                .add(Restrictions.eq("it.cat1",itemParam.getCat1().split(",")[0]))
                .add(Restrictions.not(Restrictions.eq("it.itemNo",itemParam.getItemNo())))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("if.originName"), "originName")
                        .add(Projections.property("if.originPath"), "originPath")
                ).setResultTransformer(Transformers.aliasToBean(ImageFile.class))
                .setMaxResults(3)
               .list();
        return result;
    }

    @Override
    public List<Item> getCategory3(ItemParam itemParam) {
        List<Item> result = getSessionFactory().getCurrentSession().createCriteria(Item.class)
                .add(Restrictions.eq("isDel","N"))
                .add(Restrictions.in("cat1",itemParam.getCat1().split(",")))
                .add(Restrictions.in("cat2",itemParam.getCat2().split(",")))
                .setProjection(Projections.distinct(Projections.property("cat3")))
                .addOrder(Order.asc("cat3"))
                .list();
        return result;
    }

    @Override
    public List<ImageFile> getCategory3Image(ItemParam itemParam) {
        List<ImageFile> result = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", Criteria.LEFT_JOIN)
                .add(Restrictions.not(Restrictions.eq("it.itemNo",itemParam.getItemNo())))
                .add(Restrictions.in("cat1",itemParam.getCat1().split(",")))
                .add(Restrictions.in("cat2",itemParam.getCat2().split(",")))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("if.originName"), "originName")
                        .add(Projections.property("if.originPath"), "originPath")
                ).setResultTransformer(Transformers.aliasToBean(ImageFile.class))
                .addOrder(Order.asc("ranking"))
                .addOrder(Order.desc("regDate"))
                .setMaxResults(3)
                .list();
        return result;
    }

    @Override
    public List<Item> getCategory4(ItemParam itemParam) {
        List<Item> result = getSessionFactory().getCurrentSession().createCriteria(Item.class)
                .add(Restrictions.eq("isDel","N"))
                .add(Restrictions.in("cat1",itemParam.getCat1().split(",")))
                .add(Restrictions.in("cat2",itemParam.getCat2().split(",")))
                .add(Restrictions.in("cat3",itemParam.getCat3().split(",")))
                .setProjection(Projections.distinct(Projections.property("cat4")))
                .addOrder(Order.asc("cat4"))
                .list();
        return result;
    }

    @Override
    public List<ImageFile> getCategory4Image(ItemParam itemParam) {
        List<ImageFile> result= getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", Criteria.LEFT_JOIN)
                .add(Restrictions.in("cat1",itemParam.getCat1().split(",")))
                .add(Restrictions.in("cat2",itemParam.getCat2().split(",")))
                .add(Restrictions.in("cat3",itemParam.getCat3().split(",")))
                .add(Restrictions.not(Restrictions.eq("it.itemNo",itemParam.getItemNo())))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("if.originName"), "originName")
                        .add(Projections.property("if.originPath"), "originPath")
                ).setResultTransformer(Transformers.aliasToBean(ImageFile.class))
                .addOrder(Order.asc("ranking"))
                .addOrder(Order.desc("regDate"))
                .setMaxResults(3)
                .list();
        return result;
    }

    @Override
    public List<Item> getCategory5(ItemParam itemParam) {
        List<Item> result = getSessionFactory().getCurrentSession().createCriteria(Item.class)
                .add(Restrictions.eq("isDel","N"))
                .add(Restrictions.in("cat1",itemParam.getCat1().split(",")))
                .add(Restrictions.in("cat2",itemParam.getCat2().split(",")))
                .add(Restrictions.in("cat3",itemParam.getCat3().split(",")))
                .add(Restrictions.in("cat4",itemParam.getCat4().split(",")))
                .setProjection(Projections.distinct(Projections.property("cat5")))
                .addOrder(Order.asc("cat5"))
                .list();
        return result;
    }

    @Override
    public List<ImageFile> getCategory5Image(ItemParam itemParam) {
        List<ImageFile> result = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", Criteria.LEFT_JOIN)
                .add(Restrictions.eq("it.cat1",itemParam.getCat1().split(",")[0]))
                .add(Restrictions.eq("it.cat2",itemParam.getCat2().split(",")[0]))
                .add(Restrictions.eq("it.cat3",itemParam.getCat3().split(",")[0]))
                .add(Restrictions.eq("it.cat4",itemParam.getCat4().split(",")[0]))
                .add(Restrictions.not(Restrictions.eq("it.itemNo",itemParam.getItemNo())))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("if.originName"), "originName")
                        .add(Projections.property("if.originPath"), "originPath")
                ).setResultTransformer(Transformers.aliasToBean(ImageFile.class))
                .addOrder(Order.asc("ranking"))
                .addOrder(Order.desc("regDate"))
                .setMaxResults(3)
                .list();
        return result;
    }

    @Override
    public List<Item> getCategory6(ItemParam itemParam) {
        List<Item> result = getSessionFactory().getCurrentSession().createCriteria(Item.class)
                .add(Restrictions.eq("isDel","N"))
                .add(Restrictions.in("cat1",itemParam.getCat1().split(",")))
                .add(Restrictions.in("cat2",itemParam.getCat2().split(",")))
                .add(Restrictions.in("cat3",itemParam.getCat3().split(",")))
                .add(Restrictions.in("cat4",itemParam.getCat4().split(",")))
                .add(Restrictions.in("cat5",itemParam.getCat5().split(",")))
                .setProjection(Projections.distinct(Projections.property("cat6")))
                .addOrder(Order.asc("cat6"))
                .list();
        return result;
    }

    @Override
    public List<ImageFile> getCategory6Image(ItemParam itemParam) {
        List<ImageFile> result = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", Criteria.LEFT_JOIN)
                .add(Restrictions.eq("it.cat1",itemParam.getCat1().split(",")[0]))
                .add(Restrictions.eq("it.cat2",itemParam.getCat2().split(",")[0]))
                .add(Restrictions.eq("it.cat3",itemParam.getCat3().split(",")[0]))
                .add(Restrictions.eq("it.cat4",itemParam.getCat4().split(",")[0]))
                .add(Restrictions.eq("it.cat5",itemParam.getCat5().split(",")[0]))
                .add(Restrictions.not(Restrictions.eq("it.itemNo",itemParam.getItemNo())))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("if.originName"), "originName")
                        .add(Projections.property("if.originPath"), "originPath")
                ).setResultTransformer(Transformers.aliasToBean(ImageFile.class))
                .addOrder(Order.asc("ranking"))
                .addOrder(Order.desc("regDate"))
                .setMaxResults(3)
                .list();
        return result;
    }


    @Override
    public List<ImageFile> getCategoryAllImage(ItemParam itemParam) {
        List<ImageFile> result = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", Criteria.LEFT_JOIN)
                .add(Restrictions.eq("it.cat1",itemParam.getCat1().split(",")[0]))
                .add(Restrictions.eq("it.cat2",itemParam.getCat2().split(",")[0]))
                .add(Restrictions.eq("it.cat3",itemParam.getCat3().split(",")[0]))
                .add(Restrictions.eq("it.cat4",itemParam.getCat4().split(",")[0]))
                .add(Restrictions.eq("it.cat5",itemParam.getCat5().split(",")[0]))
                .add(Restrictions.eq("it.cat6",itemParam.getCat6().split(",")[0]))
                .add(Restrictions.not(Restrictions.eq("it.itemNo",itemParam.getItemNo())))
                .setProjection(Projections.projectionList()
                        .add(Projections.property("if.originName"), "originName")
                        .add(Projections.property("if.originPath"), "originPath")
                ).setResultTransformer(Transformers.aliasToBean(ImageFile.class))
                .addOrder(Order.asc("ranking"))
                .addOrder(Order.desc("regDate"))
                .setMaxResults(3)
                .list();
        return result;
    }

    @Override
    public Item selectItem(Integer itemNo) {
        Item item = (Item) getSessionFactory().getCurrentSession().createCriteria(Item.class)
                .add(Restrictions.eq("itemNo",itemNo))
                .setMaxResults(1)
                .uniqueResult();
        return item;
    }
    @Override
    public Item selectAlertItem(Integer itemNo, String alertDate){

        Criteria criteria = (Criteria) getSessionFactory().getCurrentSession().createCriteria(Item.class)
                .add(Restrictions.eq("itemNo",itemNo))
                .setMaxResults(0);
        Criterion rest1 = Restrictions.or(Restrictions.gt("regDate", alertDate), Restrictions.gt("modifyDate", alertDate));
        criteria.add(rest1);

        if(criteria.uniqueResult() != null) {
            return (Item) criteria.list().get(0);
        }
        else return null;
    }

    @Override
    public void updateItem(Item item) {
        getSessionFactory().getCurrentSession().update(item);
    }


    @Override
    public Item selectCategoryItem(ItemParam itemParam) {
        Item item = (Item) getSessionFactory().getCurrentSession().createCriteria(Item.class)
                .add(Restrictions.eq("cat1",itemParam.getCat1()))
                .add(Restrictions.eq("cat2",itemParam.getCat2()))
                .add(Restrictions.eq("cat3",itemParam.getCat3()))
                .add(Restrictions.eq("cat4",itemParam.getCat4()))
                .add(Restrictions.eq("cat5",itemParam.getCat5()))
                .add(Restrictions.eq("ranking",itemParam.getRanking()))
                .setMaxResults(1)
                .uniqueResult();
        return item;
    }

    @Override
    public List<Item> selectCategoryTitle(ItemParam itemParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class);
        criteria.add(Restrictions.eq("cat1", itemParam.getCat1()));
        criteria.add(Restrictions.eq("isDel","N"));
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.groupProperty("cat2"));
        criteria.setProjection(projectionList);

        return criteria.list();
    }

    @Override
    public List<Item> selectCategoryBrandTitle(ItemParam itemParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class);
        if(!Strings.isNullOrEmpty(itemParam.getCat1())){
            criteria.add(Restrictions.eq("cat1", itemParam.getCat1()));
        }
        criteria.add(Restrictions.eq("brandNo", itemParam.getBrandNo()));
        criteria.add(Restrictions.eq("isDel","N"));
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.groupProperty("cat2"));
        criteria.setProjection(projectionList);

        return criteria.list();
    }

    private static final String CAT2_ACCESSORIES = "Accessories";

    @Override
    public List<Item> selectAccessoriesCategoryList(ItemParam itemParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class);
        criteria.add(Restrictions.eq("cat1", itemParam.getCat1()));
        criteria.add(Restrictions.eq("cat2", CAT2_ACCESSORIES));
        criteria.add(Restrictions.isNotNull("cat3"));
        criteria.add(Restrictions.eq("isDel","N"));
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.groupProperty("cat2"))
                .add(Projections.groupProperty("cat3"));
        criteria.setProjection(projectionList);

        return criteria.list();
    }

    @Override
    public List<Item> selectCategoryListWithoutAccessories(ItemParam itemParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class);
        criteria.add(Restrictions.eq("cat1", itemParam.getCat1()));
        criteria.add(Restrictions.isNotNull("cat2"));
        criteria.add(Restrictions.ne("cat2", CAT2_ACCESSORIES));
        criteria.add(Restrictions.isNotNull("cat4"));
        criteria.add(Restrictions.eq("isDel","N"));
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.groupProperty("cat2"))
                .add(Projections.groupProperty("cat4"));
        criteria.setProjection(projectionList);

        return criteria.list();
    }


    @Override
    public List<Item> selectAccessoriesCategoryBrandList(ItemParam itemParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class);
        if(!Strings.isNullOrEmpty(itemParam.getCat1())){
            criteria.add(Restrictions.eq("cat1", itemParam.getCat1()));
        }
        criteria.add(Restrictions.eq("cat2", CAT2_ACCESSORIES));
        criteria.add(Restrictions.isNotNull("cat3"));
        criteria.add(Restrictions.eq("brandNo", itemParam.getBrandNo()));
        criteria.add(Restrictions.eq("isDel","N"));
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.groupProperty("cat2"))
                .add(Projections.groupProperty("cat3"));
        criteria.setProjection(projectionList);

        return criteria.list();
    }

    @Override
    public List<Item> selectCategoryBrandListWithoutAccessories(ItemParam itemParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class);
        if(!Strings.isNullOrEmpty(itemParam.getCat1())){
            criteria.add(Restrictions.eq("cat1", itemParam.getCat1()));
        }
        criteria.add(Restrictions.isNotNull("cat2"));
        criteria.add(Restrictions.ne("cat2", CAT2_ACCESSORIES));
        criteria.add(Restrictions.isNotNull("cat4"));
        criteria.add(Restrictions.eq("brandNo", itemParam.getBrandNo()));
        criteria.add(Restrictions.eq("isDel","N"));
        ProjectionList projectionList = Projections.projectionList()
                .add(Projections.groupProperty("cat2"))
                .add(Projections.groupProperty("cat4"));
        criteria.setProjection(projectionList);

        return criteria.list();
    }

    @Override
    public List<Item> selectItemMenu(ItemParam itemParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class);
        criteria.setFetchMode("imageFile", FetchMode.JOIN);
        criteria.add(Restrictions.eq("cat1", itemParam.getCat1()));
        criteria.add(Restrictions.eq("isDel","N"));



        // 관리자가 수정한 데이터만 보여진다.
//        criteria.add(Restrictions.or(
//                        Restrictions.isNotEmpty("modifyDate"),
//                        Restrictions.isNotNull("modifyDate")
//                ));
        // 세일여부
        if(!Strings.isNullOrEmpty(itemParam.getDiscount())){
            criteria.add(Restrictions.eq("discount", itemParam.getDiscount()));
        }
        // 가격범위
        if(itemParam.getMinPrice()!=null && itemParam.getMaxPrice()!=null){
            criteria.add(Restrictions.between("price", itemParam.getMinPrice(), itemParam.getMaxPrice()));
        }

        // 카테고리 필터
        if(!Strings.isNullOrEmpty(itemParam.getCat2())){
            criteria.add(Restrictions.eq("cat2", itemParam.getCat2()));
            if(!Strings.isNullOrEmpty(itemParam.getCat3())){
                criteria.add(Restrictions.eq("cat3", itemParam.getCat3()));
                if(!Strings.isNullOrEmpty(itemParam.getCat4())){
                    criteria.add(Restrictions.eq("cat4", itemParam.getCat4()));
                }
            }
        }
        criteria.addOrder(Order.desc("itemNo"));
        criteria.setMaxResults(9); // 9개씩, 추후에 하나를 제외한다. 더 추가하는 부분을 위해 하나를 더 가지고 온다.
        if(itemParam.getPageCnt() != null && itemParam.getPageCnt() != 0) {
            criteria.setFirstResult((itemParam.getPageCnt() - 1) * 9); // 9개씩 보여진다.
        }
        return criteria.list();
    }

    @Override
    public List<Item> selectItemBrandMenu(ItemParam itemParam) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class);

        criteria.add(Restrictions.eq("brandNo",itemParam.getBrandNo()));
        criteria.add(Restrictions.eq("isDel","N"));
//        // 관리자가 수정한 데이터만 보여진다.
//        criteria.add(Restrictions.or(
//                        Restrictions.isNotEmpty("modifyDate"),
//                        Restrictions.isNotNull("modifyDate")
//                ));
        //cat1
        if(!Strings.isNullOrEmpty(itemParam.getCat1())){
            criteria.add(Restrictions.eq("cat1", itemParam.getCat1()));
        }
        // 세일여부
        if(!Strings.isNullOrEmpty(itemParam.getDiscount())){
            criteria.add(Restrictions.eq("discount", itemParam.getDiscount()));
        }
        // 가격범위
        if(itemParam.getMinPrice()!=null && itemParam.getMaxPrice()!=null){
            criteria.add(Restrictions.between("price", itemParam.getMinPrice(), itemParam.getMaxPrice()));
        }

        // 카테고리 필터
        if(!Strings.isNullOrEmpty(itemParam.getCat2())){
            criteria.add(Restrictions.eq("cat2", itemParam.getCat2()));
            if(!Strings.isNullOrEmpty(itemParam.getCat3())){
                criteria.add(Restrictions.eq("cat3", itemParam.getCat3()));
                if(!Strings.isNullOrEmpty(itemParam.getCat4())){
                    criteria.add(Restrictions.eq("cat4", itemParam.getCat4()));
                }
            }
        }
        criteria.addOrder(Order.desc("itemNo"));
        criteria.setMaxResults(10); // 9개씩, 추후에 하나를 제외한다. 더 추가하는 부분을 위해 하나를 더 가지고 온다.
//        criteria.setFirstResult((1 - 1) * 9); // 9개씩 보여진다.
        if(itemParam.getPageCnt() != null && itemParam.getPageCnt() != 0) {
        criteria.setFirstResult((itemParam.getPageCnt() - 1) * 9); // 9개씩 보여진다.
        }
        for(Item item : (List<Item>)criteria.list()){
            Hibernate.initialize(item.getImageFile());
        }

        return criteria.list();
    }

    @Override
    public List<ItemParam> selectMyPageList(ItemParam itemParam, PagingParam pagingParam){

        return (List<ItemParam>)getSessionFactory().getCurrentSession().createQuery(
                "SELECT li.userNo AS userNo, li.targetNo AS targetNo, li.kind AS kind, it.itemNo AS itemNo, " +
                "it.itemName AS itemName, it.price AS priceInt, imf.imageFileNo AS imageFileNo, imf.originName AS originName, " +
                "imf.originPath AS originPath, imf.represent AS represent " +
                "FROM Item it, Likes li, ImageFile imf " +
                "WHERE it.itemNo = li.targetNo AND it.itemNo = imf.itemNo " +
                "AND it.isDel = 'N' AND imf.isDel = 'N' AND li.isDel = 'N' " +
                "AND imf.represent = 'Y' AND li.kind = 'I' AND li.userNo = ? " +
                "ORDER BY it.itemNo DESC "
        )
        .setParameter(0, itemParam.getUserNo())
        .setMaxResults(pagingParam.getCountPerPage())
        .setFirstResult((pagingParam.getNowPage() - 1) * pagingParam.getCountPerPage())
        .setResultTransformer(Transformers.aliasToBean(ItemParam.class))
        .list();

    }

    @Override
    public Integer selectMyPageTotalCnt(ItemParam itemParam){
        return ((Long)getSessionFactory().getCurrentSession().createQuery(
                "SELECT COUNT(*) AS pageTotCnt " +
                        "FROM Item it, Likes li, ImageFile imf " +
                        "WHERE it.itemNo = li.targetNo AND it.itemNo = imf.itemNo " +
                        "AND it.isDel = 'N' AND imf.isDel = 'N' AND li.isDel = 'N' " +
                        "AND imf.represent = 'Y' AND li.kind = 'I' AND li.userNo = ?"
        ).setParameter(0, itemParam.getUserNo()).iterate().next()).intValue();
    }

    @Override
    public List<ItemParam> categoryLevel1(ItemParam itemParam){

        String sql = "";
        sql += "SELECT i1.cat2 AS cat2 ,(";
        sql += "    SELECT COUNT(*) ";
        sql += "    FROM Item i2 ";
        sql += "    WHERE i2.cat3 IS NOT NULL ";
        sql += "      AND i2.cat2 = i1.cat2";
        sql += "      AND i2.isDel = 'N'";
        sql += ") AS catCnt ";
        sql += "FROM Item i1 ";
        if(itemParam.getBrandNo()!=null && itemParam.getBrandNo() != 0){
            sql += ", Brand b ";
        }
        sql += "WHERE i1.cat2 IS NOT NULL ";
        if(!Strings.isNullOrEmpty(itemParam.getCat1())){
            sql += "  AND i1.cat1 = :cat1";
        }
        if(itemParam.getBrandNo()!=null && itemParam.getBrandNo() != 0){
            sql += "AND b.brandNo = i1.brandNo ";
            sql += "AND b.brandNo = :brandNo";
        }
        sql += "  AND i1.isDel = 'N' ";
        sql += "GROUP BY i1.cat2";

        Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
                .addScalar("cat2", StandardBasicTypes.STRING)
                .addScalar("catCnt", StandardBasicTypes.INTEGER);
        if(!Strings.isNullOrEmpty(itemParam.getCat1())){
            query.setParameter("cat1", itemParam.getCat1());
        }
        if(itemParam.getBrandNo()!=null && itemParam.getBrandNo() != 0){
            query.setParameter("brandNo", itemParam.getBrandNo());
        }
        query.setResultTransformer(Transformers.aliasToBean(ItemParam.class));

        return (List<ItemParam>)query.list();
    }

    @Override
    public List<ItemParam> categoryLevel2(ItemParam itemParam){

        String sql = "";
        sql += "SELECT i1.cat3 AS cat3 ,(";
        sql += "    SELECT COUNT(*) ";
        sql += "    FROM Item i2 ";
        sql += "    WHERE i2.cat4 IS NOT NULL ";
        sql += "      AND i2.cat3 = i1.cat3";
        sql += "      AND i2.isDel = 'N'";
        sql += ") AS catCnt ";
        sql += "FROM Item i1 ";
        if(itemParam.getBrandNo()!=null && itemParam.getBrandNo() != 0){
            sql += ", Brand b ";
        }
        sql += "WHERE i1.cat3 IS NOT NULL ";
        if(!Strings.isNullOrEmpty(itemParam.getCat1())){
            sql += "  AND i1.cat1 = :cat1";
        }
        if(itemParam.getBrandNo()!=null && itemParam.getBrandNo() != 0){
            sql += "AND b.brandNo = i1.brandNo ";
            sql += "AND b.brandNo = :brandNo";
        }
        sql += "  AND i1.isDel = 'N' ";
        sql += "GROUP BY i1.cat3";

        Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
                .addScalar("cat3", StandardBasicTypes.STRING)
                .addScalar("catCnt", StandardBasicTypes.INTEGER);
        if(!Strings.isNullOrEmpty(itemParam.getCat1())){
            query.setParameter("cat1", itemParam.getCat1());
        }
        if(itemParam.getBrandNo()!=null && itemParam.getBrandNo() != 0){
            query.setParameter("brandNo", itemParam.getBrandNo());
        }
        query.setResultTransformer(Transformers.aliasToBean(ItemParam.class));

        return (List<ItemParam>)query.list();
    }

    @Override
    public List<ItemParam> categoryLevel3(ItemParam itemParam){

        String sql = "";
        sql += "SELECT i1.cat4 AS cat4 ,(";
        sql += "    SELECT COUNT(*) ";
        sql += "    FROM Item i2 ";
        sql += "    WHERE i2.cat5 IS NOT NULL ";
        sql += "      AND i2.cat4 = i1.cat4";
        sql += "      AND i2.isDel = 'N'";
        sql += ") AS catCnt ";
        sql += "FROM Item i1 ";
        if(itemParam.getBrandNo()!=null && itemParam.getBrandNo() != 0){
            sql += ", Brand b ";
        }
        sql += "WHERE i1.cat4 IS NOT NULL ";
        if(!Strings.isNullOrEmpty(itemParam.getCat1())){
            sql += "  AND i1.cat1 = :cat1";
        }
        if(itemParam.getBrandNo()!=null && itemParam.getBrandNo() != 0){
            sql += "AND b.brandNo = i1.brandNo ";
            sql += "AND b.brandNo = :brandNo";
        }
        sql += "  AND i1.isDel = 'N' ";
        sql += "GROUP BY i1.cat4";

        Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
                .addScalar("cat4", StandardBasicTypes.STRING)
                .addScalar("catCnt", StandardBasicTypes.INTEGER);
        if(!Strings.isNullOrEmpty(itemParam.getCat1())){
            query.setParameter("cat1", itemParam.getCat1());
        }
        if(itemParam.getBrandNo()!=null && itemParam.getBrandNo() != 0){
            query.setParameter("brandNo", itemParam.getBrandNo());
        }
        query.setResultTransformer(Transformers.aliasToBean(ItemParam.class));

        return (List<ItemParam>)query.list();
    }

    @Override
    public Item select(ItemParam itemParam){
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class);
        criteria.add(Restrictions.eq("isDel","N"));
        criteria.add(Restrictions.eq("itemNo", itemParam.getItemNo()));
        criteria.uniqueResult();
        criteria.setMaxResults(1);
        return (Item)criteria.list().get(0);
    }

    @Override
    public List<ItemResult> selectSimilarList(Item item) {
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Item.class,"it")
                .setFetchMode("it.imageFile", FetchMode.JOIN)
                .createAlias("it.imageFile", "if", JoinType.INNER_JOIN)
                .setFetchMode("it.brand", FetchMode.JOIN)
                .createAlias("it.brand", "br", JoinType.INNER_JOIN)
                .add(Restrictions.eq("it.cat1",item.getCat1()))
                .add(Restrictions.eq("it.cat2",item.getCat2()))
                .add(Restrictions.eq("it.cat3",item.getCat3()))
                .add(Restrictions.eq("if.isDel","N"))
                .add(Restrictions.eq("if.represent","Y"))
                .add(Restrictions.eq("br.isDel","N"))
                .setMaxResults(10)
                .setProjection(Projections.projectionList()
                        .add(Projections.property("it.itemNo"), "itemNo")
                        .add(Projections.property("it.cat1"), "cat1")
                        .add(Projections.property("it.cat2"), "cat2")
                        .add(Projections.property("it.cat3"), "cat3")
                        .add(Projections.property("itemName"), "itemName")
                        .add(Projections.property("price"), "price")
                        .add(Projections.property("if.originName"), "originName")
                        .add(Projections.property("if.originPath"), "originPath")
                        .add(Projections.property("br.brandName"), "brandName")
                ).setResultTransformer(Transformers.aliasToBean(ItemResult.class))
                .addOrder(Order.desc("it.itemNo"))
                .addOrder(Order.asc("it.orderly"));

        return criteria.list();
    }

    @Override
    public ItemParam itemDetail(ItemParam itemParam){
        String sql = "";
        sql += "SELECT i.itemNo, i.itemName, i.cat1, i.cat2, i.cat3, i.link, i.price, i.saleprice, b.brandName, ifs.originName, ifs.originPath , ifs.resizeName ";
        sql += "  FROM Item i, Brand b, ImageFile ifs";
        sql += " WHERE  1=1";
        sql += "   AND i.brandNo = b.brandNo";
        sql += "   AND i.itemNo = ifs.itemNo";
        sql += "   AND ifs.represent = 'Y'";
        sql += "   AND i.isDel = 'N'";
        sql += "   AND b.isDel = 'N'";
        sql += "   AND ifs.isDel = 'N'";
        sql += "   AND i.itemNo = :itemNo";
        Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
                .addScalar("itemNo", StandardBasicTypes.INTEGER)
                .addScalar("itemName", StandardBasicTypes.STRING)
                .addScalar("cat1", StandardBasicTypes.STRING)
                .addScalar("cat2", StandardBasicTypes.STRING)
                .addScalar("cat3", StandardBasicTypes.STRING)
                .addScalar("link", StandardBasicTypes.STRING)
                .addScalar("price", StandardBasicTypes.STRING)
                .addScalar("saleprice", StandardBasicTypes.STRING)
                .addScalar("brandName", StandardBasicTypes.STRING)
                .addScalar("originName", StandardBasicTypes.STRING)
                .addScalar("originPath", StandardBasicTypes.STRING)
                .addScalar("resizeName", StandardBasicTypes.STRING);
        query.setParameter("itemNo", itemParam.getItemNo());
        query.setResultTransformer(Transformers.aliasToBean(ItemParam.class));
        if(query.list().size() > 0){
            return (ItemParam)query.list().get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<ImageFileParam> itemImageDetail(ItemParam itemParam){
        String sql = "";
        sql += "SELECT imageFileNo, itemNo, originName, originPath ";
        sql += "  FROM ImageFile";
        sql += " WHERE  1=1";
        sql += "   AND isDel = 'N'";
        sql += "   AND itemNo = :itemNo";
        Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
                .addScalar("imageFileNo", StandardBasicTypes.INTEGER)
                .addScalar("itemNo", StandardBasicTypes.INTEGER)
                .addScalar("originName", StandardBasicTypes.STRING)
                .addScalar("originPath", StandardBasicTypes.STRING);
        query.setParameter("itemNo", itemParam.getItemNo());
        query.setResultTransformer(Transformers.aliasToBean(ImageFileParam.class));
        return (List<ImageFileParam>)query.list();
    }

    @Override
    public Integer itemLikesCnt(ItemParam itemParam){
        String sql = "";
        sql += "SELECT COUNT(*) ";
        sql += "  FROM Likes";
        sql += " WHERE  1=1";
        sql += "   AND isDel = 'N'";
        sql += "   AND kind = 'I'";
        sql += "   AND targetNo = :itemNo";
        Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.setParameter("itemNo", itemParam.getItemNo());
        Long result = ((Number)query.uniqueResult()).longValue();
        return result.intValue();
    }

    @Override
    public List<CommentParam> itemCommentDetail(ItemParam itemParam){

        Integer commentCnt = 0;
        if(itemParam.getPageCommentCnt()!=null){
            commentCnt = itemParam.getPageCommentCnt();
        }
        String sql = "";
        sql += "SELECT cm.commentNo, cm.userNo, cm.targetNo, cm.text, ui.userName ";
        sql += "  FROM Comment cm, UserInfo ui";
        sql += " WHERE  1=1";
        sql += "   AND cm.userNo = ui.userNo";
        sql += "   AND cm.isDel = 'N'";
        sql += "   AND cm.kind = 'I'";
        sql += "   AND cm.targetNo = :itemNo";
        sql += " ORDER BY cm.regDate DESC";
        sql += " LIMIT " + commentCnt + "," + (commentCnt + 10);
        Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
                .addScalar("commentNo", StandardBasicTypes.INTEGER)
                .addScalar("userNo", StandardBasicTypes.INTEGER)
                .addScalar("targetNo", StandardBasicTypes.INTEGER)
                .addScalar("text", StandardBasicTypes.STRING)
                .addScalar("userName", StandardBasicTypes.STRING);
        query.setParameter("itemNo", itemParam.getItemNo());
        query.setResultTransformer(Transformers.aliasToBean(CommentParam.class));
        return (List<CommentParam>)query.list();
    }
    @Override
    public Integer itemCommentCount(ItemParam itemParam){
        String sql = "";
        sql += "SELECT COUNT(*) ";
        sql += "  FROM Comment";
        sql += " WHERE  1=1";
        sql += "   AND isDel = 'N'";
        sql += "   AND kind = 'I'";
        sql += "   AND targetNo = :itemNo";
        Query query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.setParameter("itemNo", itemParam.getItemNo());
        Long result = ((Number)query.uniqueResult()).longValue();
        return result.intValue();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
