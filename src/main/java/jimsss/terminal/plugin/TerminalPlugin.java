package jimsss.terminal.plugin;

public interface TerminalPlugin {
    @Deprecated
    String getPluginName();

    void load();

    void reload();

    void unload();

    void runCommand(String[] command);
}
