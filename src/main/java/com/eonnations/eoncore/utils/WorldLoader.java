package com.eonnations.eoncore.utils;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldLoader {

    public static void initializeWorlds() {
        WorldLoader worldLoader = new WorldLoader();
        worldLoader.loadWorlds();
        worldLoader.setupGameRules();
    }

    public void loadWorlds() {
        try {
            new WorldCreator("spawn_void").generator(new VoidChunkGenerator()).createWorld();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupGameRules() {
        List<GameRule<Boolean>> gameRulesToSet = List.of(
                GameRule.ANNOUNCE_ADVANCEMENTS,
                GameRule.SHOW_DEATH_MESSAGES,
                GameRule.COMMAND_BLOCK_OUTPUT,
                GameRule.SPECTATORS_GENERATE_CHUNKS,
                GameRule.DO_IMMEDIATE_RESPAWN,
                GameRule.DO_INSOMNIA);
        List<World> worlds = Bukkit.getWorlds();
        worlds.forEach(setGameRule(gameRulesToSet));
        setSpawnGameRules();
    }

    private Consumer<World> setGameRule(List<GameRule<Boolean>> gameRules) {
        return world -> gameRules.forEach(gameRule -> world.setGameRule(gameRule, false));
    }

    private void setSpawnGameRules() {
        List<GameRule<Boolean>> spawnRules = List.of(
                GameRule.DO_FIRE_TICK,
                GameRule.DO_WEATHER_CYCLE,
                GameRule.DO_DAYLIGHT_CYCLE,
                GameRule.DO_MOB_SPAWNING);
        World spawn = Bukkit.getWorld("spawn_void");
        setGameRule(spawnRules).accept(spawn);
    }
}
