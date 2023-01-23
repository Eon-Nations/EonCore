package me.squid.eoncore.misc.menus;

import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class HelpGUI implements StaleInventory {

    @Override
    public Inventory buildInventory() {
        return new MenuBuilder().createMenu("<green><bold>Help Menu", 54)
                .addItem(Material.DIAMOND_HOE, 20, "<green>Jobs", "Get money for doing in game tasks!")
                .addItem(Material.OAK_LEAVES, 23, "<dark_green>Wild", "Click here to be teleported to the wild")
                .addItem(Material.TRIPWIRE_HOOK, 26, "<green>Voting", "Get rewards for just a few clicks")
                .addItem(Material.SLIME_BALL, 38, "<green><bold>McMMO", "Unlock abilities and skills to do more cool stuff")
                .addItem(Material.ZOMBIE_HEAD, 41, "<green><bold>Mob Arena", "PvE Experience with friends for rewards")
                .makeDummySlots().completeInventory();
    }

    @Override
    public void clickEvent(Player clicker, ItemStack currentItem, EonCore plugin) {
        helpFunction(currentItem).accept(clicker);
        clicker.playSound(clicker.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
    }

    private Consumer<Player> helpFunction(ItemStack currentItem) {
        return switch (currentItem.getType()) {
            case DIAMOND_HOE -> clicker -> Bukkit.dispatchCommand(clicker, "jobsmenu");
            case OAK_LEAVES -> clicker -> Bukkit.dispatchCommand(clicker, "wild");
            case SLIME_BALL -> clicker -> Bukkit.dispatchCommand(clicker, "mcstats");
            case ZOMBIE_HEAD -> clicker -> Bukkit.dispatchCommand(clicker, "ma join");
            default -> clicker -> { };
        };
    }
}
