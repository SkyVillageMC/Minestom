package net.minestom.server;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.event.player.PlayerTickEvent;
import net.minestom.server.event.server.ServerStartEvent;
import net.minestom.server.instance.AnvilLoader;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.block.BlockManager;
import command.ExportCommand;
import command.GCCommand;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.utils.SkyChunkLoader;
import net.minestom.server.world.biomes.Biome;
import net.minestom.server.world.biomes.BiomeManager;

import java.lang.management.ManagementFactory;
import java.util.Objects;

public class Main {

    public static final Pos SPAWN = new Pos(80.5, 123, 75.5).withYaw(-90);

    public static final Biome SAVANNA = Biome.builder().name(NamespaceID.from("minecraft:savanna")).build();

    public static void main(String... args) {
        MinecraftServer minecraftServer = MinecraftServer.init();

        BiomeManager biomeManager = MinecraftServer.getBiomeManager();

        biomeManager.addBiome(SAVANNA);

        blockStuff();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();

        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

        instanceContainer.setChunkLoader(/*new AnvilLoader("./world")*/new SkyChunkLoader());

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
            final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(SPAWN);
            player.setGameMode(GameMode.CREATIVE);
            player.setFlying(true);
        });

        globalEventHandler.addListener(PlayerDisconnectEvent.class, e -> {
           System.gc();
           System.out.println("Used memory: " + (ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1024 / 1024) + "Mb");
        });

        globalEventHandler.addListener(PlayerTickEvent.class, e -> {
            e.getPlayer().sendPlayerListHeader(Component.text("RAM: " + MinecraftServer.getBenchmarkManager().getUsedMemory() / 1048576f + "MB"));
        });

        globalEventHandler.addListener(PlayerMoveEvent.class, e -> {
            if (e.getNewPosition().y() >= 119) {
                if (e.getNewPosition().x() < 90) return;
                if (e.getNewPosition().x() > 99) return;
                if (e.getNewPosition().y() > 131) return;
                if (!Objects.requireNonNull(e.getPlayer().getInstance()).getBlock(e.getNewPosition()).compare(Block.NETHER_PORTAL)) return;
                e.getPlayer().sendMessage("Portaaaal");
                return;
            }
            e.getPlayer().teleport(SPAWN);
            e.getPlayer().playSound(Sound.sound(Key.key("minecraft:entity.enderman.teleport"), Sound.Source.PLAYER, 1, 1));
        });

        globalEventHandler.addListener(ServerStartEvent.class, e -> {
            System.out.println("Server started!");
            instanceContainer.loadChunk(0, 0);
        });

        MinecraftServer.getCommandManager().register(new GCCommand());
        MinecraftServer.getCommandManager().register(new ExportCommand());

        MinecraftServer.setBrandName("Lobby");

        minecraftServer.start("0.0.0.0", 25565);
    }

    public static void blockStuff() {
        BlockManager blockManager = MinecraftServer.getBlockManager();

        blockManager.registerHandler("minecraft:skull", () -> () -> NamespaceID.from("minecraft:skull"));

        blockManager.registerHandler("minecraft:bed", () -> () -> NamespaceID.from("minecraft:bed"));

        blockManager.registerHandler("minecraft:barrel", () -> () -> NamespaceID.from("minecraft:barrel"));

        blockManager.registerHandler("minecraft:sign", () -> () -> NamespaceID.from("minecraft:sign"));

    }
}

