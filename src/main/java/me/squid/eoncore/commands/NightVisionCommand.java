package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class NightVisionCommand implements CommandExecutor {

    EonCore plugin;

    public NightVisionCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("nightvision")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length == 0){
            if (sender instanceof Player){
                Player p = (Player) sender;
                PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false);
                if (!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                    p.addPotionEffect(nightVision);
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("NV-Message")));
                } else {
                    p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    p.sendMessage(Utils.chat(plugin.getConfig().getString("NV-Off")));
                }
            }
        } else if (args.length == 1){
            if (sender.hasPermission(getOthersPermNode())) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null && !target.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                    PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1, false, false);
                    target.sendMessage(Utils.chat(plugin.getConfig().getString("NV-Message")));
                    target.addPotionEffect(nightVision);
                } else if (target != null && target.hasPotionEffect(PotionEffectType.NIGHT_VISION)){
                    target.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    target.sendMessage(Utils.chat(plugin.getConfig().getString("NV-Off")));
                    sender.sendMessage(Utils.chat(Objects.requireNonNull(plugin.getConfig().getString("Target-NV-Message"))
                            .replace("<target>", target.getName())));
                }
            } else {
                sender.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
            }
        }

        return true;
    }

    public String getPermissionNode(){
        return "eoncommands.nightvision";
    }

    public String getOthersPermNode(){
        return "eoncommands.nightvision.other";
    }
}
