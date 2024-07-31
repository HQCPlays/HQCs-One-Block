package org.hqcplays.hqcsoneblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Objects;

public class ProgressionCommand implements CommandExecutor, Listener {
    private String ageName;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openProgressionMenu(player);
            return true;
        }
        return false;
    }

    private void openProgressionMenu(Player player) {
        Inventory progressionMenu = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Progression Menu");

        // Add age items
        ItemStack ageItem1 = new ItemStack(Material.PAPER);
        ItemMeta meta1 = ageItem1.getItemMeta();
        meta1.setDisplayName(ChatColor.BLUE + "Starting Age");
        ageItem1.setItemMeta(meta1);
        progressionMenu.setItem(0, ageItem1);

        ItemStack ageItem2 = new ItemStack(Material.PAPER);
        ItemMeta meta2 = ageItem2.getItemMeta();
        meta2.setDisplayName(ChatColor.BLUE + "Expansion Age");
        ageItem2.setItemMeta(meta2);
        progressionMenu.setItem(1, ageItem2);

        ItemStack ageItem3 = new ItemStack(Material.PAPER);
        ItemMeta meta3 = ageItem3.getItemMeta();
        meta3.setDisplayName(ChatColor.BLUE + "Automation Age");
        ageItem3.setItemMeta(meta3);
        progressionMenu.setItem(2, ageItem3);

        // Add grey stained glass panes
        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta = glassPane.getItemMeta();
        glassMeta.setDisplayName(" ");
        glassPane.setItemMeta(glassMeta);
        for (int i = 9; i < 18; i++) {
            progressionMenu.setItem(i, glassPane);
        }

        // Add goals for the selected age
        addGoalsToMenu(progressionMenu, ageName);

        player.openInventory(progressionMenu);
    }

    private void addGoalsToMenu(Inventory menu, String ageName) {
        for (int i = 18; i < 54; i++) {
            menu.setItem(i, null); // Clear previous goals
        }

        // Add goal items
        if (Objects.equals(ageName, ChatColor.BLUE + "Starting Age")) {
            ItemStack goalItem1 = new ItemStack(Material.BOOK);
            ItemMeta meta1 = goalItem1.getItemMeta();
            meta1.setDisplayName(ChatColor.LIGHT_PURPLE + "Create your Island");
            meta1.setLore(Collections.singletonList(ChatColor.YELLOW + "Type /island to get started!"));
            goalItem1.setItemMeta(meta1);
            menu.setItem(18, goalItem1);

            ItemStack goalItem2 = new ItemStack(Material.BOOK);
            ItemMeta meta2 = goalItem2.getItemMeta();
            meta2.setDisplayName(ChatColor.LIGHT_PURPLE + "Mine your OneBlock");
            meta2.setLore(Collections.singletonList(ChatColor.YELLOW + "As you can see, it regenerates! It also gave you 1 Block Coin!"));
            goalItem2.setItemMeta(meta2);
            menu.setItem(19, goalItem2);

            ItemStack goalItem3 = new ItemStack(Material.BOOK);
            ItemMeta meta3 = goalItem3.getItemMeta();
            meta3.setDisplayName(ChatColor.LIGHT_PURPLE + "Go to the Block Coin Shop");
            meta3.setLore(Collections.singletonList(ChatColor.YELLOW + "Type /bcshop or go to your menu to check out the BCShop!"));
            goalItem3.setItemMeta(meta3);
            menu.setItem(20, goalItem3);

            ItemStack goalItem5 = new ItemStack(Material.BOOK);
            ItemMeta meta5 = goalItem5.getItemMeta();
            meta5.setDisplayName(ChatColor.LIGHT_PURPLE + "Upgrade your pickaxe to stone");
            meta5.setLore(Collections.singletonList(ChatColor.YELLOW + "Simply craft a new pickaxe out of stone!"));
            goalItem5.setItemMeta(meta5);
            menu.setItem(21, goalItem5);

            ItemStack goalItem4 = new ItemStack(Material.BOOK);
            ItemMeta meta4 = goalItem4.getItemMeta();
            meta4.setDisplayName(ChatColor.DARK_PURPLE + "Buy a Sapling");
            meta4.setLore(Collections.singletonList(ChatColor.YELLOW + "Buy a sapling from the BCShop! This will complete the Starting Age!"));
            goalItem4.setItemMeta(meta4);
            menu.setItem(22, goalItem4);
        } else if (Objects.equals(ageName, ChatColor.BLUE + "Expansion Age")) {
            ItemStack goalItem1 = new ItemStack(Material.BOOK);
            ItemMeta meta1 = goalItem1.getItemMeta();
            meta1.setDisplayName(ChatColor.LIGHT_PURPLE + "Unlock Coal Ore");
            meta1.setLore(Collections.singletonList(ChatColor.YELLOW + "Unlock the coal ore in the BCShop! All unlocked blocks have a 1% chance of appearing!"));
            goalItem1.setItemMeta(meta1);
            menu.setItem(18, goalItem1);

            ItemStack goalItem2 = new ItemStack(Material.BOOK);
            ItemMeta meta2 = goalItem2.getItemMeta();
            meta2.setDisplayName(ChatColor.LIGHT_PURPLE + "Unlock Iron Ore");
            meta2.setLore(Collections.singletonList(ChatColor.YELLOW + "Unlock the iron ore in the BCShop!"));
            goalItem2.setItemMeta(meta2);
            menu.setItem(19, goalItem2);

            ItemStack goalItem3 = new ItemStack(Material.BOOK);
            ItemMeta meta3 = goalItem3.getItemMeta();
            meta3.setDisplayName(ChatColor.LIGHT_PURPLE + "Unlock Gold Ore");
            meta3.setLore(Collections.singletonList(ChatColor.YELLOW + "Unlock the goal ore in the BCShop!"));
            goalItem3.setItemMeta(meta3);
            menu.setItem(20, goalItem3);

            ItemStack goalItem4 = new ItemStack(Material.BOOK);
            ItemMeta meta4 = goalItem4.getItemMeta();
            meta4.setDisplayName(ChatColor.DARK_PURPLE + "Unlock Ancient Debris");
            meta4.setLore(Collections.singletonList(ChatColor.YELLOW + "Unlock the Ancient Debris from the BCShop! This will complete the Expansion Age!"));
            goalItem4.setItemMeta(meta4);
            menu.setItem(22, goalItem4);

            ItemStack goalItem5 = new ItemStack(Material.BOOK);
            ItemMeta meta5 = goalItem5.getItemMeta();
            meta5.setDisplayName(ChatColor.LIGHT_PURPLE + "Unlock Diamond Ore");
            meta5.setLore(Collections.singletonList(ChatColor.YELLOW + "Unlock the diamond ore in the BCShop!"));
            goalItem5.setItemMeta(meta5);
            menu.setItem(21, goalItem5);
        } else {
            ItemStack goalItem1 = new ItemStack(Material.BOOK);
            ItemMeta meta1 = goalItem1.getItemMeta();
            meta1.setDisplayName(ChatColor.LIGHT_PURPLE + "Create your Island");
            meta1.setLore(Collections.singletonList(ChatColor.YELLOW + "Type /island to get started!"));
            goalItem1.setItemMeta(meta1);
            menu.setItem(18, goalItem1);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Progression Menu")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PAPER) {
                Player player = (Player) event.getWhoClicked();
                this.ageName = event.getCurrentItem().getItemMeta().getDisplayName();
                openProgressionMenu(player);
            }
        }
    }
}
