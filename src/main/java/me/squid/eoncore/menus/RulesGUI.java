package me.squid.eoncore.menus;

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

        Utils.createItem(inv, Material.BLUE_WOOL, 1, 1, "&6Indirect advertising is not allowed", "&f(2 Verbal Warnings -> 3h mute)");
        Utils.createItem(inv, Material.MAGENTA_WOOL, 1, 9, "&6Advertising other servers/products are not allowed", "&f(30d ban -> IP Ban)");
        Utils.createItem(inv, Material.BLUE_WOOL, 1, 10, "&6No personal information in public chat", Utils.chat("&f(24h mute -> Perm Mute"));
        Utils.createItem(inv, Material.PURPLE_WOOL, 1, 11, "&6Offensive language is not allowed", Utils.chat("&f(2 Verbal Warnings -> 24h mute)"));
        Utils.createItem(inv, Material.LIGHT_BLUE_WOOL, 1, 12, "&6Bigotry/hate speech is not allowed", Utils.chat("&f(2 Verbal Warnings -> 24h mute)"));
        Utils.createItem(inv, Material.MAGENTA_WOOL, 1, 13, "&6Misleading players is not allowed", Utils.chat("&f(Verbal Warning -> 1h mute)"));
        Utils.createItem(inv, Material.ORANGE_WOOL, 1, 14, "&6Harassing players/staff members is not allowed", Utils.chat("&f(2 Verbal Warnings -> 1h mute)"));
        Utils.createItem(inv, Material.GREEN_WOOL, 1, 15, "&6Excessive swearing is not allowed", Utils.chat("&f(Verbal Warning -> 5h mute)"));
        Utils.createItem(inv, Material.GRAY_WOOL, 1, 16, "&6No political discussions/statements allowed", Utils.chat("&f(2 Verbal Warnings -> 3h mute)"));
        Utils.createItem(inv, Material.RED_WOOL, 1, 17, "&6No sexual statements/discussions in chat", Utils.chat("&f(2 Verbal Warnings -> 3h mute)"));
        Utils.createItem(inv, Material.GREEN_WOOL, 1, 18, "&6DoX and DDoS threats are now allowed", Utils.chat("&f(Perm Ban)"));

        Utils.createItem(inv, Material.BARRIER, 1, 23, "&4Go Back");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory GeneralRules() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&6&lGeneral Rules"));

        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 1, "&6Exploiting bugs including duping is not allowed", "&f(14d ban -> IP Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 2, "&6Ban Evasion is not allowed", "&f(Perm IP Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 3, "&6Mute Evasion is not allowed", "&f(7d ban -> Perm Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 4, "&6Forging a ban is not allowed", "&f(7d ban -> 30d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 5, "&6Inappropriate names are not allowed", "&f(Kick -> 5d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 6, "&6Scamming others is not allowed", "&f(7d ban -> Perm Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 7, "&6Player killing is not allowed", "&f(14d ban -> 60d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 8, "&6Use of a hacked client is not allowed", "&f(30d ban -> IP Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 9, "&6No building near someone without permission", "&f(250 blocks)", "&f(1-30d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 12, "&6No harassing other players", "&f(1-30d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 13, "&6No inappropriate public builds", "&f(7-30d ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 14, "&6X-Ray Texture Packs/Mods are not allowed", "&f(14d -> Perm Ban)");
        Utils.createItem(inv, Material.YELLOW_WOOL, 1, 15, "&6No griefing other players. Even if it is unclaimed", "&f(7d ban -> Perm Ban)");

        Utils.createItem(inv, Material.BARRIER, 1, 23, "&4Go Back");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory MarketRules() {
        Inventory inv = Bukkit.createInventory(null, 27, Utils.chat("&5&lMarket Rules"));

        Utils.createItem(inv, Material.PURPLE_WOOL, 1, 13, "&6Only shops allowed in the shop plots", "&f(Warning -> Removal of Shop Perms)");
        Utils.createItem(inv, Material.PURPLE_WOOL, 1, 14, "&6No more than 5 hoppers and 10 item frames in the shop", "&f(Warning -> Removal of Hoppers/Item Frames)");
        Utils.createItem(inv, Material.PURPLE_WOOL, 1, 15, "&6No political builds allowed", "&f(Warning -> Removal of Shop Perms)");

        Utils.createItem(inv, Material.BARRIER, 1, 23, "&4Go Back");
        Utils.makeDummySlots(inv);

        return inv;
    }

}
