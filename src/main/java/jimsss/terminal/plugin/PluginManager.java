package jimsss.terminal.plugin;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.ArrayList;

public class PluginManager {
    private URLClassLoader urlClassLoader;

    public PluginManager(List<Plugin> plugins) throws MalformedURLException {
        init(plugins);
    }

    public static List<Plugin> listPlugin() {
        List<Plugin> pluginList = new ArrayList<>();
        File pluginsDir = new File(System.getProperty("user.dir") + "\\plugins");
        if (!pluginsDir.exists()) {
            pluginsDir.mkdir();
            return pluginList;
        }
        File[] files = pluginsDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        });
        if (files != null) {
            for (File file : files) {
                String jarFilePath = file.getAbsolutePath();
                pluginList.add(new Plugin(
                        PluginUtil.readManifest(jarFilePath, "Plugin-Name"),
                        PluginUtil.readManifest(jarFilePath, "Plugin-Main"),
                        jarFilePath));
            }
        }
        return pluginList;
    }

    private void init(List<Plugin> plugins) throws MalformedURLException {
        int size = plugins.size();
        URL[] urls = new URL[size];

        for (int i = 0; i < size; i++) {
            Plugin plugin = plugins.get(i);
            String filePath = plugin.getJarPath();
            if (new File(filePath).exists()) {
                urls[i] = new URL("file:" + filePath);
            }
        }
        if (urls.length != 0) {
            urlClassLoader = new URLClassLoader(urls);
        }
    }

    public TerminalPlugin getInstance(String className)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (urlClassLoader != null) {
            if (!className.isEmpty()) {
                Object instance = urlClassLoader.loadClass(className).getDeclaredConstructor().newInstance();

                return (TerminalPlugin) instance;
            }
        }
        return null;
    }
}
