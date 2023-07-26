package jimsss.terminal.plugin;

import lombok.Data;

@Data
public class Plugin {
    private String pluginName;
    private String jarPath;
    private String className;

    public Plugin() {
    }

    public Plugin(String jarPath, String className) {
        this.jarPath = jarPath;
        this.className = className;
    }

    public Plugin(String pluginName, String jarPath, String className) {
        this.pluginName = pluginName;
        this.jarPath = jarPath;
        this.className = className;
    }
}
