package me.squid.eoncore.tasks;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.adapter.BukkitImplAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import me.squid.eoncore.EonCore;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("ConstantConditions")
public class UtilityDoorTask implements Runnable {

    EonCore plugin;

    final HashMap<ProtectedRegion, List<Block>> blocks = new HashMap<>();
    HashMap<Player, ProtectedRegion> playersInStall = new HashMap<>();
    List<ProtectedRegion> regions = new ArrayList<>();

    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    RegionManager region = container.get(BukkitAdapter.adapt(Bukkit.getWorld("spawn")));

    ProtectedRegion stall1 = region.getRegion("utilitystall1");
    ProtectedRegion stall2 = region.getRegion("utilitystall2");
    ProtectedRegion stall3 = region.getRegion("utilitystall3");
    ProtectedRegion stall4 = region.getRegion("utilitystall4");
    ProtectedRegion stall5 = region.getRegion("utilitystall5");
    ProtectedRegion stall6 = region.getRegion("utilitystall6");
    ProtectedRegion stall7 = region.getRegion("utilitystall7");
    ProtectedRegion stall8 = region.getRegion("utilitystall8");

    public UtilityDoorTask(EonCore plugin) {
        this.plugin = plugin;
        initializeHashMap();
        initializeRegionList();
    }


    @Override
    public void run() {
        for (ProtectedRegion stall : regions) {
            if (isStallEmpty(stall)) openStall(blocks.get(stall));
            else closeStall(blocks.get(stall));
        }
    }

    private void initializeHashMap() {
        blocks.put(stall1, getNewBlockList(Bukkit.getWorld("spawn").getBlockAt(-605, 81, -228),
                Bukkit.getWorld("spawn").getBlockAt(-605, 82, -228),
                Bukkit.getWorld("spawn").getBlockAt(-605, 81, -232),
                Bukkit.getWorld("spawn").getBlockAt(-605, 82, -232)));
        blocks.put(stall2, getNewBlockList(Bukkit.getWorld("spawn").getBlockAt(-605, 81, -220),
                Bukkit.getWorld("spawn").getBlockAt(-605, 82, -220),
                Bukkit.getWorld("spawn").getBlockAt(-605, 81, -224),
                Bukkit.getWorld("spawn").getBlockAt(-605, 82, -224)));
        blocks.put(stall3, getNewBlockList(Bukkit.getWorld("spawn").getBlockAt(-598, 81, -217),
                Bukkit.getWorld("spawn").getBlockAt(-598, 82, -217),
                Bukkit.getWorld("spawn").getBlockAt(-602, 81, -217),
                Bukkit.getWorld("spawn").getBlockAt(-602, 82, -217)));
        blocks.put(stall4, getNewBlockList(Bukkit.getWorld("spawn").getBlockAt(-594, 81, -217),
                Bukkit.getWorld("spawn").getBlockAt(-594, 82, -217),
                Bukkit.getWorld("spawn").getBlockAt(-590, 81, -217),
                Bukkit.getWorld("spawn").getBlockAt(-590, 82, -217)));
        blocks.put(stall5, getNewBlockList(Bukkit.getWorld("spawn").getBlockAt(-587, 81, -220),
                Bukkit.getWorld("spawn").getBlockAt(-587, 82, -220),
                Bukkit.getWorld("spawn").getBlockAt(-587, 81, -224),
                Bukkit.getWorld("spawn").getBlockAt(-587, 82, -224)));
        blocks.put(stall6, getNewBlockList(Bukkit.getWorld("spawn").getBlockAt(-587, 81, -232),
                Bukkit.getWorld("spawn").getBlockAt(-587, 82, -232),
                Bukkit.getWorld("spawn").getBlockAt(-587, 81, -228),
                Bukkit.getWorld("spawn").getBlockAt(-587, 82, -228)));
        blocks.put(stall7, getNewBlockList(Bukkit.getWorld("spawn").getBlockAt(-594, 81, -235),
                Bukkit.getWorld("spawn").getBlockAt(-594, 82, -235),
                Bukkit.getWorld("spawn").getBlockAt(-590, 81, -235),
                Bukkit.getWorld("spawn").getBlockAt(-590, 82, -235)));
        blocks.put(stall8, getNewBlockList(Bukkit.getWorld("spawn").getBlockAt(-598, 81, -235),
                Bukkit.getWorld("spawn").getBlockAt(-598, 82, -235),
                Bukkit.getWorld("spawn").getBlockAt(-602, 81, -235),
                Bukkit.getWorld("spawn").getBlockAt(-602, 82, -235)));
    }

    private void closeStall(List<Block> list) {
        for (Block block : list) {
            Bukkit.getScheduler().runTask(plugin, () -> block.setType(Material.RED_STAINED_GLASS));
        }
    }

    private void openStall(List<Block> list) {
        for (Block block : list) {
            Bukkit.getScheduler().runTask(plugin, () -> block.setType(Material.AIR));
        }
    }

    private void initializeRegionList() {
        regions.add(stall1);
        regions.add(stall2);
        regions.add(stall3);
        regions.add(stall4);
        regions.add(stall5);
        regions.add(stall6);
        regions.add(stall7);
        regions.add(stall8);
    }

    private List<Block> getNewBlockList(Block... blocks) {
        return Arrays.asList(blocks);
    }

    private boolean isStallEmpty(ProtectedRegion stall) {
        if (playersInStall.containsValue(stall)) return false;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (stall.contains(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ())) {
                playersInStall.put(p, stall);
                return false;
            } else playersInStall.remove(p);
        }
        return true;
    }
}
