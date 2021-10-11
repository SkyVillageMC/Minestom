package net.minestom.server.utils;

import com.google.common.util.concurrent.Futures;
import net.minestom.server.Main;
import net.minestom.server.instance.Chunk;
import net.minestom.server.instance.DynamicChunk;
import net.minestom.server.instance.IChunkLoader;
import net.minestom.server.instance.Instance;
import net.minestom.server.instance.block.Block;
import net.minestom.server.world.biomes.Biome;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class SkyChunkLoader implements IChunkLoader {
    @Override
    public void loadInstance(@NotNull Instance instance) {
        instance.setTimeRate(10);
    }

    @Override
    public @NotNull CompletableFuture<@Nullable Chunk> loadChunk(@NotNull Instance instance, int chunkX, int chunkZ) {
        Chunk c = new DynamicChunk(instance, new Biome[]{Main.SAVANNA}, chunkX, chunkZ);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                c.setBlock(x, 122, z, Block.STONE);
            }
        }
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
        return true;
    }
}
