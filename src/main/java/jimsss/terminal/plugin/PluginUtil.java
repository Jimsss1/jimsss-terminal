package jimsss.terminal.plugin;

import jimsss.terminal.i18n.I18n;

import cn.hutool.core.lang.Console;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes;

public class PluginUtil {
    public static Map<String, TerminalPlugin> plugin(MethodName methodName)
            throws MalformedURLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<Plugin> pluginList = PluginManager.listPlugin();
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
                    case RELOAD -> {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                terminalPlugin.reload();
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
                pluginClassMap.put(plugin.getPluginName(), terminalPlugin);
            }
        }
        return pluginClassMap;
    }

    public static String readManifest(String jarFilePath, String name) {
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Manifest manifest = jarFile.getManifest();
            Attributes mainAttributes = manifest.getMainAttributes();
            for (Map.Entry<Object, Object> entry : mainAttributes.entrySet()) {
                String key = entry.getKey().toString();
                if (key.equalsIgnoreCase(name)) {
                    return entry.getValue().toString();
                }
            }
        } catch (Exception exception) {
            Console.error(I18n.getString("main.error") + exception.getMessage());
        }
        return null;
    }
}
