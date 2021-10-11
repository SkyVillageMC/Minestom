package lobby.command;

import net.minestom.server.command.builder.Command;

import java.lang.management.ManagementFactory;

public class GCCommand extends Command {
    public GCCommand() {
        super("gc");

        setDefaultExecutor((sender, context) -> {
            long start = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
            System.gc();
            long end = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
            sender.sendMessage("Memory saved: " + ((start - end) / 1024 / 1024) + "Mb");
        });
    }
}
