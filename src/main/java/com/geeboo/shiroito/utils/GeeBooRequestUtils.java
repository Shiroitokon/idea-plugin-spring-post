package com.geeboo.shiroito.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.geeboo.shiroito.storage.GeeBooPostStorage;
import com.geeboo.shiroito.storage.GeeBooPostStorageService;
import com.geeboo.shiroito.storage.StorageKey;
import okhttp3.*;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author hongyq (修改代码请联系开发者)
 * @date 2020-11-11 17:20
 */
public class GeeBooRequestUtils {


    private static OkHttpClient client = new OkHttpClient().newBuilder()
            .build();

    public static String post(Map<String, String> paramFormMap, String evm, String userTye, String uri) {
        GeeBooPostStorage state = GeeBooPostStorageService.getInstance().getState();
        Map<String, String> postSetting = state.getPostSetting().get(evm);
        Map<String, String> tokenMap = state.getTokenMap();
        //获取密钥
        String secret = postSetting.get(StorageKey.SECRET);
        //获取域名
        String domain = postSetting.get(StorageKey.DOMAIN);
        //获取token
        String token = tokenMap.get(StorageKey.getTokenKey(userTye, evm));


        if(token == null || token.equals("")) {
            try {
                token = userLoginAndSave(evm, userTye);
            } catch (Exception e) {
                return e.getMessage();
            }
        }


        String url = domain + uri;

        //参数添加时间戳和随机字符串
        paramFormMap.put("nonce", new Random().nextInt(10000000) + "");
        paramFormMap.put("timestamp", System.currentTimeMillis() + "");

        String sign = SignUtil.sign(paramFormMap, secret);
        paramFormMap.put("sign", sign);

        FormBody.Builder builder = new FormBody.Builder();
        for(Map.Entry<String, String> entry: paramFormMap.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        Response response;
        try {
            response = post(builder.build(), token, url);
        } catch (IOException e) {
            return e.getMessage();
        }
        try {

            if(response.code() == 401) {
               token = userLoginAndSave(evm, userTye);
               response = post(builder.build(), token, url);
            }
            String string = response.body().string();
            try {
                JSONObject jsonObject = JSON.parseObject(string);
                return JSONObject.toJSONString(jsonObject,true);
            } catch (Exception e) {
                return string;
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }


    private static Response post(RequestBody requestBody, String token, String url) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Authorization", token)
                .post(requestBody)
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response;
    }

    private static String userLoginAndSave(String evm, String userTye) throws Exception {

        String token = "";

        if(userTye.equals("APP")) {

            token = appUserLogin(evm);

        }

        if(userTye.equals("OMS")) {
            token = omsUserLogin(evm);
        }

        if(token.equals("")) {
            return "";
        }

        GeeBooPostStorage state = GeeBooPostStorageService.getInstance().getState();
        state.getTokenMap().put(StorageKey.getTokenKey(userTye, evm), token);
        GeeBooPostStorageService.getInstance().loadState(state);
        return token;
    }

    private static String omsUserLogin(String evm) throws Exception {

        GeeBooPostStorage state = GeeBooPostStorageService.getInstance().getState();
        Map<String, String> postSetting = state.getPostSetting().get(evm);
        //获取密钥
        String secret = postSetting.get(StorageKey.SECRET);
        //获取域名
        String domain = postSetting.get(StorageKey.DOMAIN);
        //获取oms用户名
        String omsUser = postSetting.get(StorageKey.OMS_USER);
        //获取oms密码
        String omsPass = postSetting.get(StorageKey.OMS_PASS);

        String url = domain + "/auth/oauth/token";


        final HashMap<String, String> params = new HashMap<>();

        params.put("username", "OMS@" + omsUser);
        params.put("grant_type", "password");
        params.put("password", passwordEncrypt(omsPass));
        params.put("scope", "server");
        params.put("nonce", new Random().nextInt(10000000) + "");
        params.put("timestamp", System.currentTimeMillis() + "");

        String sign = SignUtil.sign(params, secret);
        params.put("sign", sign);

        FormBody.Builder builder = new FormBody.Builder();
        for(Map.Entry<String, String> entry: params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        Request request = new Request.Builder()
                .addHeader("Authorization", "Basic Z2JjbG91ZDpnYmNsb3Vk")
                .post(builder.build())
                .url(url)
                .build();


            Response response = client.newCall(request).execute();
            String string = response.body().string();
            try {
                JSONObject jsonObject = JSON.parseObject(string);
                String access_token = jsonObject.getString("access_token");
                String token_type = jsonObject.getString("token_type");
                if(access_token == null || token_type == null) {
                    throw new Exception(string);
                }
                return token_type + " " + access_token;
            } catch (Exception e) {
                throw new Exception(string);
            }

        }

    private static String appUserLogin(String evm) throws Exception {
        GeeBooPostStorage state = GeeBooPostStorageService.getInstance().getState();
        Map<String, String> postSetting = state.getPostSetting().get(evm);
        //获取密钥
        String secret = postSetting.get(StorageKey.SECRET);
        //获取域名
        String domain = postSetting.get(StorageKey.DOMAIN);
        //获取oms用户名
        String appUser = postSetting.get(StorageKey.APP_USER);
        //获取oms密码
        String appPass = postSetting.get(StorageKey.APP_PASS);

        String url = domain + "/auth/oauth/token";


        final HashMap<String, String> params = new HashMap<>();

        params.put("username", "APP@+86 " + appUser);
        params.put("grant_type", "password");
        params.put("password", passwordEncrypt(appPass));
        params.put("scope", "app");
        params.put("nonce", new Random().nextInt(10000000) + "");
        params.put("timestamp", System.currentTimeMillis() + "");

        String sign = SignUtil.sign(params, secret);
        params.put("sign", sign);

        FormBody.Builder builder = new FormBody.Builder();
        for(Map.Entry<String, String> entry: params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        Request request = new Request.Builder()
                .addHeader("Authorization", "Basic Z2JjbG91ZDpnYmNsb3Vk")
                .post(builder.build())
                .url(url)
                .build();


            Response response = client.newCall(request).execute();

            String string = response.body().string();
            try {
                JSONObject jsonObject = JSON.parseObject(string);
                String access_token = jsonObject.getString("access_token");
                String token_type = jsonObject.getString("token_type");
                if(access_token == null || token_type == null) {
                    throw new Exception(string);
                }
                return token_type + " " + access_token;
            } catch (Exception e) {
                throw new Exception(string);
            }

        }

    private static String passwordEncrypt(String password) {
        String key = "gbcloudgbcloudgb";
        try {
            return aesEncrypt(password, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String aesEncrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(sKey.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);

    }

}
