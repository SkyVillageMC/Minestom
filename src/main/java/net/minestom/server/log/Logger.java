package net.minestom.server.log;

import net.minestom.server.extras.lan.OpenToLAN;

import java.util.Arrays;

public class Logger {

    private String name;

    public Logger(String name) {
        this.name = name;
    }

    public Logger(Class<?> clazz) {
        this(clazz.getName());
    }

    private String format(String format, Object... args) {
        String[] pieces = format.split("\\{\\}");
        StringBuilder sb = new StringBuilder();
        int aCounter = 0;
        if (args.length == 0) {
            return format;
        }
        for (String piece : pieces) {
            try {
                sb.append(piece).append(args[aCounter]);
            } catch (Exception ignored) {
            }
            aCounter++;
        }
        return sb.toString();
    }

    public void info(String format, Object... args) {
        System.out.println(name + "|INFO> " + format(format, args));
    }

    public void warn(String format, Object... args) {
        System.out.println(name + "|WARN> " + format(format, args));
    }

    public void error(String format, Object... args) {
        System.out.println(name + "|ERROR> " + format(format, args));
    }

    public void debug(String format, Object... args) {
        System.out.println(name + "|DEBUG> " + format(format, args));
    }
}
