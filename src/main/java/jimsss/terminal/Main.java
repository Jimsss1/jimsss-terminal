package jimsss.terminal;

import jimsss.terminal.command.BaseCommand;
import jimsss.terminal.i18n.I18n;
import jimsss.terminal.plugin.MethodName;
import jimsss.terminal.plugin.PluginUtil;
import jimsss.terminal.plugin.TerminalPlugin;

import cn.hutool.core.lang.Console;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.util.Map;

public class Main {
    //插件Map
    public static Map<String, TerminalPlugin> pluginMap;

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, InterruptedException {
        Console.log("JimsssTerminal" + MetaData.VERSION);
        //插件Map初始化
        pluginMap = PluginUtil.plugin(MethodName.LOAD);
        if (pluginMap.isEmpty()) {
            Console.log(I18n.getString("main.no_plugin"));
            if (MetaData.NO_PLUGIN_EXIT) {
                System.exit(1);
            }
        }
        while (true) {
            //读取输入
            Thread.sleep(pluginMap.size() * 100L);
            Console.print(">");
            String[] input = Console.scanner().nextLine()
                            .trim().split(" ");
            String commandPrefix = input[0];
            //判断前缀
            if (commandPrefix.equals("B")) {
                BaseCommand.runBaseCommand(input);
            } else {
                //根据前缀获取插件
                TerminalPlugin terminalPlugin = pluginMap.get(commandPrefix);
                if (terminalPlugin != null) {
                    terminalPlugin.runCommand(input);
                }
            }
        }
    }
}