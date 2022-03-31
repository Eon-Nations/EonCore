package me.squid.eoncore.tasks;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.utils.Cuboid;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BasicMineTask extends BukkitRunnable {

    EonCore plugin;
    Cuboid cuboid;
    List<Material> tempMaterials;
    Location mine;

    public BasicMineTask(EonCore plugin, Location l1, Location l2, Location mine) {
        this.plugin = plugin;
        this.mine = mine;
        cuboid = new Cuboid(l1, l2);
        tempMaterials = initializeList(getOreMap());
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (cuboid.contains(p.getLocation())) p.teleport(mine);
            Bukkit.getScheduler().runTask(plugin, () -> p.sendMessage(Utils.chat(Utils.getPrefix("nations") + "&bBasic Mine has reset")));
        }

        for (Block block : cuboid.getBlocks()) {
            if (block.getType() == Material.AIR) {
                Bukkit.getScheduler().runTask(plugin, () -> block.setType(getRandomOreMaterial(tempMaterials)));
            }
        }
    }

    private HashMap<Material, Integer> getOreMap() {
        HashMap<Material, Integer> ores = new HashMap<>();
        ores.put(Material.IRON_ORE, 3);
        ores.put(Material.STONE, 2);
        ores.put(Material.GOLD_ORE, 1);
        ores.put(Material.COAL_ORE, 2);
        ores.put(Material.LAPIS_ORE, 1);
        ores.put(Material.REDSTONE_ORE, 2);
        return ores;
    }

    private Material getRandomOreMaterial(List<Material> tempMaterials) {
        return tempMaterials.get(new Random().nextInt(tempMaterials.size())); // Select random material from temp list
    }

    private List<Material> initializeList(HashMap<Material, Integer> oremap) {
        List<Material> oreList = new ArrayList<>();
        for (Material material : oremap.keySet()) { // Loop for each Material in materialMap
            for (int i = 0; i < oremap.get(material); i++) { // Loop according to int value in materialMap
                oreList.add(material); // Add material to temp list
            }
        }
        return oreList;
    }
}
