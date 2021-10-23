package command;

import net.minestom.server.command.builder.Command;

import static net.minestom.server.Main.RAIIIIIIN;
import static net.minestom.server.Main.THUNAHH;

public class RainCommand extends Command {

    public RainCommand() {
        super("rain");

        setDefaultExecutor((sender, context) -> {
            sender.asPlayer().sendPacket(RAIIIIIIN);
            sender.asPlayer().sendPacket(THUNAHH);
        });
    }
}
