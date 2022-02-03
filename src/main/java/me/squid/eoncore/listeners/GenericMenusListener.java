package me.squid.eoncore.listeners;

import me.squid.eoncore.EonCore;
import me.squid.eoncore.menus.MobArenaGUI;
import me.squid.eoncore.menus.RulesGUI;
import me.squid.eoncore.menus.VoteRanksGUI;
import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GenericMenusListener implements Listener {

    EonCore plugin;
    RulesGUI rulesGUI = new RulesGUI();
    VoteRanksGUI voteRanksGUI = new VoteRanksGUI();
    MobArenaGUI mobArenaGUI = new MobArenaGUI();

    public GenericMenusListener(EonCore plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        // Rules Menu Listeners (The four below)
        if (e.getView().getTitle().equals(Utils.chat("&5&lEon Rules"))){
            switch (e.getCurrentItem().getType()) {
                case BLUE_WOOL -> {
                    p.openInventory(rulesGUI.ChatRules());
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                }
                case YELLOW_WOOL -> {
                    p.openInventory(rulesGUI.GeneralRules());
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                }
                case PURPLE_WOOL -> {
                    p.openInventory(rulesGUI.MarketRules());
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                }
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&b&lChat Rules"))){
            if (e.getCurrentItem().getType().equals(Material.BARRIER)){
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                p.openInventory(rulesGUI.Categories());
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&6&lGeneral Rules"))){
            if (e.getCurrentItem().getType().equals(Material.BARRIER)){
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                p.openInventory(rulesGUI.Categories());
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&5&lMarket Rules"))){
            if (e.getCurrentItem().getType().equals(Material.BARRIER)){
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                p.openInventory(rulesGUI.Categories());
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&a&lVote Ranks"))) {
            switch (e.getCurrentItem().getType()) {
                case GOLD_BLOCK:
                    p.openInventory(voteRanksGUI.VoterOfTheMonthGUI());
                    break;
                case DIAMOND_PICKAXE:
                    p.openInventory(voteRanksGUI.VoterIGUI());
                    break;
                case DIAMOND_ORE:
                    p.openInventory(voteRanksGUI.VoterIIGUI());
                    break;
                case EMERALD_BLOCK:
                    p.openInventory(voteRanksGUI.VoterIIIGUI());
                    break;
                case EMERALD:
                    p.openInventory(voteRanksGUI.VoterIVGUI());
                    break;
            }
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&a&lVoter I"))) {
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                p.openInventory(voteRanksGUI.MainGUI());
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&a&lVoter II"))) {
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                p.openInventory(voteRanksGUI.MainGUI());
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&a&lVoter III"))) {
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                p.openInventory(voteRanksGUI.MainGUI());
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&a&lVoter IV"))) {
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                p.openInventory(voteRanksGUI.MainGUI());
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&a&lVoter Of The Month"))) {
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                p.openInventory(voteRanksGUI.MainGUI());
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&a&lHelp Menu"))) {
            switch (e.getCurrentItem().getType()) {
                case DIAMOND_HOE:
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    Bukkit.dispatchCommand(p, "jobs browse");
                    break;
                case EXPERIENCE_BOTTLE:
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    Bukkit.dispatchCommand(p, "ranks");
                    p.sendMessage(Utils.chat(EonCore.prefix + "&&You can rankup with /rankup once you have enough money"));
                    break;
                case OAK_LEAVES:
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    Bukkit.dispatchCommand(p, "wild");
                    break;
                case TRIPWIRE_HOOK:
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    p.openInventory(voteRanksGUI.HelpVoteMenu());
                    break;
                case SLIME_BALL:
                    p.closeInventory();
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    Bukkit.dispatchCommand(p, "mcstats");
                    break;
                case ZOMBIE_HEAD:
                    p.openInventory(mobArenaGUI.MainGUI());
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    break;
                default:
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&a&lMob Arena Selection"))) {
            switch (e.getCurrentItem().getType()) {
                case ZOMBIE_HEAD:
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    Bukkit.dispatchCommand(p, "ma join hanger");
                    break;
                case SKELETON_SKULL:
                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1, 1);
                    p.closeInventory();
                    p.sendMessage(Utils.chat(EonCore.prefix + "&aHard Mob Arena Coming Soon!"));
                    break;
            }
            e.setCancelled(true);
        }

        if (e.getView().getTitle().equals(Utils.chat("&a&lVote Help"))) {
            switch (e.getCurrentItem().getType()) {
                case TRIPWIRE_HOOK:
                    p.closeInventory();
                    Bukkit.dispatchCommand(p, "vote");
                    break;
                case DIAMOND_PICKAXE:
                    p.openInventory(voteRanksGUI.MainGUI());
                    p.sendMessage(Utils.chat(EonCore.prefix + "&7Explore the different vote ranks that you can obtain through voting."));
                    break;
                case BARRIER:
                    p.closeInventory();
                    break;
            }
            e.setCancelled(true);
        }
    }
}
