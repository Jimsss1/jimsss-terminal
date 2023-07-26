package jimsss.terminal;

import jimsss.terminal.command.BaseCommand;
import jimsss.terminal.i18n.I18n;
import jimsss.terminal.plugin.MethodName;
import jimsss.terminal.plugin.PluginUtil;
import jimsss.terminal.plugin.TerminalPlugin;
import jimsss.terminal.plugin.WriteExampleXml;
import org.dom4j.DocumentException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    //插件Map
    public static Map<String, TerminalPlugin> pluginMap;

    public static void main(String[] args)
            throws IOException, DocumentException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        //初始化
        pluginMap = new HashMap<>();
        System.out.println("JimsssTerminal" + MetaData.VERSION);
        //插件配置文件不存在
        if (!MetaData.PLUGIN_XML_FILE.exists()) {
            boolean createPluginXmlFile = MetaData.PLUGIN_XML_FILE.createNewFile();
            System.out.println(I18n.getString("main.no_plugin_xml") + createPluginXmlFile);
            if (createPluginXmlFile) {
                new WriteExampleXml();
                System.out.println(I18n.getString("main.write_example_xml_success"));
            }
            if (MetaData.NO_PLUGIN_EXIT) {
                System.exit(1);
            }
        }
        //读取插件配置文件
        pluginMap = PluginUtil.plugin(MethodName.LOAD);
        if (pluginMap.isEmpty()) {
            System.out.println(I18n.getString("main.no_plugin"));
            if (MetaData.NO_PLUGIN_EXIT) {
                System.exit(1);
            }
        }
        while (true) {
            //读取输入
            System.out.print(">");
            String[] input = new Scanner(System.in).nextLine()
                    .trim().split(" ");
            String pluginCommandPrefix = input[0];
            //判断前缀
            if (pluginCommandPrefix.equals("B")) {
                BaseCommand.runBaseCommand(input);
            } else {
                //根据前缀获取插件
                TerminalPlugin terminalPlugin = pluginMap.get(pluginCommandPrefix);
                if (terminalPlugin != null) {
                    terminalPlugin.runCommand(input);
                }
            }
        }
    }
}