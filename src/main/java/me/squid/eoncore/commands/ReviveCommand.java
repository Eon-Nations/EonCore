package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
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
    private static HashMap<Player, List<ItemStack[]>> items = new HashMap<>();

    public ReviveCommand(EonCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    for (ItemStack[] itemStacks : items.get(p)) {
                        for (ItemStack item : itemStacks) {
                            target.getInventory().addItem(item);
                        }
                    }
                    target.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Target-Success-Revive-Message")));
                    p.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Success-Revive-Message")));
                } else p.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Target-Null")));
            }
        }
        return true;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        List<ItemStack[]> threeTypes = new ArrayList<>();
        threeTypes.add(e.getEntity().getInventory().getArmorContents());
        threeTypes.add(e.getEntity().getInventory().getExtraContents());
        threeTypes.add(e.getEntity().getInventory().getContents());
        items.put(e.getEntity(), threeTypes);
    }
}
