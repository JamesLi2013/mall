package com.james.mall.util;


import okhttp3.*;

import java.io.IOException;

/**
 * ElasticSearch 工具类
 */
public class EsUtil {
    private static OkHttpClient client=new OkHttpClient();
    public static void main(String[] args){
        addMapping();
    }

    public static void addMapping(){
        StringBuilder sb=new StringBuilder(5000);
        //todo 更好的方法?
        sb.append("{\n" +
                "        \"properties\": {\n" +
                "            \"title\": {\n" +
                "                \"type\": \"text\",\n" +
                "                \"analyzer\": \"ik_max_word\",\n" +
                "                \"search_analyzer\": \"ik_max_word\"\n" +
                "            },\n" );

        sb.append("\"subTitle\": { \"type\": \"text\" },\n");
        sb.append(  "            \"keyword\": {\n" +
                "                \"type\": \"text\",\n" +
                "                \"analyzer\": \"ik_max_word\",\n" +
                "                \"search_analyzer\": \"ik_max_word\"\n" +
                "            },\n" );
        sb.append("\"minPrice\": { \"type\": \"double\" },\n");
        sb.append("\"saleCount\": { \"type\": \"integer\" },\n");
        sb.append(  "            \"majorImage\": {\n" +
                "                \"type\": \"text\",\n" +
                "                \"index\": \"false\"\n" +
                "            },\n" );
        sb.append("\"created\": { \"type\": \"long\" },\n");
        sb.append("\"updated\": { \"type\": \"long\" }\n");

        //end
        sb.append(
                "        }\n" +
                "    \n" +
                "}");
        OkHttpClient client=new OkHttpClient();
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json"),sb.toString());
        Request request = new Request.Builder()
                .url("http://119.29.227.192:9200/productindex/product/_mapping")
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static List<ProductDto> searchProduct(String keyword){
//        List<>
//    }
}
