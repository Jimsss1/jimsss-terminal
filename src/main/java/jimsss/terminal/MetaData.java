package jimsss.terminal;

import java.io.File;
import java.util.Locale;

public class MetaData {
    public static boolean NO_PLUGIN_EXIT = false;
    public static String VERSION = "0.1";
    public static File PLUGIN_XML_FILE = new File(System.getProperty("user.dir") + "\\plugin.xml");
    public static String LANGUAGE = Locale.getDefault().getLanguage();
    public static String COUNTRY = Locale.getDefault().getCountry();
}
