package com.james.mall.bean;


import lombok.Data;

@Data
public class Product {

    private long id;
    private String title;//产品标题
    private String subTitle;//卖点
    private String keyword;
    //系统自动保存几个搜索属性作为搜索,比如:品牌,衣服中的颜色 ,尺码,电脑中的内存和处理器

    private String props;//所有属性集

    private String qualification;//商品资质 qualification (可选,图标)
    private String colors;//销售属性 颜色
    private String meals;//销售属性 套餐
//    private String saleProps;//宝贝规格 saleProps (销售属性,存sku表,如颜色,套餐)
    private long  price;//宝贝价格 price
    private long  minPrice;//最低标价 price
    private long  maxPrice;//最高标价 price
    private int  quantity;//宝贝数量 quantity (总库存,如果有多个sku,则sku各库存数量总和要相等)
    private int saleCount;//销量
    private String departurePlace;//发货地:departurePlace

    private String multiImage;//宝贝图片 multiImage 多图 (1-5,其中一张为主图)
    private String majorImage;// 主图 majorImage
    private String descPC;// 宝贝在PC的描述 descForPC
    private String descMobile;// 宝贝在手机的描述 descForMobile
    private String deliverTemplate;//物流模板 deliverTemplate
    private String afterSale;//  售后服务 afterSale
    private int productStatus;//审核中1,预售2,销售中3,下架4,其它状态5(自己定义,不一定准确)
    private long startTime;//开始销售时间 startTime

    private String skuIds;//包含的sku
    private long majorSku;//默认sku
    private long sellerId;//卖家id sellerId
    private String sellerName;//卖家名称 sellerName
    private String sellerIcon;//卖家图标 sellerIcon
    private boolean selfSupport;//自营产品

    private long created;
    private long updated;

}
