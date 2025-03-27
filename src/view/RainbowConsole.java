package view;

public class RainbowConsole {

    public static final String RESET = "\u001B[0m";
    public static final String PRIMARY = "\u001B[36m";
    public static final String ACCENT = "\u001B[32m";
    public static final String ERROR = "\u001B[31m";
    public static final String WARNING = "\u001B[33m";

    public static void prnt(String text, int style) {
        switch (style) {
            case 1 -> System.out.println("   " + PRIMARY + text + RESET);
            case 2 -> System.out.println("   " + ACCENT + text + RESET);
            case 3 -> System.out.println("   " + WARNING + text + RESET);
            case 4 -> System.out.println("   " + ERROR + text + RESET);
            default -> System.out.println("   " + text);
        }
    }
}
