package jimsss.terminal.i18n;

import jimsss.terminal.MetaData;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class I18n {
    public static Locale locale = new Locale(MetaData.LANGUAGE, MetaData.COUNTRY);
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("terminal", locale);

    public static String getString(String key) {
        return resourceBundle.getString(key);
    }

    public static String getFormatString(String key, Object... valve) {
        return MessageFormat.format(resourceBundle.getString(key), valve);
    }
}
