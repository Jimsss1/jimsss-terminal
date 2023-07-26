package jimsss.terminal.command;

public class CommandUtils {
    public static boolean isInteger(String string) {
        char[] chars = string.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
