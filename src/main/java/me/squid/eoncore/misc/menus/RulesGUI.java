package me.squid.eoncore.misc.menus;

import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class RulesGUI {

    public Inventory Categories(){
        Inventory inv = Bukkit.createInventory(null, 9, Utils.chat("&5&lEon Rules"));

        Utils.createItem(inv, Material.BLUE_WOOL, 1, 4, "&b&lChat Rules");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 5, "&6&lGeneral Rules");
        Utils.createItem(inv, Material.PURPLE_WOOL, 1, 6, "&5&lMarket Rules");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory ChatRules() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&b&lChat Rules"));

        Utils.createItem(inv, Material.MAGENTA_WOOL, 1, 9, "&6Advertising other servers/products are not allowed", "&f(30d ban -> IP Ban)");
        Utils.createItem(inv, Material.ORANGE_WOOL, 1, 14, "&6Harassing players/staff members or using offensive language is not allowed", "&f(2 Verbal Warnings -> 1h mute)");
        // Rule for Spam: No inedible spam allowed (1 Verbal Warning -> Perm Mute)
        Utils.createItem(inv, Material.GREEN_WOOL, 1, 18, "&6DoX and DDoS threats are not allowed", "&f(Perm Ban)");

        Utils.createItem(inv, Material.BARRIER, 1, 23, "&4Go Back");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory GeneralRules() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&6&lGeneral Rules"));

        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 1, "&6Exploiting bugs including duping is not allowed", "&f(14d ban -> IP Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 2, "&6Evasion of any punishment is not allowed", "&f(Perm IP Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 4, "&6Forging a ban is not allowed", "&f(7d ban -> 30d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 5, "&6Inappropriate names are not allowed", "&f(Kick -> 5d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 6, "&6Use of a hacked client is not allowed", "&f(30d ban -> IP Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 7, "&6No offensive public builds", "&f(7-30d ban)");

        Utils.createItem(inv, Material.BARRIER, 1, 23, "&4Go Back");
        Utils.makeDummySlots(inv);

        return inv;
    }

}
