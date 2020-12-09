package me.squid.eoncore.menus;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class HelpGUI {

    public Inventory Main(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, Utils.chat("&a&lHelp Menu"));

        inv.setItem(5, getPlayerHead(p));
        Utils.createItem(inv, Material.DIAMOND_HOE, 1, 20, "&a&lJobs", "&fGet money for doing in game tasks");
        Utils.createItem(inv, Material.EXPERIENCE_BOTTLE, 1, 23, "&5&lKits", "&fHere you can find kits that you claim for your rank");
        Utils.createItem(inv, Material.OAK_LEAVES, 1, 26, "&2&lWild", "&fClick here to be teleported to the wild");
        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 1, 38, "&a&lVoting", "&fGet rewards for just a few clicks");
        Utils.createItem(inv, Material.SLIME_BALL, 1, 41, "&a&lMcMMO", "&fUnlock abilities and skills to do more cool stuff");
        Utils.createItem(inv, Material.ZOMBIE_HEAD, 1, 44, "&a&lMob Arena", "&fPvE Experience with friends for rewards");
        Utils.makeDummySlots(inv);

        return inv;
    }

    private ItemStack getPlayerHead(Player p) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setOwningPlayer(p);
        skull.setDisplayName(p.getName());

        List<String> lore = new ArrayList<>();
        lore.add(Utils.chat("&bHealth: " + p.getHealth()));
        lore.add(Utils.chat("&bFood Level: " + p.getFoodLevel()));
        lore.add(Utils.chat("&bRank: " + StringUtils.capitalize(EonCore.getPerms().getUserManager().getUser(p.getUniqueId()).getPrimaryGroup())));
        skull.setLore(lore);

        item.setItemMeta(skull);
        return item;
    }

}
