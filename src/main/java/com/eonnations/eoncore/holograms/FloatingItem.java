package com.eonnations.eoncore.holograms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class FloatingItem implements AutoCloseable {
    private Item item;

    public FloatingItem(Location location, Material material) {
        this.item = location.getWorld().dropItem(location, new ItemStack(material, 1));
        item.setGravity(false);
        item.setVelocity(new Vector(0, 0, 0));
        item.setItemStack(new ItemStack(material, 1));
        item.setCanMobPickup(false);
        item.setGlowing(true);
        item.setCanPlayerPickup(false);
        item.setPersistent(true);
        item.setWillAge(false);
    }

    @Override
    public void close() {
       item.remove(); 
    }
}
