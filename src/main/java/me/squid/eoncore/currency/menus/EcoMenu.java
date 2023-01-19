package me.squid.eoncore.currency.menus;


import me.squid.eoncore.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class EcoMenu {
    Economy eco;

    public EcoMenu(Economy econManager) {
        this.eco = econManager;
    }

    public Inventory ShopCategory() {
        Inventory inv = Bukkit.createInventory(null, 27, "&5&lCategories");

        Utils.createItem(inv, Material.STONE_BRICKS, 1, 12, "&f&lBlocks");
        Utils.createItem(inv, Material.OAK_BOAT, 1, 14, "&f&lMisc");
        Utils.createItem(inv, Material.HOPPER, 1, 16, "&7&lHopper Shop");

        return inv;
    }

    public Inventory BlockShop(){
        Inventory inv = Bukkit.createInventory(null, 27, ("&f&lBlocks"));

        Utils.createItem(inv, Material.SAND, 1, 12, "&f&lSand", ("&5Price: $2"));
        Utils.createItem(inv, Material.COBBLESTONE, 1, 13, "&f&lCobblestone", ("&5Price: $1"));
        Utils.createItem(inv, Material.GRAVEL, 1, 14, "&f&lGravel", ("&5Price: $1"));
        Utils.createItem(inv, Material.DIRT, 1, 15, "&f&lDirt", ("&5Price: $1"));
        Utils.createItem(inv, Material.STONE, 1, 16, "&f&lStone", ("&5Price: $1.50"));

        return inv;
    }

    public Inventory MiscShop() {
        Inventory inv = Bukkit.createInventory(null, 27, ("&f&lMisc"));

        Utils.createItem(inv, Material.PHANTOM_MEMBRANE, 1, 11, "&f&lPhantom Membrane", ("&5Price: $100"));
        Utils.createItem(inv, Material.ITEM_FRAME, 1, 13, "&f&lItem Frames", ("&5Price: $750"));
        Utils.createItem(inv, Material.END_CRYSTAL, 1, 15, "&f&lEnd Crystal", ("&5Price: $1000"));
        Utils.createItem(inv, Material.SHULKER_SHELL, 1, 17, "&f&lShulker Shell", ("&5Price: $750"));

        return inv;
    }

    public Inventory HopperShop(){
        Inventory inv = Bukkit.createInventory(null, 27, ("&a&lHopper Shop"));

        Utils.createItem(inv, Material.HOPPER, 1, 14, "&7&lHopper", ("&bPrice: $500"));

        return inv;
    }

    public Inventory HopperAmount() {
        Inventory inv = Bukkit.createInventory(null, 27, ("&a&lHopper Amount"));

        Utils.createItem(inv, Material.HOPPER, 1,2, "&f&l1");
        Utils.createItem(inv, Material.HOPPER, 2, 3, "&f&l2");
        Utils.createItem(inv, Material.HOPPER, 4, 4, "&f&l4");
        Utils.createItem(inv, Material.HOPPER, 8, 5, "&f&l8");
        Utils.createItem(inv, Material.HOPPER, 16, 6, "&f&l16");
        Utils.createItem(inv, Material.HOPPER, 32, 7, "&f&l32");
        Utils.createItem(inv, Material.HOPPER, 64, 8, "&f&l64");
        Utils.createItem(inv, Material.GREEN_STAINED_GLASS_PANE, 1, 23, "&aBuy More");

        return inv;
    }

    public Inventory QuantityMenu(Material clicked){
        Inventory inv = Bukkit.createInventory(null, 27, ("&a&lAmount"));

        Utils.createItem(inv, clicked, 1, 11, "&f&l1");
        Utils.createItem(inv, clicked, 2, 12, "&f&l2");
        Utils.createItem(inv, clicked, 4, 13, "&f&l4");
        Utils.createItem(inv, clicked, 8, 14, "&f&l8");
        Utils.createItem(inv, clicked, 16, 15, "&f&l16");
        Utils.createItem(inv, clicked, 32, 16, "&f&l32");
        Utils.createItem(inv, clicked, 64, 17, "&f&l64");
        Utils.createItem(inv, Material.GREEN_STAINED_GLASS_PANE, 1, 23, "&aBuy More");

        return inv;
    }

    public Inventory StackMenu(Material clicked) {
        Inventory inv = Bukkit.createInventory(null, 9, "&a&lStacks");

        Utils.createItem(inv, clicked, 64, 1, "&f&l1 Stack of Blocks");
        for (int i = 2; i <= 9; i++){
            Utils.createItem(inv, clicked, 64, i, "&f&l" + i + " Stacks of Blocks");
        }

        return inv;
    }
}
