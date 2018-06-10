package com.james.mall.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

    @Test
    public void isVerificationTime(){
        Assert.assertTrue(StringUtil.isVerificationTime("2018-06-08 09:36:51"));
    }
}
