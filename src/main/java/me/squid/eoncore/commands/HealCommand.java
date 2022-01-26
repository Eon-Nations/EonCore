package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.managers.Cooldown;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    EonCore plugin;
    CooldownManager cooldownManager;

    public HealCommand(EonCore plugin) {
        this.plugin = plugin;
        this.cooldownManager = new CooldownManager();
        plugin.getCommand("heal").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player p){
            if (args.length == 0) {
                if (cooldownManager.hasCooldown(p.getUniqueId())) {
                    Cooldown cooldown = cooldownManager.getCooldown(p.getUniqueId());
                    p.sendMessage(Utils.getPrefix("nations").append(Component.text("You can do /heal in " +
                            Utils.getFormattedTimeString(cooldown.getTimeRemaining()))));
                    return true;
                } else {
                    long minutes = plugin.getConfig().getLong("Heal-Cooldown");
                    Cooldown cooldown = new Cooldown(p.getUniqueId(), minutes * 60L * 1000L, System.currentTimeMillis());
                    cooldownManager.add(cooldown);
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Heal-Message")));
                }
            } else if (args.length == 1){
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null && p.hasPermission(getOthersPermNode())) {
                    target.setFoodLevel(20);
                    target.setHealth(20);
                    target.sendMessage(Utils.chat(plugin.getConfig().getString("Heal-Message")));
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("Heal-Other").replace("<player>", target.getName())));
                } else {
                    p.sendMessage(Utils.chat(EonCore.prefix + plugin.getConfig().getString("Target-Null")));
                }
            }
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.heal";
    }

    public String getOthersPermNode(){
        return "eoncommands.heal.others";
    }
}
