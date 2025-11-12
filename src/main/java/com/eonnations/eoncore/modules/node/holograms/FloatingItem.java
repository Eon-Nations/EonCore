package com.eonnations.eoncore.modules.node.holograms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class FloatingItem implements AutoCloseable {
    private final Item item;

    public FloatingItem(Location location, Material material) {
        this.item = location.getWorld().dropItem(location, new ItemStack(material, 1));
        item.setGravity(false);
        item.setVelocity(new Vector(0, 0, 0));
        item.setCanMobPickup(false);
        item.setGlowing(true);
        item.setCanPlayerPickup(false);
        item.setPersistent(true);
        item.setWillAge(false);
        item.setNoPhysics(true);
        item.setInvulnerable(true);
    }

    @Override
    public void close() {
       item.remove(); 
    }
}
