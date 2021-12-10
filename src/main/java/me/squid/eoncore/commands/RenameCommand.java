package me.squid.eoncore.commands;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class RenameCommand implements CommandExecutor {

    EonCore plugin;
    String prefix = "&7[&5&lRename&r&7] ";

    public RenameCommand(EonCore plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("rename")).setExecutor(this);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String label, String[] args) {

        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (p.hasPermission(getPermissionNode())) {
                String name = getItemName(args);
                ItemStack item = p.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();

                if (name.equalsIgnoreCase("")) meta.setDisplayName(null);
                else meta.setDisplayName(name);
                item.setItemMeta(meta);
                p.sendMessage(Utils.chat(prefix + "&bItem name changed!"));

            } else p.sendMessage(Utils.chat(plugin.getConfig().getString("No-Perms")));
        } else System.out.println("No");

        return true;
    }

    public String getPermissionNode() {
        return "eoncommands.rename";
    }

    private String getItemName(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(" ");
        }
        String allArgs = sb.toString().trim();
        return ChatColor.translateAlternateColorCodes('&', allArgs);
    }
}
