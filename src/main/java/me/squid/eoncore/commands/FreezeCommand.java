package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class FreezeCommand implements CommandExecutor {

    EonCore plugin;

    public FreezeCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("freeze")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player) sender;
            if (p.hasPermission(getPermissionNode())){
                if (args.length == 0){
                    p.sendTitle(Utils.chat("&b&lFreeze People!"), Utils.chat("Usage: /freeze <player>"), 40, 40,20);
                } else if (args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null){
                        if (target.hasPermission(getImmunePermNode())){
                            p.sendMessage(Utils.chat(plugin.getConfig().getString("Freeze-Immune")));
                        } else {
                            if (target.getWalkSpeed() == 0){
                                target.sendMessage(Utils.chat(plugin.getConfig().getString("Target-Unfrozen")));
                                target.setWalkSpeed((float) 0.2);
                                target.setFlySpeed((float) 0.1);
                                target.setCanPickupItems(true);
                                target.removePotionEffect(PotionEffectType.JUMP);
                                p.sendMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Player-UnFrozen"))
                                        .replace("<player>", target.getName())));
                            } else {
                                target.sendMessage(Utils.chat(plugin.getConfig().getString("Target-Frozen")));
                                target.addPotionEffect(PotionEffectType.JUMP.createEffect(Integer.MAX_VALUE, 10000));
                                target.setWalkSpeed(0);
                                target.setFlySpeed(0);
                                target.setCanPickupItems(false);
                                p.sendMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Player-Frozen"))
                                        .replace("<player>", target.getName())));
                            }
                            return true;
                        }
                    } else {
                        p.sendMessage(Utils.chat(plugin.getConfig().getString("Valid-Target")));
                    }
                }
            } else {
                p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
            }
        } else {
            sender.sendMessage(Utils.chat(plugin.getConfig().getString("Console-Message")));
        }
        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.freeze";
    }

    public String getImmunePermNode(){
        return "eoncommands.freeze.immune";
    }
}
