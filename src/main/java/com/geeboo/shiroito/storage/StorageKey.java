package com.geeboo.shiroito.storage;

/**
 * @author hongyq (修改代码请联系开发者)
 * @date 2020-11-09 18:35
 */
public class StorageKey {

    public static final String DOMAIN = "domain";

    public static final String SECRET = "secret";

    public static final String OMS_USER = "omsUser";

    public static final String OMS_PASS = "omsPass";

    public static final String APP_USER = "appUser";

    public static final String APP_PASS = "appPass";

    public static String getTokenKey(String userType , String evm) {
        return evm + "_" + userType;
    }


}
