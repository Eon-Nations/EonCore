package me.squid.eoncore.menus;

import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class RanksMenu {

    public Inventory MainGUI() {
        Inventory inv = Bukkit.createInventory(null, 9, Utils.chat("&5&lEon Ranks"));

        Utils.createItem(inv, Material.BLUE_BANNER, 1, 2, "&1&lTraveler", "&f$5,500",
                "- &f2 Pet Slots", "- &f3 Sethomes", "- &fAccess to /hat");
        Utils.createItem(inv, Material.PURPLE_BANNER, 1, 3, "&5&lExplorer", "&f$10,000", "- &f5 Mob Arena Keys",
                "- &f5 Sethomes", "- &f4 Pet Slots", "- &f3000 Claim Blocks");
        Utils.createItem(inv, Material.GREEN_BANNER, 1, 4, "&a&lRanger", "&f$25,000"
        , "- &f7 Sethomes", "- &f6 Pet Slots", "- &f10k Claim Blocks", "- &fBasic Mine");
        Utils.createItem(inv, Material.LIGHT_BLUE_BANNER, 1, 5, "&b&lSpaceman", "&f$75,000",
                "- &f9 Sethomes", "- &f10 Pet Slots", "- &fCustom Particle Effects", "- &f20k Claim Blocks");
        Utils.createItem(inv, Material.YELLOW_BANNER, 1, 6, "&6&lAstronaut", "&f$150,000",
                "- &f15 Sethomes", "- &f20 Pet Slots", "- &f50k Claim Blocks", "- &f15 Galactic Keys", "- &f10 Mob Arena Keys");
        Utils.createItem(inv, Material.GREEN_BANNER, 1, 7, "&2&lAlien", "&f$500,000",
                "- &f25 Sethomes", "- &f30 Pet Slots", "- &f50k Claim Blocks", "- &f10 Galactic Keys");
        Utils.createItem(inv, Material.DIAMOND_SWORD, 1, 8, "&1&lGa&2&lm&3&ler", "&f$1,500,000", "- &fComing soon!");

        return inv;
    }

}
