package me.squid.eoncore.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MutedPlayers {

    private static File file = new File(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("EonCore")).getDataFolder(), "mutedplayers.yml");
    private static FileConfiguration fileConfig;

    public void setupFile() {
        if (!file.exists()) {
            boolean success;
            try {
                success = file.createNewFile();
                System.out.println("Muted Players File Creation: " + success);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void saveFile() {
        if (file != null){
            try {
                fileConfig.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reload(){
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig(){
        return fileConfig;
    }

    public void loadDefaults(){
        getConfig().createSection("players");
    }
}
