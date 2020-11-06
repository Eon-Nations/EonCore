package me.squid.eoncore.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class Kit {

    public abstract String getName();
    public abstract List<ItemStack> getItems();
    public abstract List<String> getDescription();

    public abstract void giveKit(Player p);

}
