package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.List;

public class FixCommand implements CommandExecutor {

    EonCore plugin;
    long cooldownLength;
    final CooldownManager cooldownManager = new CooldownManager();

    public FixCommand(EonCore plugin) {
        this.plugin = plugin;
        this.cooldownLength = 10L * 60L * 1000L; // 10 Minutes converted to seconds converted to milliseconds
        plugin.getCommand("fix").setExecutor(this);
        plugin.getCommand("fix").setTabCompleter(getTabComplete());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            if (cooldownManager.hasCooldown(p.getUniqueId())) {
                Cooldown cooldown = cooldownManager.getCooldown(p.getUniqueId());
                String timeRemaining = Utils.getFormattedTimeString(cooldown.getTimeRemaining());
                p.sendMessage(Utils.getPrefix("nations").append(Component.text("You can use /fix in " + timeRemaining)));
                return true;
            }

            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("hand")) {
                    if (p.hasPermission("eoncommands.fix.hand")) {
                        ItemStack item = p.getInventory().getItemInMainHand();
                        if (!applyFix(item)) {
                            p.sendMessage(Utils.chat(EonCore.prefix + "&bNo item in hand found"));
                        } else {
                            p.sendMessage(Utils.chat(EonCore.prefix + "&7Item Repaired"));
                            addCooldownToPlayer(p);
                        }
                    } else p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
                } else if (args[0].equalsIgnoreCase("all")) {
                    if (p.hasPermission("eoncommands.fix.all")) {
                        for (ItemStack item : p.getInventory().getContents()) {
                            if (item != null && !item.getType().equals(Material.AIR)) {
                                applyFix(item);
                            }
                        }
                        p.sendMessage(Utils.chat(EonCore.prefix + "&7All items in inventory repaired"));
                        addCooldownToPlayer(p);
                        return true;
                    } else p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
                }
            } else p.sendMessage(Utils.chat(EonCore.prefix + "&7Usage: /fix hand/all"));
        }
        return true;
    }

    public void addCooldownToPlayer(Player p) {
        if (!p.hasPermission("eoncommands.fix.cooldown.immune")) {
            Cooldown cooldown = new Cooldown(p.getUniqueId(), cooldownLength, System.currentTimeMillis());
            cooldownManager.add(cooldown);
        }
    }

    public boolean applyFix(ItemStack item) {
        Damageable damageable = (Damageable) item.getItemMeta();
        try {
            damageable.setDamage(0);
            item.setItemMeta(damageable);
            return true;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public TabCompleter getTabComplete() {
        return ((sender, command, alias, args) -> {
            List<String> list1 = new ArrayList<>();
            list1.add("hand");
            list1.add("all");
            return list1;
        });
    }
}
