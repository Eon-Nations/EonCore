package me.squid.eoncore.kit.kits;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.kit.Kit;
import me.squid.eoncore.managers.CooldownManager;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class StarterKit extends Kit {

    EonCore plugin;

    public StarterKit(EonCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "starter";
    }

    @Override
    public List<ItemStack> getItems() {
        List<ItemStack> toReturn = new ArrayList<>();

        toReturn.add(Utils.createKitItem(Material.WOODEN_SWORD, 1, "&7&lStick", null, null));
        toReturn.add(Utils.createKitItem(Material.WOODEN_PICKAXE, 1, "&f&lPick", null, null));
        toReturn.add(Utils.createKitItem(Material.WOODEN_AXE, 1, "&7&lWood", null, null));
        toReturn.add(Utils.createKitItem(Material.WOODEN_SHOVEL, 1, "&7&lSpoon", null, null));
        toReturn.add(Utils.createKitItem(Material.COOKED_BEEF, 16, "", null, null));

        return toReturn;
    }

    @Override
    public List<String> getDescription() {
        List<String> toReturn = new ArrayList<>();
        toReturn.add(Utils.chat("&7Description: "));
        toReturn.add(Utils.chat("- &5The starter kit on Eon Survival"));
        toReturn.add(Utils.chat("- &5Contains Basic Needs"));
        return toReturn;
    }

    @Override
    public void giveKit(Player p) {
        List<ItemStack> toReturn = getItems();


    }
}
