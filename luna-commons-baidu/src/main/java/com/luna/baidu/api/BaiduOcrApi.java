package com.luna.baidu.api;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.luna.common.dto.WordDTO;
import com.luna.common.http.HttpUtils;
import com.luna.common.http.HttpUtilsConstant;
import com.luna.common.utils.Base64Util;
import com.luna.common.utils.img.ImageUtils;
import com.luna.common.utils.text.CharsetKit;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/**
 * @author Luna@win10
 * @date 2020/5/3 9:58
 */
public class BaiduOcrApi {

    /**
     * 百度云OCR识别
     *
     * @param image
     * @return
     * @throws IOException
     */
    public static List<WordDTO> baiDuOcr(String key, String image) {
        HashMap<String, String> map = Maps.newHashMap();
        if (HttpUtils.isNetUrl(image)) {
            map.put("url", image);
        } else if (Base64Util.isBase64(image)) {
            map.put("image", image);
        } else {
            map.put("image", Base64Util.encodeBase64String(ImageUtils.getBytes(image)));
        }
        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiContent.HOST, BaiduApiContent.OCR,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.X_WWW_FORM_URLENCODED),
            ImmutableMap.of("access_token", key), map);
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        return JSON.parseArray(JSON.parseObject(s).get("words_result").toString(), WordDTO.class);
    }

    /**
     * 百度文字识别Ocr 附带位置返回 高精度版
     *
     * @param image
     * @return
     * @throws IOException
     */
    public static List<WordDTO> baiduOcrAndAddress(String key, String image) throws UnsupportedEncodingException {
        if (HttpUtils.isNetUrl(image)) {
            image = "url=" + URLEncoder.encode(image, CharsetKit.UTF_8);
        } else if (Base64Util.isBase64(image)) {
            image = "image=" + URLEncoder.encode(image, CharsetKit.UTF_8);
        } else {
            image = "image="
                + URLEncoder.encode(Base64Util.encodeBase64String(ImageUtils.getBytes(image)), CharsetKit.UTF_8);
        }
        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiContent.HOST, BaiduApiContent.OCR_ADDRESS,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.X_WWW_FORM_URLENCODED),
            ImmutableMap.of("access_token", key), image);
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        return JSON.parseArray(JSON.parseObject(s).get("words_result").toString(), WordDTO.class);
    }

    /**
     * 百度文字Ocr 附带位置返回普通版
     *
     * @param image
     * @return
     * @throws IOException
     */
    public static List<WordDTO> baiduOcrAndAddressNormal(String key, String image) throws UnsupportedEncodingException {
        if (HttpUtils.isNetUrl(image)) {
            image = "url=" + URLEncoder.encode(image, CharsetKit.UTF_8);
        } else if (Base64Util.isBase64(image)) {
            image = "image=" + URLEncoder.encode(image, CharsetKit.UTF_8);
        } else {
            image = "image="
                + URLEncoder.encode(Base64Util.encodeBase64String(ImageUtils.getBytes(image)), CharsetKit.UTF_8);
        }
        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiContent.HOST, BaiduApiContent.OCR_ADDRESS_NORMAL,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.X_WWW_FORM_URLENCODED),
            ImmutableMap.of("access_token", key), image);
        String s = HttpUtils.checkResponseAndGetResult(httpResponse, true);
        return JSON.parseArray(JSON.parseObject(s).get("words_result").toString(), WordDTO.class);
    }

}
