package com.james.mall.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.mall.bean.*;
import com.james.mall.service.ProductService;
import com.james.mall.service.PropKeyService;

import com.james.mall.service.PropValueService;
import com.james.mall.service.SkuService;
import com.james.mall.util.Pair;
import com.james.mall.util.ResponseUtil;
import com.james.mall.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * 导入tb product数据
 */
@RestController
public class ImportProductDataController {
    private Logger logger=Logger.getLogger(getClass().getName());

    @Autowired
    ProductService productService;

    @Autowired
    PropKeyService propKeyService;

    @Autowired
    PropValueService propValueService;
    


    @Autowired
    SkuService skuService;
    
    private PropKey colorPropKey;
    private PropKey mealPropKey;
    private List<PropValue> mealValues;
    private Iterable<PropKey> allPropKeys;
    private Iterable<PropValue> allPropValues;
    private HashMap<String, Long> allPropKeyMap;
    private HashMap<String, Long> allPropValueMap;

    @GetMapping("/product/propKey")
    @ResponseBody
    public BaseResponseBean<String> importPropKey() {
        ArrayList<PropKey> propKeys = new ArrayList<>();
        PropKey colorKey = new PropKey();
        PropKey mealKey = new PropKey();

        colorKey.setPName("颜色分类");
        mealKey.setPName("套餐类型");
        propKeys.add(colorKey);
        propKeys.add(mealKey);
        propKeyService.insertList(propKeys);
        return ResponseUtil.turnData("");
    }

    @GetMapping("/product/mealValue")
    @ResponseBody
    public BaseResponseBean<String> importMealValue() {
        PropKey mealPropKey = propKeyService.findByPName("套餐类型");
        List<PropValue> propValues = new ArrayList<>();
        PropValue standardValue = new PropValue();
        standardValue.setCreated(System.currentTimeMillis());
        standardValue.setPid(mealPropKey.getId());
        standardValue.setVName("官方标配");
        propValues.add(standardValue);

        String[] numbers = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
        for (String number : numbers) {
            PropValue propValue = new PropValue();
            propValue.setCreated(System.currentTimeMillis());
            propValue.setPid(mealPropKey.getId());
            propValue.setVName("套餐" + number);
            propValues.add(propValue);
        }
        propValueService.insertList(propValues);
        return ResponseUtil.turnData("");
    }

    @GetMapping("/product/import")
    @ResponseBody
    public BaseResponseBean<Product> importProductData() {
        colorPropKey = propKeyService.findByPName("颜色分类");
        mealPropKey = propKeyService.findByPName("套餐类型");
        mealValues = propValueService.findByPid(mealPropKey.getId());
        allPropKeys = propKeyService.findAll();
        allPropValues = propValueService.findAll();
        allPropKeyMap = new HashMap<>();
        allPropValueMap = new HashMap<>();
        for (PropKey propKey : allPropKeys) {
            allPropKeyMap.put(propKey.getPName(), propKey.getId());
        }
        for (PropValue propValue : allPropValues) {
            allPropValueMap.put(propValue.getVName(), propValue.getId());
        }

        HashMap<String, Long> mealMap = new HashMap<>();
        for (PropValue mealValue : mealValues) {
            mealMap.put(mealValue.getVName(), mealValue.getId());
        }

        String tbItemsStr = StringUtil.file2String("classpath:static/importdata/tb_items.json", true);
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, TbProduct.class);
        try {
            ArrayList<TbProduct> tbItems = mapper.readValue(tbItemsStr, javaType);
            ArrayList<Product> products = new ArrayList<>();
            for (TbProduct item : tbItems) {
                Product product = new Product();
                product.setTitle(item.getTitle());
                product.setSubTitle(item.getSubTitle());

                Pair<Long, Long> rangePricePair = getRangePrice(item.getBeforePrice());
                product.setMinPrice(rangePricePair.getFirst());
                product.setMaxPrice(rangePricePair.getSecond());

//                product.setQuantity();
                List<String> headThumbs = item.getHeadThumbs();
                StringBuilder headSb = new StringBuilder();
                for (int i = 0; i < headThumbs.size(); i++) {
                    headSb.append(headThumbs.get(i));
                    if (i != headThumbs.size() - 1) headSb.append(";");
                }
                product.setMultiImage(headSb.toString());
                if (headThumbs.size() > 0) product.setMajorImage(headThumbs.get(0));
                product.setDescPC(item.getDesc());
                product.setDescMobile(item.getUrl());

//                普通属性保存  版本类型: 中国大陆

                List<String> attributes = item.getAttributes();
                product.setProps(getAttributeIds(attributes));

//                颜色/套餐属性保存 TODO 服装是颜色+尺码,还有可能只有颜色或尺码.
//                抓取应该将dl[1]更改为dl[*],就避免硬编码meals.sku也得改
//              Selectable colors = page.getHtml().xpath("//*[@id=\"J_isku\"]/div/dl[1]/dd/ul");//颜色属性

                String colorKey = StringUtil.findFirstText(item.getColors(), "data-property=\\\"(.*?)\\\"");
                String afterColors = saveColors(item.getColors(),colorKey);
                String afterMeals = saveMeals(item.getMeals(),item.getColors(), colorKey,mealMap);

//                product.setQuantity();
                product.setColors(afterColors);
                product.setMeals(afterMeals);
                product.setCreated(System.currentTimeMillis());
                products.add(product);
//                Product saveProduct = productService.insert(product);
                productService.insert(product);
//                todo 是否有id?
                System.out.println("产品id:"+product.getId());

                saveSku(product,afterColors,afterMeals);

            }
//            productService.save(products);
            return ResponseUtil.turnData(products.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseUtil.turnData(null);
    }

   private void saveSku(Product saveProduct, String afterColors, String afterMeals) {
        List<Sku> skus = new ArrayList<>();
        String[] colorIds = afterColors.split(";");
        String[] mealIds = afterMeals.split(";");
        if (colorIds.length > 0 && mealIds.length > 0) {
            for (String colorId : colorIds) {
                for (String mealId : mealIds) {
                    Sku sku = new Sku();
                    sku.setQuantity(100);
                    sku.setColor(colorId);
                    sku.setMeal(mealId);
                    sku.setProductId(saveProduct.getId());
                    sku.setPrice(randomPrice(saveProduct.getMinPrice(), saveProduct.getMaxPrice()));
                    sku.setCreated(System.currentTimeMillis());
                    skus.add(sku);
                }
            }
        } else if (colorIds.length > 0) {
            for (String colorId : colorIds) {
                Sku sku = new Sku();
                sku.setQuantity(100);
                sku.setColor(colorId);
//                            sku.setMeal(mealId);
                sku.setProductId(saveProduct.getId());
                sku.setPrice(randomPrice(saveProduct.getMinPrice(), saveProduct.getMaxPrice()));
                sku.setCreated(System.currentTimeMillis());
                skus.add(sku);
            }
        } else if (mealIds.length > 0) {
            for (String mealId : mealIds) {
                Sku sku = new Sku();
                sku.setQuantity(100);
//                        sku.setColor(colorId);
                sku.setMeal(mealId);
                sku.setProductId(saveProduct.getId());
                sku.setPrice(randomPrice(saveProduct.getMinPrice(), saveProduct.getMaxPrice()));
                sku.setCreated(System.currentTimeMillis());
                skus.add(sku);
            }
        }
        if (skus.size() > 0) {
            skuService.insertList(skus);
//                    由于每次查sku,反正也是根据productId获取此产品的skus,就不保存了.
            int skuQuantity = 0;
            for (Sku sku : skus) {
                skuQuantity += sku.getQuantity();
            }
            Iterator<Sku> iterator = skus.iterator();
            Sku firstSku = iterator.next();
            saveProduct.setQuantity(skuQuantity);
            saveProduct.setMajorSku(firstSku.getId());
            productService.update(saveProduct);

        }
    }

    private String saveMeals(String mealsStr, String colorsStr, String colorKey, HashMap<String, Long> mealMap) {
        List<String> meals = new ArrayList<>();
        StringBuilder mealSb = new StringBuilder();

        if (!StringUtil.isEmpty(mealsStr)) {
            meals = StringUtil.findAllText(mealsStr, "<span>(.*?)</span>");
        } else if ("套餐类型".equals(colorKey)) {
            meals = StringUtil.findAllText(colorsStr, "<span>(.*?)</span>");
        }
        for (String meal : meals) {
//            Long vId = mealMap.get(meal);
//            if (vId != null) {
//                mealSb.append(mealpropKey.getId()).append(":")
//                        .append(vId).append(";");
//            }
              mealSb.append("套餐类型").append(":")
                        .append(meal).append(";");
        }
        return mealSb.toString();
    }

    private String saveColors(String colorsStr, String colorKey) {

        StringBuilder colorSb = new StringBuilder();
        if ("颜色分类".equals(colorKey)) {
            List<String> colors = StringUtil.findAllText(colorsStr, "<span>(.*?)</span>");
            for (String color : colors) {
                colorSb.append(colorKey).append(":")
                        .append(color).append(";");
            }
         /*   ArrayList<CustomPropValue> customPropValues = new ArrayList<>();
            for (String color : colors) {
                CustomPropValue cpv = new CustomPropValue();
                cpv.setCreated(System.currentTimeMillis());
                cpv.setPid(colorpropKey.getId());
                cpv.setVName(color);
                customPropValues.add(cpv);
            }
            Iterable<CustomPropValue> saveColorValues = custompropValueService.save(customPropValues);
            for (CustomPropValue value : saveColorValues) {
                colorSb.append(colorpropKey.getId()).append(":")
                        .append(value.getId()).append(";");
            }*/
        }
        return colorSb.toString();
    }

    //保存普通属性,非销售属性
    private String getAttributeIds(List<String> attributes) {
        StringBuilder attributeIds = new StringBuilder(300);
        for (String attribute : attributes) {
            String[] splitAttribute = attribute.split(":");
            Long attrKeyId = allPropKeyMap.get(splitAttribute[0].trim());

            if (attrKeyId == null || attrKeyId == 0) {//没有则把属性值保存到数据库
                PropKey addKey = new PropKey();
                addKey.setPName(splitAttribute[0].trim());
                propKeyService.insert(addKey);
//                propkey id??
                attrKeyId=addKey.getId();
                allPropKeyMap.put(addKey.getPName(),addKey.getId());
            }else  if(attrKeyId== colorPropKey.getId()||attrKeyId== mealPropKey.getId()){
                continue;
            }

            if (splitAttribute.length == 1) {
                attributeIds.append(attrKeyId).append(":;");
            } else if (splitAttribute.length == 2) {
                // 额外处理此类情况,一对多:适用场景: 家庭影音 学生 高清游戏 尊贵旗舰 家庭娱乐
                String[] allAttrValues = splitAttribute[1].trim().split(" ");
                for (String value : allAttrValues) {
                    Long attrValueId = allPropValueMap.get(value);
                    if(attrValueId==null||attrValueId==0){
                        PropValue addValue=new PropValue();
                        addValue.setCreated(System.currentTimeMillis());
                        addValue.setPid(attrKeyId);
                        addValue.setVName(value);
                        propValueService.insert(addValue);
//                        id
                        attrValueId=addValue.getId();
                        allPropValueMap.put(addValue.getVName(),addValue.getId());
                    }
                    attributeIds.append(attrKeyId).append(":")
                            .append(attrValueId).append(";");
                }

            }
        }

        return attributeIds.toString();
    }


    public Pair<Long, Long> getRangePrice(String rangePrice) {
        try {
            String[] prices = rangePrice.split(" - ");
            return Pair.of((long)(Double.parseDouble(prices[0])*100), (long)(Double.parseDouble(prices[1])*100));
        } catch (RuntimeException e) {
            return Pair.of(0L, 0L);
        }
    }

    public long randomPrice(long minPrice, long maxPrice) {
        Random random = new Random();
        double price = minPrice + random.nextDouble() * (maxPrice - minPrice);
        return (long)price;
    }

    @RequestMapping("/propkey/test")
    public String getTest(){
        PropKey propKey=new PropKey();
        propKey.setPName("pp1");
        propKeyService.insert(propKey);
        logger.info("pid:"+propKey.getId());
        return "pid:"+propKey.getId();
    }
}
