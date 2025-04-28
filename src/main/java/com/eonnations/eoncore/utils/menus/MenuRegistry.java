package com.eonnations.eoncore.utils.menus;

import io.vavr.Tuple2;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.Arrays;

public class MenuRegistry {
    private final Map<String, YamlConfiguration> menus;

    public static Tuple2<String, YamlConfiguration> parseMenu(File menuFile) {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(menuFile);
        return new Tuple2<>(menuFile.getName().split("\\.")[0], configuration);
    }

    public MenuRegistry(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "menus");
        menus = List.ofAll(Arrays.stream(file.listFiles()))
                .map(MenuRegistry::parseMenu)
                .toMap(Tuple2::_1, Tuple2::_2);
    }

    public Option<YamlConfiguration> getMenu(String menuName) {
        return menus.get(menuName);
    }
}
