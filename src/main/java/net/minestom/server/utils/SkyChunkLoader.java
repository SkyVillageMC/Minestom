package net.minestom.server.utils;

import com.google.common.collect.Lists;
import net.minestom.server.Main;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.DynamicChunk;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.biomes.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;
import org.jglrxavpok.hephaistos.nbt.NBTException;
import org.jglrxavpok.hephaistos.nbt.NBTReader;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class SkyChunkLoader implements IChunkLoader {

    private Block[][][][][] blocks = new Block[16][16][16][255][16];

    @Override
    public void loadInstance(@NotNull Instance instance) {
        System.out.println("Loading world");
        for (int cX = 0; cX < 16; cX++) {
            for (int cZ = 0; cZ < 16; cZ++) {
                for (int x = 0; x < 16; x++) {
                    for (int y = 0; y < 256; y++) {
                        for (int z = 0; z < 16; z++) {
                            blocks[cX][cZ][x][y][z] = Block.AIR;
                        }
                    }
                }
            }
        }
        instance.setTimeRate(0);
        try {
            NBTCompound compound = (NBTCompound) new NBTReader(new File("./save.sv"), false).read();
            compound.getKeys().forEach(s -> {
                Integer[] coords = (Integer[]) Lists.asList("0", s.split(",")).stream().map(Integer::parseInt).toArray();
                int chunkX = (coords[1] / 16) - 8;
                int chunkZ = (coords[3] / 16) - 8;

                blocks[chunkX][chunkZ][coords[1] % 16][coords[2]][coords[3] % 16] = Block.fromBlockId(compound.getInt(s));
            });
        } catch (IOException | NBTException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded world!");
    }

    @Override
    public @NotNull CompletableFuture<@Nullable Chunk> loadChunk(@NotNull Instance instance, int chunkX, int chunkZ) {
        long start = System.currentTimeMillis();
        Block[][][] b = blocks[chunkX + 8][chunkZ + 8];

        Chunk c = new DynamicChunk(instance, new Biome[]{Main.SAVANNA}, chunkX, chunkZ);
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 256; y++) {
                for (int z = 0; z < 16; z++) {
                    c.setBlock(x, y, z, b[x][y][z] == null ? Block.AIR : b[x][y][z]);
                }
            }
        }
        System.out.println("Loaded chunk at " + chunkX + ", " + chunkZ + ", took " + (System.currentTimeMillis() - start) + "ms!");
        return CompletableFuture.completedFuture(c);
    }

    @Override
    public @NotNull CompletableFuture<Void> saveInstance(@NotNull Instance instance) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public @NotNull CompletableFuture<Void> saveChunk(@NotNull Chunk chunk) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public @NotNull CompletableFuture<Void> saveChunks(@NotNull Collection<Chunk> chunks) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public boolean supportsParallelSaving() {
        return false;
    }

    @Override
    public boolean supportsParallelLoading() {
        return false;
    }
}
