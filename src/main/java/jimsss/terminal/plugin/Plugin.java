package jimsss.terminal.plugin;

import lombok.Data;

@Data
public class Plugin {
    private String pluginName;
    private String className;
    private String jarPath;

    public Plugin() {
    }

    public Plugin(String jarPath, String className) {
        this.jarPath = jarPath;
        this.className = className;
    }

    public Plugin(String pluginName, String className, String jarPath) {
        this.pluginName = pluginName;
        this.jarPath = jarPath;
        this.className = className;
    }
}
