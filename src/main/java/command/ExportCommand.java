package command;

import net.minestom.server.command.builder.Command;

import java.util.concurrent.ExecutionException;

public class ExportCommand extends Command {

    public ExportCommand() {
        super("export");

        setDefaultExecutor((sender, context) -> {
            try {
                sender.asPlayer().getInstance().saveChunksToStorage().thenRun(() -> {
                    sender.sendMessage("Chunks saved.");
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
