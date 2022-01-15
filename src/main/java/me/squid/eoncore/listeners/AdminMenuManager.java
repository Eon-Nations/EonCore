package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.menus.AdminGUI;
import me.squid.eoncore.managers.MutedManager;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
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
    MutedManager mutedManager;
    AdminGUI adminGUI;

    final String prefix = "&7[&b&lEon Admin&7] &r";

    public AdminMenuManager(EonCore plugin, MutedManager mutedManager, AdminGUI adminGUI) {
        this.plugin = plugin;
        this.mutedManager = mutedManager;
        this.adminGUI = adminGUI;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onAdminMenuClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        // Main Menu
        if (e.getView().title().equals(Utils.chat("&5&lEon Admin GUI"))) {
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
        if (e.getView().title().equals(Utils.chat("&5&lEon Management"))) {
            if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                p.openInventory(adminGUI.PeopleOptionsGUI(e.getCurrentItem()));
            } else p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
            e.setCancelled(true);
        }

        // Weather GUI
        if (e.getView().title().equals(Utils.chat("&b&lWeather/Time Menu"))){
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
        if (e.getView().title().equals(Utils.chat("&a&lPlayer Options"))) {
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
        if (e.getView().title().equals(Utils.chat("&bBan Reasons"))) {
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
        if (e.getView().title().equals(Utils.chat("&bMute Reasons"))) {
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

        if (e.getView().title().equals(Utils.chat("&a&lLength"))) {
            // Action and Reason from the Lore of the item
            String reason = e.getView().getItem(4).getItemMeta().getLore().get(0).split(": ")[1];
            String action = e.getView().getItem(4).getItemMeta().getLore().get(1).split(": ")[1];
            String u = e.getView().getItem(4).getItemMeta().getDisplayName().split(": ")[1];
            UUID uuid = UUID.fromString(u);
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
            long length = 0;

            if (action.equals("mute")) {
                long hour = 60 * 1000 * 60;
                switch (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())) {
                    case "1 Hour" -> length = hour;
                    case "3 Hours" -> length = hour * 3;
                    case "12 Hours" -> length = hour * 12;
                    case "24 Hours" -> length = hour * 24;
                    case "7 Days" -> length = hour * 24 * 7;
                    case "Perm Mute" -> length = -1;
                }
                Cooldown cooldown = new Cooldown(uuid, length, System.currentTimeMillis());
                mutedManager.addCooldown(cooldown);
                if (length == -1 && player.isOnline()) {
                    player.getPlayer().sendMessage(Utils.getPrefix("moderation").append(
                            Utils.chat("&aYou have been permanently muted for " + reason + " by " + p.getName())));
                }
                if (player.getPlayer() != null) {
                    player.getPlayer().sendMessage(Utils.getPrefix("moderation")
                            .append(Utils.chat("&aYou have been muted for " + reason + " by " + p.getName() + " for " + Utils.getFormattedTimeString(length))));
                }
                for (Player staff : Bukkit.getOnlinePlayers()) {
                    if (staff.hasPermission("eoncommands.staffchat")) staff.sendMessage(Utils.chat("&7[&a&lEon Moderation&7] &a" + player.getName() + " has been muted for " + reason + " by " + p.getName() + " for "
                            + Utils.getFormattedTimeString(length)));
                }
            } else {
                long day = 60 * 1000 * 60 * 24;
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
                player.banPlayer(reason, new Date(System.currentTimeMillis() + day), p.getName(), true);
                Bukkit.getOnlinePlayers().stream().filter(online -> online.hasPermission("eoncommands.staffchat"))
                        .forEach(staff -> staff.sendMessage(Utils.getPrefix("moderation").append(
                                Utils.chat("&a" + player.getName() + " has been banned for " + reason + " by " + p.getName() + " for " +
                                        ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())))));
            }
            e.setCancelled(true);
        }

        if (e.getView().title().equals(Utils.chat("&b&lMuted Chat"))) {
            if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                p.openInventory(adminGUI.mutedOptions(e.getCurrentItem()));
            }
            e.setCancelled(true);
        }

        if (e.getView().title().equals(Utils.chat("&b&lMuted Options"))) {
            UUID uuid = UUID.fromString(e.getView().getItem(4).getLore().get(1).split(": ")[1]);
            switch (e.getCurrentItem().getType()) {
                case EMERALD_BLOCK -> {
                    mutedManager.removePlayer(uuid);
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    p.closeInventory();
                    p.sendMessage(Utils.chat("&aPlayer successfully unmuted."));
                }
                case RED_CONCRETE -> {
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                }
            }
            e.setCancelled(true);
        }
    }
}
