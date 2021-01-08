package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.menus.AdminGUI;
import me.squid.eoncore.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.UUID;


public class AdminMenuManager implements Listener {

    EonCore plugin;
    AdminGUI adminGUI = new AdminGUI();
    public static CooldownManager cooldownManager = new CooldownManager();

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

        // People Options
        if (e.getView().getTitle().equalsIgnoreCase(Utils.chat("&a&lPlayer Options"))) {
            ItemStack head = e.getView().getItem(4);
            String u = head.getItemMeta().getLore().get(3).split(": ")[1];
            UUID uuid = UUID.fromString(u);
            Player target = Bukkit.getPlayer(uuid);

            switch (e.getCurrentItem().getType()) {
                case WOODEN_AXE:
                    p.openInventory(adminGUI.getBanReasons(uuid));
                    break;
                case GOLDEN_CARROT:
                    target.setFoodLevel(20);
                    p.sendMessage(Utils.chat(prefix + "&7Fed " + target.getName()));
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    break;
                case ENDER_PEARL:
                    p.teleportAsync(target.getLocation());
                    break;
                case WOODEN_SHOVEL:
                    p.openInventory(adminGUI.getMuteReasons(uuid));
                    break;
            }
            e.setCancelled(true);
        }

        // Reasons to ban on the server
        if (e.getView().getTitle().equalsIgnoreCase(Utils.chat("&bBan Reasons"))) {
            if (e.getCurrentItem().getType() == Material.WOODEN_SHOVEL) {
                e.setCancelled(true);
            } else {
                String u = e.getView().getItem(4).getItemMeta().getLore().get(0).split(": ")[1];
                UUID uuid = UUID.fromString(u);
                String reason = e.getCurrentItem().getItemMeta().getDisplayName();
                String action = "ban";
                p.openInventory(adminGUI.getLengthGUI(uuid, action, reason));
            }
            e.setCancelled(true);
        }

        // Reasons to mute on the server
        if (e.getView().getTitle().equalsIgnoreCase(Utils.chat("&bMute Reasons"))) {
            if (e.getCurrentItem().getType() == Material.WOODEN_SHOVEL) {
                e.setCancelled(true);
            } else {
                String u = e.getView().getItem(4).getItemMeta().getLore().get(0).split(": ")[1];
                UUID uuid = UUID.fromString(u);
                String reason = e.getCurrentItem().getItemMeta().getDisplayName();
                String action = "mute";
                p.openInventory(adminGUI.getLengthGUI(uuid, action, reason));
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equalsIgnoreCase(Utils.chat("&aLength"))) {
            // Action and Reason from the Lore of the item
            String reason = e.getView().getItem(4).getItemMeta().getLore().get(0).split(": ")[1];
            String action = e.getView().getItem(4).getItemMeta().getLore().get(1).split(": ")[1];
            String u = e.getView().getItem(4).getItemMeta().getDisplayName().split(": ")[1];
            UUID uuid = UUID.fromString(u);
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            long length = 0;
            Cooldown cooldown;

            if (action.equals("mute")) {
                switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) {
                    case "1 Hour":
                        length = 3600000;
                        break;
                    case "3 Hours":
                        length = 10800000;
                        break;
                    case "12 Hours":
                        length = 43200000;
                        break;
                    case "24 Hours":
                        length = 86400000;
                        break;
                    case "7 Days":
                        length = 604800000;
                        break;
                    case "Perm Mute":
                        // Permanently Mute
                        break;
                }
                cooldown = new Cooldown(uuid, length, System.currentTimeMillis());
                cooldownManager.add(cooldown);
                if (player.getPlayer() != null) {
                    player.getPlayer().sendMessage(Utils.chat("&7[&a&lEon Moderation&7] &aYou have been muted for " + reason + " by " + p.getName() + " for " + length / 60000 + " minutes"));
                }
                for (Player p1 : Bukkit.getOnlinePlayers()) {
                    if (p1.hasPermission("eoncommands.staffchat")) p1.sendMessage(Utils.chat("&7[&a&lEon Moderation&7] &a" + player.getName() + " has been muted for " + reason + " by " + p.getName() + " for "
                            + length / 60000 + " minutes"));
                }
            } else {
                long day = 86400000;
                switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) {
                    case "1 Day":
                        break;
                    case "3 Days":
                        day *= 3;
                        break;
                    case "7 Days":
                        day *= 7;
                        break;
                    case "14 Days":
                        day *= 14;
                        break;
                    case "30 Days":
                        day *= 30;
                        break;
                    case "Perm Ban":
                        player.banPlayer(reason, null, p.getName(), true);
                        break;
                }
                player.banPlayer(Utils.chat(reason), new Date(System.currentTimeMillis() + day), p.getName(), true);
                for (Player p1 : Bukkit.getOnlinePlayers()) {
                    if (p1.hasPermission("eoncommands.staffchat")) p1.sendMessage(Utils.chat("&7[&a&lEon Moderation&7] &a" + player.getName() + " has been banned for " + reason + " by " + p.getName() + " for " +
                            ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())));
                }
            }
            e.setCancelled(true);
        }
    }
}
