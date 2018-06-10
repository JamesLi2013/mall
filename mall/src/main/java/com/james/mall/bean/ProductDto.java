package com.james.mall.bean;


import lombok.Data;

@Data
public class ProductDto {

    private long id;
    private String title;//产品标题
    private String subTitle;//卖点
    private String colors;//销售属性 颜色
    private String meals;//销售属性 套餐
    private long  price;//宝贝价格 price
    private long  minPrice;//最低标价 price
    private long  maxPrice;//最高标价 price
    private int  quantity;//宝贝数量 quantity (总库存,如果有多个sku,则sku各库存数量总和要相等)
    private int saleCount;//销量
    private String departurePlace;//发货地:departurePlace

    private String majorImage;// 主图 majorImage
    private int productStatus;//审核中1,预售2,销售中3,下架4,其它状态5(自己定义,不一定准确)
    private long sellerId;//卖家id sellerId
    private String sellerName;//卖家名称 sellerName
    private String sellerIcon;//卖家图标 sellerIcon
    private boolean selfSupport;//自营产品


}
