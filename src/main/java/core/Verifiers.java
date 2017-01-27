package core;

public class Verifiers {
    public static void verify(boolean b) {
        if (!b) {
            throw new RuntimeException();
        }
    }

    public static void verify(boolean b, String message, Object... args) {
        if (!b) {
            throw new RuntimeException(String.format(message, args));
        }
    }

    public static void verifyArg(boolean b, String argName, Object argValue) {
        if (!b) {
            throw new RuntimeException(String.format("Incorrect argument: %s = %s", argName, argValue));
        }
    }

    public static void verifyArgNotNull(Object arg, String argName) {
        if (arg == null) {
            throw new IllegalArgumentException(String.format("Argument is null: %s", argName));
        }
    }
}
