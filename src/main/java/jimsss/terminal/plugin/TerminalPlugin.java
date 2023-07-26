package jimsss.terminal.plugin;

public interface TerminalPlugin {
    String getPluginName();

    void load();

    void reload();

    void unload();

    void runCommand(String[] command);
}
