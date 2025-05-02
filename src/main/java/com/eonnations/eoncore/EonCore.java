package com.eonnations.eoncore;

import com.eonnations.eoncore.common.database.sql.Credentials;
import com.eonnations.eoncore.common.database.sql.SQLDatabase;
import com.eonnations.eoncore.utils.menus.MenuRegistry;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import com.eonnations.eoncore.common.EonModule;
import com.eonnations.eoncore.utils.WorldLoader;

import io.vavr.collection.List;
import io.vavr.control.Try;

public class EonCore extends JavaPlugin {
    private List<EonModule> loadedModules;
    @Getter
    private SQLDatabase database;
    @Getter
    private MenuRegistry menuRegistry;

    private List<EonModule> registerAllModules() {
        Reflections reflections = new Reflections("com.eonnations.eoncore");
        return List.ofAll(reflections.getSubTypesOf(EonModule.class))
            .map(moduleClass -> Try.of(() -> moduleClass.getDeclaredConstructor(EonCore.class).newInstance(this))
                    .onFailure(Throwable::printStackTrace))
            .filter(Try::isSuccess)
            .map(Try::get);
    }

    public <T extends EonModule> T getLoadedModule(Class<T> moduleClass) {
        return loadedModules.filter(module -> module.getClass().equals(moduleClass))
                .map(o -> (T) o)
                .single();
    }

    @Override
    public void onLoad() {
        database = new SQLDatabase(Credentials.credentials(this));
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
        menuRegistry = new MenuRegistry(this);
        loadedModules = registerAllModules();
        loadedModules.forEach(EonModule::load);
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();
        saveDefaultConfig();
        WorldLoader.initializeWorlds(this);
        loadedModules.forEach(EonModule::setup);
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
        loadedModules.forEach(EonModule::cleanup);
        Bukkit.getScheduler().cancelTasks(this);
    }
}
