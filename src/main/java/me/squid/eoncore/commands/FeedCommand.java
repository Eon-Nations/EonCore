package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    EonCore plugin;
    CooldownManager cooldownManager;

    public FeedCommand(EonCore plugin) {
        this.plugin = plugin;
        cooldownManager = new CooldownManager();
        plugin.getCommand("feed").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p) {
            if (args.length == 0) {
                if (!p.hasPermission(getImmuneCooldownNode())) {
                    int minutes = p.hasPermission("eoncommands.feed.5") ? 5 : 10;
                    plugin.getLogger().info("Feed Minutes: " + minutes);
                    Cooldown cooldown = new Cooldown(p.getUniqueId(), minutes * 60L * 1000L, System.currentTimeMillis());
                    if (!cooldownManager.hasCooldown(p.getUniqueId())) {
                        cooldownManager.add(cooldown);
                        p.sendMessage(Utils.chat(plugin.getConfig().getString("Feed-Message")));
                        p.setFoodLevel(20);
                        p.setSaturation(10);
                    } else p.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Feed-Cooldown-Message")));
                } else {
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Feed-Message")));
                    p.setFoodLevel(20);
                    p.setSaturation(10);
                }
            } else if (args.length == 1 && p.hasPermission(getOthersPermNode())) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    target.sendMessage(Utils.chat(plugin.getConfig().getString("Feed-Message")));
                    target.setFoodLevel(20);
                    target.setSaturation(10);
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Feed-Other")
                            .replace("<target>", target.getName())));
                } else {
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Target-Null")));
                }
            } else {
                p.sendMessage(Bukkit.getPermissionMessage());
            }
        }
        return true;
    }

    public String getOthersPermNode(){
        return "eoncommands.feed.others";
    }

    public String getImmuneCooldownNode() {
        return "eoncommands.feed.cooldown.immune";
    }
}
