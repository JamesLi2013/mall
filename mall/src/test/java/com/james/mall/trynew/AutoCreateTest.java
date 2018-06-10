package com.james.mall.trynew;

import com.james.mall.bean.Friend;
import com.james.mall.bean.PropKey;

import javax.xml.bind.annotation.XmlElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class AutoCreateTest {

    public static void main(String[] args) {
        try {
//            Class<?> obj = Class.forName("com.withiter.test.Person");
            Class<?> obj = Class.forName("com.james.mall.bean.Friend");
            Field[] f = obj.getDeclaredFields();
            for(Field field : f){
                field.setAccessible(true);
                System.out.println(field.getName()+":"+field.getType().getName()+":");
//                Annotation[] annotations = field.getAnnotations();
//                for (Annotation annotation : annotations) {
//                    System.out.println(annotation.annotationType().getName());
//                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String tableName = "good";
        String commentTableName = "点赞表";
        sb.append("DROP TABLE IF EXISTS ").append(tableName).append(";\n")
                .append("CREATE TABLE ").append(tableName).append(" (\n" +
                "  id      BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT\n" +
                "  COMMENT '" + commentTableName + " id',\n")
                .append("  p_name  VARCHAR(255) COMMENT '属性key名称',\n")
                .append(
                        "  created DATETIME                    DEFAULT CURRENT_TIMESTAMP\n" +
                                "  COMMENT '创建时间',\n" +
                                "  updated DATETIME                    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP\n" +
                                "  COMMENT '更新时间'\n" +
                                ")\n" +
                                "  ENGINE = InnoDB\n" +
                                "  DEFAULT CHARSET = utf8\n" +
                                "  COMMENT '" + commentTableName + "';");
//        System.out.println(sb.toString());
    }
}
