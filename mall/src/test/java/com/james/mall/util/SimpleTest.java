package com.james.mall.util;


import org.junit.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class SimpleTest {
    @Test
    public void copyDirTest() {

        CopyFile.copyDir("E:\\openSource\\BackEnd\\gs-rest-service\\complete", "e:/complete");

    }

    @Test
    public void zipDirTest() {
        try {
            String projectName = "mall";
//            String sourceDirPath = "E:\\openSource\\BackEnd\\gs-rest-service\\complete";
            String sourceDirPath = "E:\\openSource\\BackEnd\\" + projectName;
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
//            String targetZip = "e:/testWork/complete" + sdf.format(new Date()) + ".zip";
            String targetZip = "e:/testWork/" + projectName + sdf.format(new Date()) + ".zip";
            CopyFile.packExclude(sourceDirPath, targetZip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void zipAndroidTest() {
        try {
//            String sourceDirPath = "E:\\openSource\\Improve";
            String sourceDirPath = "E:\\openSource\\2017\\NewTemp";
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
            String targetZip = "e:/testWork/NewTemp" + sdf.format(new Date()) + ".zip";
            CopyFile.packExclude(sourceDirPath, targetZip);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
