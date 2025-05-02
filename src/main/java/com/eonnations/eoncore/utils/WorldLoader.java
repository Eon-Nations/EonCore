package com.eonnations.eoncore.utils;

import java.util.List;
import java.util.function.Consumer;

import io.vavr.control.Try;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldLoader {

    public static void initializeWorlds(JavaPlugin plugin) {
        WorldLoader worldLoader = new WorldLoader();
        worldLoader.loadWorlds(plugin);
        worldLoader.setupGameRules(plugin);
    }

    public void loadWorlds(JavaPlugin plugin) {
        Bukkit.getGlobalRegionScheduler().run(plugin, task -> {
            Try<World> worldCreateAttempt = Try.of(() -> new WorldCreator("spawn_void").generator(new VoidChunkGenerator()).createWorld());
        });
    }

    public void setupGameRules(JavaPlugin plugin) {
        List<GameRule<Boolean>> gameRulesToSet = List.of(
                GameRule.ANNOUNCE_ADVANCEMENTS,
                GameRule.SHOW_DEATH_MESSAGES,
                GameRule.COMMAND_BLOCK_OUTPUT,
                GameRule.SPECTATORS_GENERATE_CHUNKS,
                GameRule.DO_IMMEDIATE_RESPAWN,
                GameRule.DO_INSOMNIA);
        List<World> worlds = Bukkit.getWorlds();
        worlds.forEach(setGameRule(gameRulesToSet, plugin));
        setSpawnGameRules(plugin);
    }

    private Consumer<World> setGameRule(List<GameRule<Boolean>> gameRules, JavaPlugin plugin) {
        return world -> {
            Bukkit.getGlobalRegionScheduler().run(plugin, task -> {
                gameRules.forEach(gameRule -> world.setGameRule(gameRule, false));
            });
        };
    }

    private void setSpawnGameRules(JavaPlugin plugin) {
        List<GameRule<Boolean>> spawnRules = List.of(
                GameRule.DO_FIRE_TICK,
                GameRule.DO_WEATHER_CYCLE,
                GameRule.DO_DAYLIGHT_CYCLE,
                GameRule.DO_MOB_SPAWNING);
        World spawn = Bukkit.getWorld("spawn_void");
        setGameRule(spawnRules, plugin).accept(spawn);
    }
}
