package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.enginehub.piston.CommandManager;

import java.util.Objects;

public class ClaimCommand implements CommandExecutor {

    EonCore plugin;
    CooldownManager cooldownManager;

    public ClaimCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("shovel")).setExecutor(this);
        cooldownManager = new CooldownManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (!cooldownManager.hasCooldown(player.getUniqueId())) {
                // Create a new ItemStack
                ItemStack goldShovel = new ItemStack(Material.GOLDEN_SHOVEL);
                ItemMeta meta = goldShovel.getItemMeta();
                meta.setDisplayName(Utils.chat("&6&lClaim Shovel"));
                goldShovel.setItemMeta(meta);

                // Give the player our items (comma-seperated list of all ItemStack)
                player.getInventory().addItem(goldShovel);
                player.sendMessage(Utils.chat(EonCore.prefix + "&7Golden shovel"));
            }
        }

        return true;
    }

}
