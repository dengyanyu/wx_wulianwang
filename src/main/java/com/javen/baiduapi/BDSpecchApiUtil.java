package com.javen.baiduapi;

import java.io.IOException;

import org.json.JSONObject;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;

public class BDSpecchApiUtil {
    //设置APPID/AK/SK
    public static final String APP_ID = "11810673";
    public static final String API_KEY = "hzak3QUIjpoA6ZxDoVCanzQT";
    public static final String SECRET_KEY = "4sbOgjAGfYvSpwAUtWNnCWhykiVjBwgo";
    private static  AipSpeech client;
    public static synchronized AipSpeech getInstance() {
    	if (client==null) {
			client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
		}
    	return client;
	}
    public static void main(String[] args) {
        // 初始化一个AipSpeech
        AipSpeech client =getInstance();
//        // 调用接口
//        JSONObject res = client.asr("C:\\Users\\wc\\git\\weixin_guide\\src\\main\\java\\test\\PFRO2UD8o5_OLMb47RfNnEUTFLMpGlV-ojrhKqAaUDqElUlU3Y43ervMdTqRKeXk", "amr", 8000, null);
////        System.out.println(res.toString(2));
//        JSONArray rs = (JSONArray) res.get("result");
//        System.out.println(rs.get(0));
        
        
        // 调用接口
        TtsResponse res = client.synthesis("你好百度  hello中国", "zh", 1, null);
        byte[] data = res.getData();
        JSONObject res1 = res.getResult();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, "C:\\Users\\wc\\git\\weixin_guide\\src\\main\\java\\test\\output.mp3");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (res1 != null) {
            System.out.println(res1.toString(2));
        }
        
        
    }
}