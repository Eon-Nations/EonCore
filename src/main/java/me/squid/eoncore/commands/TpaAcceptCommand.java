package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.tasks.TpaCooldownTask;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TpaAcceptCommand implements CommandExecutor {

    EonCore plugin;

    final String prefix = "&7[&5&lEon Survival&7]&r ";

    public TpaAcceptCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("tpaccept")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (TpaCommand.getRequests().containsKey(p.getUniqueId())) {
                if (!p.hasPermission("eoncommands.tpa.cooldown.bypass")) {
                    new TpaCooldownTask(plugin, Bukkit.getPlayer(TpaCommand.getRequests().get(p.getUniqueId())), p).run();
                } else if (p.hasPermission("eoncommands.tpa.cooldown.bypass")){
                    new TpaCooldownTask(plugin, Bukkit.getPlayer(TpaCommand.getRequests().get(p.getUniqueId())), p).runTaskLater(plugin, plugin.getConfig().getInt("Delay-Seconds") * 20);
                    Objects.requireNonNull(Bukkit.getPlayer(TpaCommand.getRequests().get(p.getUniqueId()))).sendMessage(Utils.chat(prefix + Objects.requireNonNull(plugin.getConfig().getString("Cooldown-Teleport-Message"))
                    .replace("<seconds>", String.valueOf(plugin.getConfig().getInt("Delay-Seconds")))));
                }
            } else {
                p.sendMessage(Utils.chat(prefix + plugin.getConfig().getString("No-Pending-Requests")));
            }
        }

        return true;
    }
}
