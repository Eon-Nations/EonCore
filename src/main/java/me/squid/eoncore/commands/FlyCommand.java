package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.FunctionalBukkit;
import me.squid.eoncore.utils.Messaging;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class FlyCommand implements CommandExecutor {
    EonCore plugin;
    private final ArrayList<Player> flyingPlayers = new ArrayList<>();

    public FlyCommand(EonCore plugin) {
        this.plugin = plugin;
        plugin.getCommand("fly").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            toggleFly(p, false);
        } else if (args.length == 1 && p.hasPermission(getOthersPermNode())) {
            Optional<Player> maybeTarget = FunctionalBukkit.getPlayerFromName(args[0]);
            maybeTarget.ifPresentOrElse(target -> toggleFly(target, p.hasPermission(getImmuneNode())), () -> Messaging.sendNullMessage(p, plugin.getConfig()));
        }
        return true;
    }

    private void toggleFly(Player player, boolean immune) {
        if (immune) return;
        if (flyingPlayers.contains(player))
            turnOffFly(player);
        else turnOnFly(player);
    }

    private void turnOnFly(Player player) {
        if (player.getWorld().getName().equals("spawn_void")) return;
        player.setAllowFlight(true);
        Messaging.sendNationsMessage(player, plugin.getConfig().getString("Fly-On"));
        flyingPlayers.add(player);
    }

    private void turnOffFly(Player player) {
        player.setAllowFlight(false);
        Messaging.sendNationsMessage(player, plugin.getConfig().getString("Fly-Off"));
        flyingPlayers.remove(player);
    }

    public String getOthersPermNode() {
        return "eoncommands.fly.others";
    }

    public String getImmuneNode() {
        return "eoncommands.fly.others.immune";
    }
}
