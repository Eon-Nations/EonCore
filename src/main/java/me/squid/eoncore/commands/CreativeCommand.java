package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CreativeCommand implements CommandExecutor {

    EonCore plugin;

    public CreativeCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("gmc").setExecutor(this);
    }

    private void sendPermPlayerMessage(Player p) {
        String message = Utils.chat(plugin.getConfig().getString("Creative-Other")
                .replace("<target>", p.getName()));
        p.sendMessage(message);
    }

    private void setPlayerToCreative(Player p, Optional<Player> sender) {
        p.setGameMode(GameMode.CREATIVE);
        p.sendMessage(Utils.chat(plugin.getConfig().getString("Creative-Message")));
        sender.ifPresent(this::sendPermPlayerMessage);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player p) {
            if (args.length == 0){
                setPlayerToCreative(p, Optional.empty());
            } else if (args.length == 1 && p.hasPermission(getOthersPermNode())) {
                Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0]);
                maybeTarget.ifPresent(player -> setPlayerToCreative(player, Optional.of(p)));
            }
        }
        return true;
    }

    public String getOthersPermNode(){
        return "eoncommands.gmc.others";
    }
}
