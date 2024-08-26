package com.eonnations.eoncore.common;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public abstract class EonPlugin extends JavaPlugin {

    public abstract void load();
    public abstract void setup();
    public abstract void cleanup();

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onEnable() {
        setup();
    }

    @Override
    public void onDisable() {
        cleanup();
    }
}
