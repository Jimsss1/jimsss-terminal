package jimsss.terminal.plugin;

import jimsss.terminal.MetaData;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    public static List<Plugin> getPluginList()
            throws DocumentException, MalformedURLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(MetaData.PLUGIN_XML_FILE);
        Element rootElement = document.getRootElement();
        List<?> plugins = rootElement.elements("plugin");

        List<Plugin> list = new ArrayList<>();
        for (Object pluginObject : plugins) {
            Element pluginElement = (Element) pluginObject;
            Plugin plugin = new Plugin(
                    pluginElement.elementText("jarPath"),
                    pluginElement.elementText("class")
            );
            plugin.setPluginName(new PluginManager(List.of(plugin)).getInstance(plugin.getClassName()).getPluginName());
            list.add(plugin);
        }
        return list;
    }
}
