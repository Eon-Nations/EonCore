package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.messaging.Messaging;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FixCommand implements CommandExecutor {

    EonCore plugin;
    // 10 Minutes converted to second converted to milliseconds
    static final long COOLDOWN_LENGTH = 10L * 60L * 1000L;
    final CooldownManager cooldownManager = new CooldownManager();

    public FixCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("fix").setExecutor(this);
        plugin.getCommand("fix").setTabCompleter(getTabComplete());
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            if (cooldownManager.hasCooldown(p.getUniqueId())) {
                Cooldown cooldown = cooldownManager.getCooldown(p.getUniqueId());
                String timeRemaining = Utils.getFormattedTimeString(cooldown.getTimeRemaining());
                Messaging.sendNationsMessage(p, "You can /fix in " + timeRemaining);
                return false;
            }

            if (args.length == 1) {
                String hand = args[0];
                if (hand.equals("hand")) {
                    fixItemInHand(p);
                } else if (hand.equals("all")) {
                    fixAllItems(p);
                }
            } else p.sendMessage(Utils.chat(Utils.getPrefix("nations") + "&7Usage: /fix hand/all"));
        }
        return true;
    }

    private void fixAllItems(Player player) {
        if (!player.hasPermission("eoncommands.fix.all")) {
            Messaging.sendNationsMessage(player, "You do not have permission to fix all items");
            return;
        }
        ItemStack[] items = player.getInventory().getContents();
        long amountRepaired = Arrays.stream(items)
                .filter(Objects::nonNull)
                .filter(item -> !item.getType().equals(Material.AIR))
                .map(this::applyFix)
                .count();
        Messaging.sendNationsMessage(player, amountRepaired + " items in inventory repaired");
        addCooldownToPlayer(player);
    }

    private void fixItemInHand(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!applyFix(item)) {
            Messaging.sendNationsMessage(player, "No item in hand found");
        } else {
            Messaging.sendNationsMessage(player, "Item repaired");
            addCooldownToPlayer(player);
        }
    }

    public void addCooldownToPlayer(Player p) {
        if (!p.hasPermission("eoncommands.fix.cooldown.immune")) {
            Cooldown cooldown = new Cooldown(p.getUniqueId(), COOLDOWN_LENGTH, System.currentTimeMillis());
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
        return ((sender, command, alias, args) -> List.of("hand", "all"));
    }
}
