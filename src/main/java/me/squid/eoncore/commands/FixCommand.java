package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.tasks.FixCommandCooldownTask;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class FixCommand implements CommandExecutor {

    EonCore plugin;

    private static List<Player> list = new ArrayList<>();

    public FixCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("fix").setExecutor(this);
        plugin.getCommand("fix").setTabCompleter(getTabComplete());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (list.contains(p)) {
                p.sendMessage(Utils.chat(EonCore.prefix + "&7You are still on cooldown"));
                return true;
            } else {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("hand") || args[0].equalsIgnoreCase("all")) {
                        if (args[0].equalsIgnoreCase("hand")) {
                            if (p.hasPermission("eoncommands.fix.hand")) {
                                ItemStack item = p.getInventory().getItemInMainHand();
                                Damageable damageable = (Damageable) item.getItemMeta();
                                try {
                                    damageable.setDamage(0);
                                    item.setItemMeta((ItemMeta) damageable);
                                    p.sendMessage(Utils.chat(EonCore.prefix + "&7Item Repaired"));
                                    if (!p.hasPermission("eoncommands.fix.cooldown.immune")) {
                                        list.add(p);
                                        new FixCommandCooldownTask(p).runTaskLater(plugin, 12000L);
                                    }
                                    return true;
                                }
                                catch (NullPointerException e) {
                                    p.sendMessage(Utils.chat(EonCore.prefix + "&bNo item in hand found"));
                                }
                            } else p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
                        }
                        if (args[0].equalsIgnoreCase("all")) {
                            if (p.hasPermission("eoncommands.fix.all")) {
                                for (ItemStack item : p.getInventory().getContents()) {
                                    if (item != null && !item.getType().equals(Material.AIR)) {
                                        Damageable damageable = (Damageable) item.getItemMeta();
                                        damageable.setDamage(0);
                                        item.setItemMeta(damageable);
                                    }
                                }
                                p.sendMessage(Utils.chat(EonCore.prefix + "&7All items in inventory repaired"));
                                if (!p.hasPermission("eoncommands.fix.cooldown.immune")) {
                                    list.add(p);
                                    new FixCommandCooldownTask(p).runTaskLater(plugin, 12000L);
                                }
                                return true;
                            } else p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
                        }
                    } else p.sendMessage(Utils.chat(EonCore.prefix + "&7Usage: /fix hand/all"));
                } else p.sendMessage(Utils.chat(EonCore.prefix + "&7Usage: /fix hand/all"));
            }
        }

        return true;
    }

    public TabCompleter getTabComplete() {
        return ((sender, command, alias, args) -> {
            List<String> list1 = new ArrayList<>();
            list1.add("hand");
            list1.add("all");
            return list1;
        });
    }

    public static void removePlayerFromList(Player p) {
        list.remove(p);
    }
}
