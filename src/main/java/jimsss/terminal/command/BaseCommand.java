package jimsss.terminal.command;

import jimsss.terminal.Main;
import jimsss.terminal.MetaData;
import jimsss.terminal.i18n.I18n;
import jimsss.terminal.plugin.*;
import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;

public class BaseCommand {
    private static final String baseCommandHelp = I18n.getString("command.help");

    public static void runBaseCommand(String[] input)
            throws DocumentException, MalformedURLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (input.length != 1) {
            switch (input[1]) {
                case "help" -> BaseCommand.getHelp();
                case "version" -> BaseCommand.getVersion();
                case "exit" -> BaseCommand.exit();
                case "plugins" -> BaseCommand.getPlugins();
                case "reload" -> BaseCommand.reloadPlugin(input);
                case "unload" -> BaseCommand.unloadPlugin(input);
                default -> BaseCommand.noCommand();
            }
        }
    }

    public static void noCommand() {
        System.out.println(I18n.getString("command.base_no_command"));
    }

    public static void getHelp() {
        System.out.println(baseCommandHelp);
    }

    public static void getVersion() {
        System.out.println("JimsssTerminal " + I18n.getString("command_version") + ":" + MetaData.VERSION);
    }

    public static void exit()
            throws MalformedURLException, DocumentException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        PluginUtil.plugin(MethodName.UNLOAD);
        System.exit(0);
    }

    public static void getPlugins()
            throws DocumentException, MalformedURLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<Plugin> pluginList = XMLParser.getPluginList();
        System.out.print(I18n.getString("command.plugin.list.one") + ":");
        int i = 0;
        for (; i < pluginList.size(); i++) {
            System.out.println(pluginList.get(i).getPluginName() + " ");
        }
        System.out.println("\n" + I18n.getFormatString("command.plugin.list.two", i));
    }

    public static void reloadPlugin(String[] input)
            throws MalformedURLException, DocumentException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (input.length == 3) {
            TerminalPlugin terminalPlugin = getTerminalPlugin(input);
            if (terminalPlugin != null) {
                terminalPlugin.reload();
            } else {
                System.out.println(I18n.getString("command.plugin.plugin_name_invalid"));
            }
        } else {
            System.out.println(I18n.getString("command.invalid"));
        }
    }

    public static void unloadPlugin(String[] input)
            throws MalformedURLException, DocumentException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (input.length == 3) {
            TerminalPlugin terminalPlugin = getTerminalPlugin(input);
            Collection<TerminalPlugin> terminalPluginCollection = Main.pluginMap.values();
            while (terminalPluginCollection.contains(terminalPlugin)) {
                terminalPluginCollection.remove(terminalPlugin);
            }
            if (terminalPlugin != null) {
                terminalPlugin.unload();
            } else {
                System.out.println(I18n.getString("command.plugin.plugin_name_invalid"));
            }
        } else {
            System.out.println(I18n.getString("command.invalid"));
        }
    }

    public static TerminalPlugin getTerminalPlugin(String[] input)
            throws DocumentException, MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Plugin> pluginList = XMLParser.getPluginList();
        PluginManager pluginManager = new PluginManager(pluginList);
        String className = "";
        for (Plugin plugin : pluginList) {
            if (plugin.getPluginName().equals(input[2])) {
                className = plugin.getClassName();
                break;
            }
        }
        return pluginManager.getInstance(className);
    }
}
