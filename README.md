# jimsss-terminal
## 我自己开发的一个支持插件控制台
# 插件如何开发?
1. 在IntelliJ IDEA 2023.1.3中创建一个Maven项目,然后在项目目录创建一个文件夹(推荐名称:lib或library)但是名称内不能包含中文!
2. 复制Github上此项目的存储库中的最新的释放jar包(该文件会根据版本更新),保存到上一步创建的文件夹中,然后 右键->添加为库
3. 打开IntelliJ IDEA中的 文件->项目结构->工件->添加工件 工件内需要有该项目的编译输出和主清单文件,主清单文件需要有这2个属性:
    - Plugin-Name(插件名称,同时也是命令前缀)
    - Plugin-Main(插件实现了TerminalPlugin的类)
4. 然后创建一个类,这个类的名字随意,但包名不能为 jimsss.terminal ,这个类要实现TerminalPlugin,填到主清单文件的Plugin-Main
## 接口定义:
- getPluginName():已弃用,用于获取插件名称,但是最好写上!示例:
```
@Override
public String getPluginName() {
    try {
        return PluginUtil.readManifest(该类的类名.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath(), "Plugin-Name");
    } catch (URISyntaxException exception) {
        Console.log(jimsss.terminal.i18n.I18n.getString("main.error") + exception.getMessage());
        return "你的插件名称";
    }
}
```
- load():插件第一次加载时执行
- reload():终端输入 B reload 你的插件名称 时执行,重新加载
- unload():终端输入 B unload 你的插件名称 时执行,卸载插件
- runCommand(String[] input):input的第0个元素和插件名称相符时执行,input为终端读取输入内容去除前后空格后分割后的数组
# 这玩意支持国际化!
