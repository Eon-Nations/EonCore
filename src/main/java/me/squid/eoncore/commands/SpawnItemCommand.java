package me.squid.eoncore.commands;

import me.squid.eoncore.EonCommand;
import me.squid.eoncore.EonCore;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.eonnations.eonpluginapi.api.Command;

@Command(name = "spawnitem",
        usage = "/spawnitem <material>")
public class SpawnItemCommand extends EonCommand {

    public SpawnItemCommand(EonCore plugin) {
        super(plugin);
    }

    @Override
    protected void execute(Player player, String[] args) {
        Material material = Material.valueOf(args[0]);
        ItemStack itemStack = new ItemStack(material, 1);
        Item item = player.getWorld().dropItem(player.getLocation(), itemStack);
        item.setVelocity(new Vector(0, 0, 0));
        item.setCanPlayerPickup(false);
        item.setGlowing(true);
        item.setGravity(false);
    }
}
