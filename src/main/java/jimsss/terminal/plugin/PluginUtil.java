package jimsss.terminal.plugin;

import org.dom4j.DocumentException;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginUtil {
    public static Map<String, TerminalPlugin> plugin(MethodName methodName)
            throws DocumentException, MalformedURLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<Plugin> pluginList = XMLParser.getPluginList();
        PluginManager pluginManager = new PluginManager(pluginList);
        Map<String, TerminalPlugin> pluginClassMap = new HashMap<>();
        for (Plugin plugin : pluginList) {
            TerminalPlugin terminalPlugin = pluginManager.getInstance(plugin.getClassName());
            if (terminalPlugin != null) {
                switch (methodName) {
                    case LOAD -> {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                terminalPlugin.load();
                            }
                        });
                        thread.start();
                    }
                    case UNLOAD -> {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                terminalPlugin.unload();
                            }
                        });
                        thread.start();
                    }
                }
                pluginClassMap.put(terminalPlugin.getPluginName(), terminalPlugin);
            }
        }
        return pluginClassMap;
    }

}
