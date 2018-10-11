package com.alibaba.ocr.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.api.gateway.demo.constant.HttpHeader;
import com.aliyun.api.gateway.demo.util.HttpUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用api网关，通过app_key进行身份认证
 */
public class APPKeyDemo {

    //自定义参与签名Header前缀（可选,默认只有"X-Ca-"开头的参与到Header签名）
    //private final static List<String> CUSTOM_HEADERS_TO_SIGN_PREFIX = new ArrayList<String>();

    /**
     * 获取参数的json对象
     */
    //public static JSONObject getParam(int type, String dataValue) {
    //    JSONObject obj = new JSONObject();
    //    try {
    //        obj.put("dataType", type);
    //        obj.put("dataValue", dataValue);
    //    } catch (JSONException e) {
    //        e.printStackTrace();
    //    }
    //   return obj;
    //}
    public static void main(String[] args) throws Exception {
        //请求path
        String host = "http://textread.xiaohuaerai.com";
        //String host = "http://textread.market.alicloudapi.com";

        String path = "/textread";
        String APP_CODE = "25e2ddd33d73446f9e9a11deaa7935be";
        String method = "POST";

        String imgFile = "/Users/fenghouzhi/Desktop/test.png";

        //String APP_KEY = "25104835";
        //String APP_SECRET = "af0d81c0641fbb050eedeb83768abd37";
        //Boolean is_old_format = true; //如果文档的输入中含有inputs字段，设置为True， 否则设置为False
        //Boolean is_old_format = false;

        //请根据线上文档修改configure字段
        //JSONObject configObj = new JSONObject();
        //configObj.put("side", "face");
        //configObj.put("min_size", 16);
        //configObj.put("output_prob", true);
        //String config_str = configObj.toString();
        //System.out.println("param json info:    " + config_str);

        /**
         * 对图像进行base64编码
         */
        String imgBase64;
        try {
            File file = new File(imgFile);
            System.out.println("param json info:    " + file.getName() + "    " + file.length());
            byte[] content = new byte[(int) file.length()];
            FileInputStream finputstream = new FileInputStream(file);
            finputstream.read(content);
            finputstream.close();
            imgBase64 = new String(Base64.encodeBase64(content));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + APP_CODE);
        //根据API的要求，定义相对应的Content-Type
        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded;charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("src", imgBase64);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            //获取response的body
            String text = EntityUtils.toString(response.getEntity());
            System.out.println("response info:  " + text);
            JSONObject jsonObject = JSON.parseObject(text);
            String ss = jsonObject.get("msg").toString();
            System.out.println(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 拼装请求body的json字符串
//        JSONObject requestObj = new JSONObject();
//        requestObj.put("src", "图片base64编码");
//        try {
//            if (is_old_format) {
//                JSONObject obj = new JSONObject();
//                obj.put("image", getParam(50, imgBase64));
//                if (config_str.length() > 0) {
//                    obj.put("configure", getParam(50, config_str));
//                }
//                JSONArray inputArray = new JSONArray();
//                inputArray.add(obj);
//                requestObj.put("inputs", inputArray);
//            } else {
//                requestObj.put("image", imgBase64);
//                if (config_str.length() > 0) {
//                    requestObj.put("configure", config_str);
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        //Body内容
        //String body = requestObj.toString();

//        Map<String, String> headers = new HashMap<String, String>();
//        //（必填）根据期望的Response内容类型设置
//        headers.put(HttpHeader.HTTP_HEADER_ACCEPT, "application/json");
//        //（可选）Body MD5,服务端会校验Body内容是否被篡改,建议Body非Form表单时添加此Header
//        headers.put(HttpHeader.HTTP_HEADER_CONTENT_MD5, MessageDigestUtil.base64AndMD5(body));
//        //（POST/PUT请求必选）请求Body内容格式
//        headers.put(HttpHeader.HTTP_HEADER_CONTENT_TYPE, ContentType.CONTENT_TYPE_TEXT);

//        Request request = new Request(Method.POST_STRING, host, path, APP_KEY, APP_SECRET, Constants.DEFAULT_TIMEOUT);
//        request.setHeaders(headers);
//        request.setSignHeaderPrefixList(CUSTOM_HEADERS_TO_SIGN_PREFIX);
//        request.setStringBody(body);
//
//        //调用服务端
//        Response response = Client.execute(request);
//        if (response.getStatusCode() != 200) {
//            System.out.println("Http code: " + response.getStatusCode());
//            System.out.println("Http header error: " + response.getHeader("X-Ca-Error-Message"));
//            System.out.println("Http body error msg: " + response.getBody());
//            return;
//        }
//        String res = response.getBody();
//        JSONObject res_obj = JSON.parseObject(res);
//        if (is_old_format) {
//            JSONArray outputArray = res_obj.getJSONArray("outputs");
//            String output = outputArray.getJSONObject(0).getJSONObject("outputValue").getString("dataValue");
//            JSONObject out = JSON.parseObject(output);
//            System.out.println(out.toJSONString());
//        } else {
//            System.out.println(res_obj.toJSONString());
//        }

    }
}
