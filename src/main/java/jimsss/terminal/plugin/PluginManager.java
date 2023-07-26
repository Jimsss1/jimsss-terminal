package jimsss.terminal.plugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

public class PluginManager {
    private URLClassLoader urlClassLoader;

    public PluginManager(List<Plugin> plugins) throws MalformedURLException {
        init(plugins);
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
        if (!Arrays.stream(urls).toList().isEmpty()) {
            urlClassLoader = new URLClassLoader(urls);
        }
    }

    public TerminalPlugin getInstance(String className)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (urlClassLoader != null) {
            if (!className.isEmpty()) {
                Class<?> loadClass = urlClassLoader.loadClass(className);
                Object instance = loadClass.getDeclaredConstructor().newInstance();

                return (TerminalPlugin) instance;
            }
        }
        return null;
    }
}
