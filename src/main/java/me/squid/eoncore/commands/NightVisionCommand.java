package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Messaging;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVisionCommand implements CommandExecutor {
    EonCore plugin;
    static final String OTHERS_IMMUNE_NODE = "eoncommands.nightvision.others";

    public NightVisionCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("nightvision").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0) {
                toggleNightVision(p);
            } else if (args.length == 1 && sender.hasPermission(OTHERS_IMMUNE_NODE)) {
                FunctionalBukkit.getPlayerOrSendMessage(p, this::toggleNightVision, args[0]);
            }
        }
        return true;
    }

    private void toggleNightVision(Player player) {
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            Messaging.sendNationsMessage(player, plugin.getConfig().getString("NV-Off"));
        } else {
            PotionEffect nightVision = new PotionEffect(PotionEffectType.NIGHT_VISION, Short.MAX_VALUE, 1, false, false);
            player.addPotionEffect(nightVision);
            Messaging.sendNationsMessage(player, plugin.getConfig().getString("NV-Message"));
        }
    }
}
