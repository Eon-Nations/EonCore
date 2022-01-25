package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReviveCommand implements CommandExecutor, Listener {

    EonCore plugin;
    private final HashMap<Player, List<ItemStack>> items = new HashMap<>();

    public ReviveCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("revive").setExecutor(this);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                List<ItemStack> targetItems = items.remove(target);
                targetItems.forEach(item -> target.getInventory().addItem(item));
                target.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Target-Success-Revive-Message")));
                sender.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Success-Revive-Message")));
            } else sender.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Target-Null")));
        }
        return true;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        items.put(e.getEntity(), e.getDrops());

        Location deathLocation = e.getPlayer().getLocation();
        // Log the death location in case a bug happens and further investigation should be added
        plugin.getLogger().info("Player died at: x=" + deathLocation.getX() + " y=" + deathLocation.getY() +
                " z=" + deathLocation.getZ() + " world=" + deathLocation.getWorld().getName());
    }
}
