# idea-plugin-spring-post
idea插件实现spring接口访问功能，可用于插件开发学习，囊括了idea插件开发的核心组件和常用功能
___
# 使用

1. 项目导入idea使用**Gradle**打包（侧边栏gradle->Tasks->intellij->buildPlugin）成zip文件，在项目目录build/distributions中获取
![alt text](/images/build.png)
2. 在idea中安装插件
3. 点击File | Settings | Other Settings | GeeBooPostConfigurable 设置基本信息
![alt text](/images/set.png)
4. 右键对应的测试方法名称，点击简帛post
![alt text](/images/action.png)
5. 在弹窗下编辑发送参数
![alt text](/images/toolWindow.png)

# 开发
## UI开发
下载idea插件**JFormDesigner**可更简单的进行UI的编辑和创建
## idea插件核心组件
项目使用idea插件中的四个核心功能

1. 右键点击编辑器对应的目标触发回调的**AnAction**
2. 创建侧边弹窗的**ToolWindowFactory**
3. 后台设置参数界面**SearchableConfigurable**
4. 数据持久化**PersistentStateComponent**

### AnAction
___
在项目中对应实现类为**GeeBooPostAction** 实现方法并在META-INF/plugin.xml 中配置
#### actionPerformed(AnActionEvent event)
右键点击事件
#### update(AnActionEvent event)
每次唤醒右键菜单栏渲染（项目中，当右键光标没有在方法上时对应事件按钮置灰并不可用）

### ToolWindowFactory
___
在项目中对应实现类为**GeeBooPostWindowFactory** 实现方法并在META-INF/plugin.xml 中配置

### SearchableConfigurable
在项目中实现方法为**GeeBooPostConfigurable** 实现方法并在META-INF/plugin.xml 中配置
#### JComponent createComponent()
创建并生成的UI界面
#### void apply()
点击应用设置时执行的方法
#### boolean isModified()
当返回为true时**apply()** 才能够生效

### PersistentStateComponent
___
项目中对应的实现为**GeeBooPostStorageService** 实现方法并在META-INF/plugin.xml 中配置
#### @State
持久化信息
##### @Storage
持久化保存的文件位置
#### T  getState()
获取持久化的数据信息
#### void loadState(T state)
保存持久化信息数据

## 插件开发常用方法



