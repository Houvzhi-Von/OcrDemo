package com.jinjia.ordersys.sign;

import com.jinjia.ordersys.util.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Param:
 * @return:
 * @Author: fenghouzhi
 * @Date: 3:56 PM 2018/10/10
 */
@Slf4j
public class Test {

    public static void main(String[] args) {

        String imgFile = "/Users/fenghouzhi/Desktop/test.png";

        /**
         * 对图像进行base64编码
         */
        String imgBase64;
        String contentSize;
        String sign;

        try {

            sign = Sign.appSign(Constats.APP_ID, Constats.SECRET_ID, Constats.SECRET_KEY, null);

            File file = new File(imgFile);
            contentSize = file.length() + "";
            log.info("param json info:    " + file.getName() + "    " + file.length());

            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(Base64.encodeBase64(content));
            log.info("base64 info:  " + imgBase64);


            Map<String, Object> paramMap = new HashMap<>(3);
            paramMap.put("appid", Constats.APP_ID_STR);
            paramMap.put("image", imgBase64);
            //paramMap.put("url", "https://upload-images.jianshu.io/upload_images/2534116-c7b4de4aa60791e8.jpeg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");

            String responseData = HttpRequestUtil.httpPostOcr(Constats.URL, sign, paramMap);
            log.info("response info:    " + responseData);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
