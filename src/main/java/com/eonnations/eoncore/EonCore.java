package com.eonnations.eoncore;

import org.bukkit.Bukkit;
import org.eonnations.eonpluginapi.EonPlugin;

import com.eonnations.eoncore.node.Node;
import com.eonnations.eoncore.utils.WorldLoader;

public class EonCore extends EonPlugin {

    public EonCore() {
        super();
    }

    @Override
    public void load() { }

    @Override
    public void setup() {
        saveDefaultConfig();
        WorldLoader.initializeWorlds();
        Node.registerChestPlaceListener();
    }

    @Override
    public void cleanup() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
