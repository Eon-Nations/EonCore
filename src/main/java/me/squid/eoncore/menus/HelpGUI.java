package me.squid.eoncore.menus;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HelpGUI {

    public Inventory Main(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, Utils.chat("&a&lHelp Menu"));

        Utils.createItem(inv, Material.PLAYER_HEAD, 1, 5, String.format("%s%s", ChatColor.GREEN, p.getName()),
                "&bHealth: " + p.getHealth(),
                "&bFood Level: " + p.getFoodLevel(),
                "&bRank: " + StringUtils.capitalize(EonCore.getPerms().getUserManager().getUser(p.getUniqueId()).getPrimaryGroup()));
        Utils.createItem(inv, Material.DIAMOND_HOE, 1, 20, "&a&lJobs", "&fGet money for doing in game tasks");
        Utils.createItem(inv, Material.EXPERIENCE_BOTTLE, 1, 23, "&5&lKits", "&fHere you can find kits that you claim for your rank");
        Utils.createItem(inv, Material.OAK_LEAVES, 1, 26, "&2&lWild", "&fClick here to be teleported to the wild");
        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 1, 38, "&a&lVoting", "&fGet rewards for just a few clicks");
        Utils.createItem(inv, Material.SLIME_BALL, 1, 41, "&a&lMcMMO", "&fUnlock abilities and skills to do more cool stuff");
        Utils.createItem(inv, Material.ZOMBIE_HEAD, 1, 44, "&a&lMob Arena", "&fPvE Experience with friends for rewards");
        Utils.makeDummySlots(inv);

        return inv;
    }

}
