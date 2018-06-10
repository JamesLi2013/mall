package com.james.mall.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import sun.misc.BASE64Encoder;
import sun.security.action.GetPropertyAction;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static final long VALID_VERIFICATION = 5 * 60 * 1000; //验证码有效期暂定5分钟

    public static boolean isEmpty(String text) {
        if (text == null || "".equals(text)) return true;
        return false;
    }

    public static boolean isPhone(String phone) {
        Pattern p = Pattern.compile("^[1][35789]\\d{9}$");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    public static boolean isVerificationTime(long lastTime) {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis - lastTime < VALID_VERIFICATION;
    }

    public static boolean isVerificationTime(String lastTime) {
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date lastDate = sdf.parse(lastTime);
            return currentTimeMillis - lastDate.getTime() < VALID_VERIFICATION;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static String getMd5(String text) {
        byte[] mingData = text.getBytes();
        BigInteger md5Data = null;
        try {
            md5Data = new BigInteger(1, encryptMD5(mingData));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        String md5Str = md5Data.toString(16);
        if (md5Str.length() < 32) {
            md5Str = 0 + md5Str;
        }
        return md5Str;
    }

    public static byte[] encryptMD5(byte[] data) throws NoSuchAlgorithmException {

        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();

    }

    public static Pair<String, String> encryptPassword(String password) {
        String salt = UUID.randomUUID().toString().replace("-", "");
        String encryptedPassword = getMd5(password + salt);
        return Pair.of(encryptedPassword, salt);
    }

    public static boolean verifyPassword(String password, String encyptedPassword, String salt) {
        return encyptedPassword.equals(getMd5(password + salt));
    }

    /**
     * InputStream -> String
     */
    public static String tranInputStreamToString(InputStream is) {
        if (is == null) return "";
        final StringBuilder sb = new StringBuilder();
        try {
            final BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String data;
            while ((data = br.readLine()) != null) {
                sb.append(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }

    /**
     * 获取今天的文件夹名称 ,如/2018/0102/
     */
    public static String getTodayDateDir() {
        SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MMdd/");
        return sdf.format(new Date());
    }


    public static String tranTime2Date(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
        return sdf.format(new Date(time));
    }

    public static String toSqlDate(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(time));
    }

    public static String tranTime2Date(long time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(time));
    }


    /**
     * 去除.0格式的日期时间字符串
     *
     * @param dateTime "2018-05-15 10:13:20.0"
     * @return "2018-05-15 10:13:20"
     */
    public static String replaceDotZero(String dateTime) {
        if (dateTime != null && dateTime.endsWith(".0")) {
            dateTime = dateTime.replace(".0", "");
        }
        return dateTime;
    }

    /**
     * 获取自定义属性 username的值
     */
    public static String getUsernameAttributeValue(HttpServletRequest request) {
        return (String) request.getAttribute("username");
    }

    public static String findFirstText(String text, String reg) {
        String result = null;
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(text);
        if (m.find()) {
            result = m.group(1);
        }
        return result;
    }

    public static List<String> findAllText(String text, String reg) {
        List<String> result = new ArrayList<>();
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(text);
        while (m.find()) {
            result.add(m.group(1));
        }
        return result;
    }

    public static String file2String(String fileName, boolean fromResource) {
        if (fromResource) {
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
            Resource resource = ctx.getResource(fileName);
            try {
                fileName = resource.getFile().getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }

        return StringUtil.file2String(fileName);
    }

    public static String file2String(String fileName) {
        File file = new File(fileName);
        String csn = AccessController.doPrivileged(
                new GetPropertyAction("file.encoding"));
//        System.out.println("file.encoding:" + csn);
        //        Charset cs = lookup(csn);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    new FileInputStream(new File(fileName)), Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder(1024 * 10);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            inputStreamReader.close();
            br.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取2位小数总价
     */
    public static double getTotalPriceDecimal(double price, int quantity) {
        int priceInt = (int) (price * 100);
        int totalPriceInt = priceInt * quantity;
        double totalPriceDouble = totalPriceInt / 100d;
        String totalPriceStr = new DecimalFormat("#.00")
                .format(totalPriceDouble);
        totalPriceDouble = Double.parseDouble(totalPriceStr);
        return totalPriceDouble;

    }

    /**
     * 获取2位小数
     */
    public static double formatDecimal(double price) {
        String priceStr = new DecimalFormat("#.00").format(price);
        return Double.parseDouble(priceStr);

    }

    public static String encodeBase64(String str) {
        sun.misc.BASE64Encoder base64Encode = new BASE64Encoder();
        return base64Encode.encode(str.getBytes());
    }


    /**
     * @param unicodeStr \ u开头的uf-8编码 如"\"country\":\"\\u7f8e\\u56fd\""
     * @return 正常的文本
     */
    public static String decodeUnicode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        int length = unicodeStr.length();
        for (int i = 0; i < length; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < length - 5)
                        && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr
                        .charAt(i + 1) == 'U')))
                    try {
                        sb.append((char) Integer.parseInt(
                                unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        sb.append(unicodeStr.charAt(i));
                    }
                else
                    sb.append(unicodeStr.charAt(i));
            } else {
                sb.append(unicodeStr.charAt(i));
            }
        }
        return sb.toString();
    }
}
