package com.geeboo.shiroito.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hongyq (修改代码请联系开发者)
 * @date 2020-11-09 17:29
 */
public class GeeBooPostStorage {
    //当前环境
    private String currentEvm = "local";

    private String prefixModule = "";

    private Map<String, Map<String, String>> postSetting = new HashMap<>();

    public String getCurrentEvm() {
        return currentEvm;
    }

    public void setCurrentEvm(String currentEvm) {
        this.currentEvm = currentEvm;
    }

    public Map<String, Map<String, String>> getPostSetting() {
        return postSetting;
    }

    public void setPostSetting(Map<String, Map<String, String>> postSetting) {
        this.postSetting = postSetting;
    }

    public String getPrefixModule() {
        return prefixModule;
    }

    public void setPrefixModule(String prefixModule) {
        this.prefixModule = prefixModule;
    }
}
