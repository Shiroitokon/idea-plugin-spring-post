package com.geeboo.shiroito.windows;

import com.geeboo.shiroito.storage.GeeBooPostStorage;
import com.geeboo.shiroito.storage.GeeBooPostStorageService;
import com.geeboo.shiroito.storage.StorageKey;
import com.geeboo.shiroito.ui.GeeBooPostSettingUI;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hongyq (修改代码请联系开发者
 * @date 2020-11-09 16:44
 */
public class GeeBooPostConfigurable implements SearchableConfigurable {

    private GeeBooPostSettingUI geeBooPostSettingUI;



    @Override
    public @NotNull String getId() {
        return "GeeBooPostConfigurable";
    }

    @Override
    public @Nls(capitalization = Nls.Capitalization.Title) String getDisplayName() {
        return getId();
    }

    @Override
    public @Nullable JComponent createComponent() {
        if (null == geeBooPostSettingUI) {
            this.geeBooPostSettingUI = new GeeBooPostSettingUI();
        }
        return geeBooPostSettingUI.getLayeredPane1();
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {

        GeeBooPostStorage state = GeeBooPostStorageService.getInstance().getState();
        saveGeeBooPostStorage(state);
    }

    private void saveGeeBooPostStorage(GeeBooPostStorage state) {

        JComboBox evm = this.geeBooPostSettingUI.getEvm();
        String currentEvm = (String)evm.getSelectedItem();
        state.setCurrentEvm(currentEvm);

        Map<String, Map<String, String>> postSetting = state.getPostSetting();
        Map<String, String> stringStringMap = postSetting.computeIfAbsent(currentEvm, x -> new HashMap<>());

        String domain = this.geeBooPostSettingUI.getDomain().getText();
        String secret = this.geeBooPostSettingUI.getSecret().getText();
        String omsUser = this.geeBooPostSettingUI.getOmsUser().getText();
        String omsPass = this.geeBooPostSettingUI.getOmsPass().getText();
        String appUser = this.geeBooPostSettingUI.getAppUser().getText();
        String appPass = this.geeBooPostSettingUI.getAppPass().getText();

        stringStringMap.put(StorageKey.DOMAIN, domain);
        stringStringMap.put(StorageKey.SECRET, secret);
        stringStringMap.put(StorageKey.OMS_USER, omsUser);
        stringStringMap.put(StorageKey.OMS_PASS, omsPass);
        stringStringMap.put(StorageKey.APP_USER, appUser);
        stringStringMap.put(StorageKey.APP_PASS, appPass);

        GeeBooPostStorageService.getInstance().loadState(state);
    }


    @Override
    public void disposeUIResources() {
        geeBooPostSettingUI = null;
    }
}
