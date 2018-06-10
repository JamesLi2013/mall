package com.james.mall.trynew;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.Test;

import java.nio.charset.Charset;

public class BloomFilterTest {

    @Test
    public void test(){
        BloomFilter<String> bloomFilter=BloomFilter.create(
                Funnels.stringFunnel(Charset.forName("utf-8")),
                500,
                0.01);
        for (int i = 0; i < 1000; i++) {
            bloomFilter.put("hello"+i*11);
        }
        System.out.println(bloomFilter.mightContain("hello"+19*11));
        System.out.println(bloomFilter.mightContain("hello"+267*11));
        System.out.println(bloomFilter.mightContain("hello"+399*11));
    }
}
