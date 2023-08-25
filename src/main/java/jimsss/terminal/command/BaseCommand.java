package jimsss.terminal.command;

import jimsss.terminal.Main;
import jimsss.terminal.MetaData;
import jimsss.terminal.i18n.I18n;
import jimsss.terminal.plugin.*;

import cn.hutool.core.lang.Console;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;

public class BaseCommand {
    private static final String baseCommandHelp = I18n.getString("command.help");

    public static void runBaseCommand(String[] input)
            throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (input.length != 1) {
            switch (input[1]) {
                case "debug" -> BaseCommand.debugMode();
                case "help" -> BaseCommand.getHelp();
                case "version" -> BaseCommand.getVersion();
                case "exit" -> BaseCommand.exit();
                case "plugins" -> BaseCommand.getPlugins();
                case "reload" -> BaseCommand.reloadPlugin(input);
                case "unload" -> BaseCommand.unloadPlugin(input);
            }
        }
    }

    public static void debugMode() {
        MetaData.DEBUG_MODE = true;
    }

    public static void getHelp() {
        Console.log(baseCommandHelp);
    }

    public static void getVersion() {
        Console.log("JimsssTerminal " + I18n.getString("command_version") + ":" + MetaData.VERSION);
    }

    public static void exit()
            throws MalformedURLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Main.pluginMap = PluginUtil.plugin(MethodName.UNLOAD);
        System.exit(0);
    }

    public static void getPlugins() {
        List<Plugin> pluginList = PluginManager.listPlugin();
        Console.print(I18n.getString("command.plugin.list.one"));
        int i = 0;
        for (; i < pluginList.size(); i++) {
            Console.log(pluginList.get(i).getPluginName() + " ");
        }
        Console.log("\n" + I18n.getFormatString("command.plugin.list.two", i));
    }

    public static void reloadPlugin(String[] input)
            throws MalformedURLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (input.length == 3) {
            String pluginName = input[2];
            TerminalPlugin terminalPlugin = getTerminalPlugin(pluginName);
            if (terminalPlugin != null) {
                if (!Main.pluginMap.containsKey(pluginName)) {
                    Main.pluginMap.put(pluginName, terminalPlugin);
                }
                terminalPlugin.reload();
            } else {
                Console.log(I18n.getString("command.plugin.plugin_name_invalid"));
            }
        } else {
            Console.log(I18n.getString("command.invalid"));
        }
    }

    public static void unloadPlugin(String[] input)
            throws MalformedURLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (input.length == 3) {
            String pluginName = input[2];
            TerminalPlugin terminalPlugin = getTerminalPlugin(pluginName);
            if (terminalPlugin == null) {
                Console.log(I18n.getString("command.plugin.plugin_name_invalid"));
                return;
            }
            terminalPlugin.unload();
            Main.pluginMap.remove(pluginName);
        } else {
            Console.log(I18n.getString("command.invalid"));
        }
    }

    public static TerminalPlugin getTerminalPlugin(String pluginName)
            throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Plugin> pluginList = PluginManager.listPlugin();
        PluginManager pluginManager = new PluginManager(pluginList);
        String className = "";
        for (Plugin plugin : pluginList) {
            if (plugin.getPluginName().equals(pluginName)) {
                className = plugin.getClassName();
                break;
            }
        }
        return pluginManager.getInstance(className);
    }
}
