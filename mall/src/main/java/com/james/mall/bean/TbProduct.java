package com.james.mall.bean;

import java.util.List;

/**
 * 非完整数据,仅用于抓取tb商品详情部分数据.
 */
public class TbProduct {
    private String title;
    private String subTitle;
    private String beforePrice;
    private String promoPrice;
    private String colors;
    private String meals;
    private List<String> headThumbs;
    private List<String> attributes;
    private String desc;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getBeforePrice() {
        return beforePrice;
    }

    public void setBeforePrice(String beforePrice) {
        this.beforePrice = beforePrice;
    }

    public String getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(String promoPrice) {
        this.promoPrice = promoPrice;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getMeals() {
        return meals;
    }

    public void setMeals(String meals) {
        this.meals = meals;
    }

    public List<String> getHeadThumbs() {
        return headThumbs;
    }

    public void setHeadThumbs(List<String> headThumbs) {
        this.headThumbs = headThumbs;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TbProduct{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", beforePrice='" + beforePrice + '\'' +
                ", promoPrice='" + promoPrice + '\'' +
                ", colors='" + colors + '\'' +
                ", meals='" + meals + '\'' +
                ", headThumbs=" + headThumbs +
                ", attributes=" + attributes +
                ", desc='" + desc + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
