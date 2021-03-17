package com.geeboo.shiroito.action;

import com.geeboo.shiroito.storage.GeeBooPostStorage;
import com.geeboo.shiroito.storage.GeeBooPostStorageService;
import com.geeboo.shiroito.ui.GeeBooPostTableUI;
import com.geeboo.shiroito.utils.ParamValueUtils;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author hongyq (修改代码请联系开发者)
 * @date 2020-11-05 14:48
 */
public class GeeBooPostAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        //获取选择的光标类型
        PsiElement psiElement = event.getData(LangDataKeys.PSI_ELEMENT);
        Project project = event.getData(LangDataKeys.PROJECT);
        if(!(psiElement instanceof PsiMethod)) {
            return;
        }

        PsiFile psiFile = event.getData(LangDataKeys.PSI_FILE);
        //不是JAVA类直接返回
        if(!(psiFile instanceof  PsiJavaFile)){
            return;
        }

        PsiJavaFile psiJavaFile = (PsiJavaFile)psiFile;
        PsiClass psiClass = psiJavaFile.getClasses()[0];
        PsiMethod psiMethod = (PsiMethod) psiElement;
        //获取模块名称
        Module module = event.getData(LangDataKeys.MODULE);
        if(module == null) {
            return;
        }

        //获取访问链接
        String uri = getNameSpaceUri(module) + getClassUri(psiClass) + getMethodUri(psiMethod);

        Map<String, String> formParamMap = getFormParamMap(psiMethod, module);
        //获取请求方式
        String requestMethod = getRequestMethod(psiMethod);

        String contentType = getContentType(psiMethod);

        cacheParamValueWrapper(uri, formParamMap, requestMethod);

        //选中窗口
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(project);
        ToolWindow geebooPost = toolWindowManager.getToolWindow("geebooPost");
        geebooPost.show(() -> {});

        GeeBooPostTableUI instance = GeeBooPostTableUI.getInstance();
        instance.paramFormGenerate(formParamMap, uri, requestMethod, contentType);

    }

    private void cacheParamValueWrapper(String uri, Map<String, String> formParamMap, String requestMethod) {

        if(formParamMap == null || formParamMap.size() == 0) {
            return;
        }
        GeeBooPostStorage state = GeeBooPostStorageService.getInstance().getState();
        Map<String, Map<String, String>> paramStorage = state.getParamStorage();
        if(paramStorage == null || paramStorage.size() == 0) {
            return;
        }

        Map<String, String> map = paramStorage.get(uri + "_" + requestMethod);
        if(map == null) {
            return;
        }

        for(Map.Entry<String, String> entry: formParamMap.entrySet()) {

            String key = entry.getKey();
            Optional.ofNullable(map.get(key)).ifPresent(entry::setValue);
        }
    }

    private String getContentType(PsiMethod psiMethod) {

        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        for(PsiParameter parameter: parameters) {

            PsiAnnotation annotation = parameter.getAnnotation("org.springframework.web.bind.annotation.RequestBody");

            if(annotation != null) {
                return "JSON";
            }

        }

        return "FORM";
    }

    private String getRequestMethod(PsiMethod psiMethod) {

        PsiAnnotation annotation = psiMethod.getAnnotation("org.springframework.web.bind.annotation.GetMapping");

        if(annotation != null) {
            return "GET";
        }

        return "POST";

    }


    private Map<String, String> getFormParamMap (PsiMethod psiMethod, Module module) {

        HashMap<String, String> formParamMap = new HashMap<>();

        //获取访问参数类型
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();

        if(parameters == null || parameters.length == 0) {
            return formParamMap;
        }


        for(PsiParameter parameter: parameters) {
            String name = parameter.getName();
            String className = parameter.getType().getCanonicalText();
            Boolean isBase = checkBaseType(className);

            //基本参数类型直接加入数据
            if(isBase) {
                formParamMap.put(name, ParamValueUtils.valueGenerate(name, className));
                continue;
            }

            //如果为数组
            if(className.startsWith("java.util.List")) {
                formParamMap.put(name, ParamValueUtils.valueGenerate(name, className));
                continue;
            };

            //如果为时间
            if(className.equals("java.util.Date") || className.equals("java.time.LocalDateTime")) {
                formParamMap.put(name, ParamValueUtils.valueGenerate(name, className));
                continue;
            }

            //过滤类型
            if(className.equals("org.springframework.validation.BindingResult") ||                    className.equals("javax.servlet.http.HttpServletResponse")) {
                continue;
            }


            //对象类型获取对象元数据
            PsiClass param = JavaPsiFacade.getInstance(parameter.getProject()).findClass(className, GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module));

            //如果为枚举
            if(param.isEnum()) {
                handleEnumParam(name, param, formParamMap);
                continue;
            }

            handleObjectParam(param, formParamMap);
        }

        return formParamMap;
    }


    private void handleObjectParam(PsiClass param, Map<String, String> src) {


        PsiField[] allFields = param.getAllFields();

        if(allFields.length == 0) {
            return;
        }

        for(PsiField field: allFields) {
            //如果为枚举
            String name = field.getName();

            //过滤指定名称
            if(name.equals("serialVersionUID")) {
                continue;
            }

            String canonicalText = field.getType().getCanonicalText();
            //如果为基础类型
            if(checkBaseType(canonicalText)) {
                src.put(name, ParamValueUtils.valueGenerate(name, canonicalText));
                continue;
            }

            PsiClass psiClass = PsiUtil.resolveClassInType(field.getType());

            if(psiClass == null) {
                src.put(name, ParamValueUtils.valueGenerate(name, canonicalText));
                continue;
            }

            if(psiClass.isEnum()) {
                handleEnumParam(name, psiClass, src);
                continue;
            }
            src.put(name, ParamValueUtils.valueGenerate(name, canonicalText));
        }
    }


    private void handleEnumParam(String fieldName, PsiClass param, Map<String, String> src) {

        PsiField[] allFields = param.getAllFields();

        if(allFields.length == 0) {
            return;
        }

        List<String> stringList = new ArrayList<>();

        for(PsiField field: allFields) {
            //如果为枚举
            if(!(field instanceof PsiEnumConstant)) {
                continue;
            }

            PsiEnumConstant psiEnumConstant = (PsiEnumConstant)field;
            stringList.add(psiEnumConstant.getName());

        }

        int i = new Random().nextInt(stringList.size());

        src.put(fieldName, stringList.get(i));
    }

    private Boolean checkBaseType(String className) {

        if(
                className.equals("java.lang.Long") ||
                className.equals("java.lang.String") ||
                className.equals("java.lang.Double") ||
                className.equals("java.lang.Float") ||
                className.equals("java.lang.Integer") ||
                className.equals("java.lang.Boolean") ||
                className.equals("int") ||
                className.equals("float") ||
                className.equals("long") ||
                className.equals("double") ||
                className.equals("long") ||
                className.equals("boolean")

        ) {
            return true;
        }
        return false;
    }

    private String getNameSpaceUri(Module moduleElement) {
        String moduleName = moduleElement.getName();
        GeeBooPostStorage state = GeeBooPostStorageService.getInstance().getState();

        if(moduleName == null || moduleName.equals("") || state == null || null == state.getPrefixModule()) {
            return "";
        }
        return "/" + moduleName.replaceAll(state.getPrefixModule(), "");
    }

    public String getClassUri(PsiClass controllerClass) {
        PsiAnnotation annotation = controllerClass.getAnnotation("org.springframework.web.bind.annotation.RequestMapping");

        if(annotation == null) {
            return "";
        }

        PsiAnnotationMemberValue psiClassUrl = annotation.findAttributeValue("value");
        if(psiClassUrl == null) {
            return "";
        }
        String srcText = psiClassUrl.getText();
        if(srcText == null || srcText.equals("") || srcText.equals("{}")) {
            return "";
        }

        String text = srcText.replaceAll("\"", "");

        if(text.indexOf("/") == 0) {
            return text;
        }

        return "/" + text;
    }

    private String getMethodUri(PsiMethod psiMethod) {
        PsiAnnotation annotation = psiMethod.getAnnotation("org.springframework.web.bind.annotation.PostMapping");


        if(annotation == null) {
            annotation = psiMethod.getAnnotation("org.springframework.web.bind.annotation.GetMapping");
        }

        if(annotation == null) {

            annotation = psiMethod.getAnnotation("org.springframework.web.bind.annotation.RequestMapping");

        }


        PsiAnnotationMemberValue psiMethodUrl = annotation.findAttributeValue("value");

        if(psiMethodUrl == null) {
            return "";
        }

        String srcText = psiMethodUrl.getText();
        if(srcText == null || srcText.equals("") || srcText.equals("{}")) {
            return "";
        }

        String text = srcText.replaceAll("\"", "");

        if(text.indexOf("/") == 0) {
            return text;
        }

        return "/" + text;
    }

    /**
     * 每次右键会使用此方法
     * 光标不在方法上置为不可用
     * @param event
     */
    @Override
    public void update(@NotNull AnActionEvent event) {
        PsiElement psiElement = event.getData(LangDataKeys.PSI_ELEMENT);
        if(!(psiElement instanceof PsiMethod)) {
            event.getPresentation().setEnabled(false);
        } else {
            event.getPresentation().setEnabled(true);
        }
        super.update(event);
    }
}
