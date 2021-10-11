package net.minestom.server.utils;

import net.minestom.server.instance.block.BlockManager;

public class Logger {
    private final String name;

    public Logger() {
        this.name = "MAIN";
    }

    public Logger(Class<BlockManager> clazz) {
        name = clazz.getName();
    }
}
