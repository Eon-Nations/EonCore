package com.eonnations.eoncore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import com.eonnations.eoncore.common.EonModule;
import com.eonnations.eoncore.utils.WorldLoader;

import io.vavr.collection.List;
import io.vavr.control.Try;

public class EonCore extends JavaPlugin {

    private List<EonModule> loadedModules = registerAllModules();

    private List<EonModule> registerAllModules() {
        Reflections reflections = new Reflections("com.eonnations.eoncore");
        return List.ofAll(reflections.getSubTypesOf(EonModule.class))
            .map(moduleClass -> Try.of(() -> moduleClass.getDeclaredConstructor().newInstance(this)))
            .filter(Try::isSuccess)
            .map(moduleTry -> moduleTry.get());
    }

    @Override
    public void onLoad() {
        loadedModules.forEach(EonModule::load);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        WorldLoader.initializeWorlds();
        loadedModules.forEach(EonModule::setup);
    }

    @Override
    public void onDisable() {
        loadedModules.forEach(EonModule::cleanup);
        Bukkit.getScheduler().cancelTasks(this);
    }
}
