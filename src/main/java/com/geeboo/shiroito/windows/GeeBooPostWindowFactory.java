package com.geeboo.shiroito.windows;

import com.geeboo.shiroito.ui.GeeBooPostTableUI;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

/**
 * @author hongyq (修改代码请联系开发者)
 * @date 2020-11-05 14:48
 */
public class GeeBooPostWindowFactory  implements ToolWindowFactory {


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        GeeBooPostTableUI geeBooPostTableUI = GeeBooPostTableUI.getInstance();
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content geebooPost = contentFactory.createContent(geeBooPostTableUI.getMainGeebooWindow(), "geeboo-post", false);
        toolWindow.getContentManager().addContent(geebooPost);

    }



}
