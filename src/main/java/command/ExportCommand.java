package command;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.Instance;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.*;

public class ExportCommand extends Command {

    public ExportCommand() {
        super("export");

        setDefaultExecutor((sender, context) -> {
            Collection<Chunk> chunks = ((Instance)MinecraftServer.getInstanceManager().getInstances().toArray()[0]).getChunks();
            List<String> palette = new ArrayList<>();
//            chunks.forEach(chunk -> {
//                for (int x = 0; x < 16; x++) {
//                    for (int y = 0; y < 256; y++) {
//                        for (int z = 0; z < 16; z++) {
//                            String id = chunk.getBlock(x, y, z).namespace().asString();
//                            Block.STONE
//                        }
//                    }
//                }
//            });
            NBTCompound compound = new NBTCompound();
        });
    }

}
