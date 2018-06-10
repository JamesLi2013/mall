package com.james.mall.util;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Random;

/**
 * 单机简单生成随机数,然后再排序,用作订单id,防止被人推测出实际订单数量.
 * 生产环境需要将本批id,范围,前缀号(4位)保存起来.
 *
 * 淘宝订单如下
 * //        120891383243224279
 //        120891383244224279   //a账号

 //        126652259257 623703//另外的id号
 Leaf——美团点评分布式ID生成系统
 https://tech.meituan.com/MT_Leaf.html
 */
public class RandomIdUtil {
    private static LinkedList<Long> preOrderIdList = new LinkedList<>();
    private static int randomNumberOrigin = 112376;
    private static int randomNumberBound = 999999;
    private static int randomIndex = randomNumberOrigin;
    private static int randomCount = 1000;
    private static int prefix = 1122;


    /**
     * 获取第一个id,并将其移除
     */
    public static long nextOrderId() {
        if (preOrderIdList.size() == 0) {
            preOrderIdList = createOrderIdPoorSimple();
        }
        return preOrderIdList.removeFirst();
    }

    public static LinkedList<Long> createOrderIdPoorSimple() {
        Properties properties = PropertiesUtil.get("order");
        if (!StringUtil.isEmpty(properties.getProperty("prefix"))) {
            prefix = Integer.parseInt(properties.getProperty("prefix"));
        }
        if (!StringUtil.isEmpty(properties.getProperty("randomIndex"))) {
            randomIndex = Integer.parseInt(properties.getProperty("randomIndex"));
        }
        if (!StringUtil.isEmpty(properties.getProperty("date"))
                && !StringUtil.tranTime2Date(System.currentTimeMillis(), "yyyyMMdd")
                .equals(properties.getProperty("date"))) {
            prefix++;
        }
        int origin = randomIndex;
        int bound = origin + 50000;
        if (bound > randomNumberBound) {
            RandomIdUtil.prefix++;
            randomIndex = randomNumberOrigin;
            origin = randomIndex;
            bound = origin + 50000;
        }else{
            randomIndex = bound;
        }
        properties.setProperty("prefix", RandomIdUtil.prefix + "");
        properties.setProperty("randomIndex", randomIndex + "");
        properties.setProperty("date", StringUtil.tranTime2Date(System.currentTimeMillis(), "yyyyMMdd"));
        PropertiesUtil.store("order", properties);
        preOrderIdList = new LinkedList<>();
        return createOrderIdPoor((long) RandomIdUtil.prefix * 1000000L, origin, bound, randomCount);
    }

    public static LinkedList<Long> createOrderIdPoor(long prefix, int randomNumberOrigin,
                                                     int randomNumberBound, int randomCount) {
        LinkedList<Long> ids = new LinkedList<>();
        int[] randomResult = random(randomNumberOrigin, randomNumberBound, randomCount);
        for (int id : randomResult) {
            ids.add(prefix + (long) id);
        }
        return ids;
    }

    /**
     * 返回指定范围指定个数升序的随机数
     *
     * @param randomNumberOrigin 最小值范围
     * @param randomNumberBound  最大值范围
     * @param randomCount        随机个数
     * @return
     */
    private static int[] random(int randomNumberOrigin, int randomNumberBound, int randomCount) {
        final int[] numbers = new Random().ints(randomNumberOrigin, randomNumberBound)
                .distinct().limit(randomCount).sorted().toArray();
        return numbers;
    }
}
