package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.files.BanMessages;
import me.squid.eoncore.files.MutedPlayers;
import me.squid.eoncore.menus.AdminGUI;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class AdminMenuManager implements Listener {

    EonCore plugin;
    AdminGUI adminGUI = new AdminGUI();
    BanMessages banMessages = new BanMessages();
    MutedPlayers mutedPlayers = new MutedPlayers();

    final String prefix = "&7[&b&lEon Admin&7] &r";

    public AdminMenuManager(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onAdminMenuClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        // Main Menu
        if (e.getView().getTitle().equalsIgnoreCase(Utils.chat("&5&lEon Admin GUI"))) {
             switch (e.getCurrentItem().getType()) {
                 case PLAYER_HEAD:
                     p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 1, 1);
                     p.openInventory(adminGUI.PeopleGUI());
                     break;
                 case PAPER:
                     plugin.reloadConfig();
                     p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Reload-Message")));
                     p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 1, 1);
                     break;
                 case CLOCK:
                     p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 1, 1);
                     p.closeInventory();
                     p.openInventory(adminGUI.WeatherTimeGUI(p));
                     break;
             }
             e.setCancelled(true);
        }

        // Player Management Tab
        if (e.getView().getTitle().equalsIgnoreCase(Utils.chat("&5&lEon Management"))) {
            if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                p.openInventory(adminGUI.PeopleOptionsGUI(e.getCurrentItem()));
            } else p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            e.setCancelled(true);
        }

        // Weather GUI
        if (e.getView().getTitle().equalsIgnoreCase(Utils.chat("&b&lWeather/Time Menu"))){
            switch (e.getCurrentItem().getType()){
                case LIGHT_BLUE_CONCRETE:
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 2, 1);
                    p.getWorld().setTime(500);
                    p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Time-Set-Day")));
                    break;
                case GREEN_CONCRETE:
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, SoundCategory.BLOCKS, 1, 1);
                    p.getWorld().setThundering(false);
                    p.getWorld().setStorm(false);
                    p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Weather-Set-Clear")));
                    break;
                case GRAY_CONCRETE:
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1, 1);
                    p.getWorld().setTime(13000);
                    p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Time-Set-Night")));
                    break;
                case LIGHT_GRAY_CONCRETE:
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SoundCategory.BLOCKS, 1, 1);
                    p.getWorld().setStorm(true);
                    p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("Weather-Set-Rain")));
                    break;
                default:
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                    break;
            }
            e.setCancelled(true);
        }
    }
}
