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
