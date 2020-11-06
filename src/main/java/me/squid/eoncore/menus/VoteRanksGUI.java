package me.squid.eoncore.menus;

import me.squid.eoncore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class VoteRanksGUI {

    public Inventory MainGUI() {
        Inventory inv = Bukkit.createInventory(null, 9, Utils.chat("&a&lVote Ranks"));

        Utils.createItem(inv, Material.GOLD_BLOCK, 1, 7, "&a&lVoter of the Month");
        Utils.createItem(inv, Material.DIAMOND_PICKAXE, 1, 3, "&a&lVoter I");
        Utils.createItem(inv, Material.DIAMOND_ORE, 1, 4, "&a&lVoter II");
        Utils.createItem(inv, Material.EMERALD_BLOCK, 1, 5, "&a&lVoter III");
        Utils.createItem(inv, Material.EMERALD, 1, 6, "&a&lVoter IV");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory VoterOfTheMonthGUI() {
        Inventory inv = Bukkit.createInventory(null, 9, Utils.chat("&a&lVoter Of The Month"));

        Utils.createItem(inv, Material.EMERALD, 1, 1, "&a&l$20,000");
        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 1, 2, "&5&l5 Galactic Keys");
        Utils.createItem(inv, Material.BARRIER, 1, 9, "&4&lExit");
        Utils.createItem(inv, Material.COMMAND_BLOCK, 1, 3, "&a&lCommand: /fly for 1 month");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory VoterIGUI() {
        Inventory inv = Bukkit.createInventory(null, 9, Utils.chat("&a&lVoter I"));

        Utils.createItem(inv, Material.DIAMOND_SWORD, 1, 1, "&a&lVoter Set of Tools/Armor");
        Utils.createItem(inv, Material.BARRIER, 1, 9, "&4&lExit");
        Utils.createItem(inv, Material.DIAMOND_SWORD, 1, 2, "&a&l10 Vote Crate Keys");
        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 1, 3, "&5&l3 Galactic Keys");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory VoterIIGUI() {
        Inventory inv = Bukkit.createInventory(null, 9, Utils.chat("&a&lVoter II"));

        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 1, 1, "&a&lVote Kit");
        Utils.createItem(inv, Material.BARRIER, 1, 9, "&4&lExit");
        Utils.createItem(inv, Material.DIAMOND_PICKAXE, 1, 2 ,"&a&lVoters Cave");
        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 1, 3, "&5&l5 Galactic Keys");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory VoterIIIGUI() {
        Inventory inv = Bukkit.createInventory(null, 9, Utils.chat("&a&lVoter III"));

        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 1, 1, "&5&l10 Galactic Keys");
        Utils.createItem(inv, Material.BARRIER, 1, 9, "&4&lExit");
        Utils.createItem(inv, Material.COMMAND_BLOCK, 1, 2, "Command: /feed for two weeks");
        Utils.makeDummySlots(inv);

        return inv;
    }

    public Inventory VoterIVGUI() {
        Inventory inv = Bukkit.createInventory(null, 9, Utils.chat("&a&lVoter IV"));

        Utils.createItem(inv, Material.TRIPWIRE_HOOK, 1, 1, "&5&l10 Galactic Keys");
        Utils.createItem(inv, Material.DIAMOND_PICKAXE, 1, 2, "&5&lSet of Galactic Tools");
        Utils.createItem(inv, Material.BARRIER, 1, 9, "&4&lExit");
        Utils.createItem(inv, Material.DIAMOND_ORE, 1, 3, "&a&lAccess to Donator Cave");
        Utils.createItem(inv, Material.COMMAND_BLOCK, 1, 4, "&a&lPermanent Access to /feed or /fly (Choice)");
        Utils.makeDummySlots(inv);

        return inv;
    }

}
