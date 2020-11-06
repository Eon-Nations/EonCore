package me.squid.eoncore.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class BanMessages {

    private static File file = new File(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("EonCore")).getDataFolder(), "BanMessages.yml");
    private static FileConfiguration fileConfig;

    public void setupFile(){
        if (!file.exists()){
            boolean success;
            try {
                success = file.createNewFile();
                System.out.println("BanMessages File creation: " + success);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig(){
        return fileConfig;
    }

    public void saveFile() throws IOException {
        if (file != null){
            fileConfig.save(file);
        }
    }

    public void reload(){
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void loadDefaults(){
        getConfig().addDefault("Mute-Message", "&7[&a&lEon Moderation&7] &aYou have been muted for <reason> by <player> for <minutes> minutes");
        getConfig().addDefault("Muted-Message", "&7[&a&lEon Moderation&7] &4You are muted");
        getConfig().addDefault("PermMuted-Message", "&7[&a&lEon Moderation&7] &4You have been permanently muted");
        getConfig().addDefault("Unmute-Message", "&7[&a&lEon Moderation&7] &aYou have been unmuted!");
        getConfig().addDefault("Muted-Broadcast", "&7[&a&lEon Moderation&7] &a<name> has been muted for <reason> by <player> for <length>");
        getConfig().addDefault("Ban-Broadcast", "&7[&a&lEon Moderation&7] &a<name> has been banned for <reason> for <time>");
        getConfig().addDefault("PermBan-Broadcast", "&7[&a&lEon Moderation&7] &a<name> has been banned for <reason>");
        getConfig().addDefault("PermMute-Broadcast", "&7[&a&lEon Moderation&7] &a<name> has been permmuted for <reason>");
        getConfig().addDefault("Unmuted-Broadcast", "&7[&a&lEon Moderation&7] &a<player> has been successfully unmuted");
    }
}
