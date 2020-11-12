package com.geeboo.shiroito.storage;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author hongyq (修改代码请联系开发者)
 * @date 2020-11-09 17:28
 */
@State(name = "GeeBooPostStorage", storages = {@Storage(value = "geeboo-post.xml")})
public class GeeBooPostStorageService  implements PersistentStateComponent<GeeBooPostStorage> {

    private GeeBooPostStorage geeBooPostStorage;


    public static GeeBooPostStorageService getInstance() {
        return ServiceManager.getService(GeeBooPostStorageService.class);
    }

    @Nullable
    @Override
    public GeeBooPostStorage getState() {
        if(geeBooPostStorage == null) {
            geeBooPostStorage = new GeeBooPostStorage();
        }
        return geeBooPostStorage;
    }

    @Override
    public void loadState(@NotNull GeeBooPostStorage state) {
        geeBooPostStorage = state;
    }
}
