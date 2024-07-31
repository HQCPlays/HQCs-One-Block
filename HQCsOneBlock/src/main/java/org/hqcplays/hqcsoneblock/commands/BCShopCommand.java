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
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.OneBlockController;
import org.hqcplays.hqcsoneblock.PickaxeController;
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.hqcplays.hqcsoneblock.numberSheets.PricesSheet;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.UUID;

import static org.hqcplays.hqcsoneblock.HQCsOneBlock.updateScoreboard;
import static org.hqcplays.hqcsoneblock.numberSheets.PricesSheet.*;

public class BCShopCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openMainShopGUI(player);
            return true;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }

    public void openMainShopGUI(Player player) {
        Inventory shopGUI = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Main Shop");

        // Create display items that will open up other shops
        ItemStack itemShop = new ItemStack(Material.CHEST);
        ItemMeta itemShopMeta = itemShop.getItemMeta();
        itemShopMeta.setDisplayName(ChatColor.GOLD + "Item Shop");
        itemShop.setItemMeta(itemShopMeta);

        ItemStack blockUpgradeShop = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta blockUpgradeShopMeta = blockUpgradeShop.getItemMeta();
        blockUpgradeShopMeta.setDisplayName(ChatColor.GOLD + "Block Upgrade Shop");
        blockUpgradeShop.setItemMeta(blockUpgradeShopMeta);

        // Place display items
        shopGUI.setItem(11, itemShop);
        shopGUI.setItem(15, blockUpgradeShop);

        // Actually open the main menu
        player.openInventory(shopGUI);
    }

    public void openItemShopGUI(Player player) {
        Inventory itemShopGUI = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Item Shop");

        for (Material item : PricesSheet.getItemShopItems()) {
            ItemStack shopItem = new ItemStack(item);
            ItemMeta shopItemMeta = shopItem.getItemMeta();
            shopItemMeta.setDisplayName(ChatColor.GOLD + item.name());
            shopItemMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Price: " + PricesSheet.getItemShopPrices(item) + " Block Coins"));
            shopItem.setItemMeta(shopItemMeta);

            itemShopGUI.addItem(shopItem);
        }

        player.openInventory(itemShopGUI);
    }

    public void openBlockUpgradeShopGUI(Player player) {
        Inventory blockUpgradeShopGUI = Bukkit.createInventory(null, 27, ChatColor.DARK_GREEN + "Block Upgrade Shop");

        for (Material block : PricesSheet.getBlockUnlockBlocks()) {
            ItemStack shopItem = new ItemStack(block);
            ItemMeta shopItemMeta = shopItem.getItemMeta();
            shopItemMeta.setDisplayName(ChatColor.GOLD + block.name());
            shopItemMeta.setLore(Collections.singletonList(ChatColor.YELLOW + "Price: " + PricesSheet.getBlockUnlockPrices(block) + " Block Coins"));
            shopItem.setItemMeta(shopItemMeta);

            blockUpgradeShopGUI.addItem(shopItem);
        }

        player.openInventory(blockUpgradeShopGUI);
    }

    // This function controls what happens when the player clicks in the shop
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;

        Player player = (Player) event.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        String inventoryTitle = event.getView().getTitle();

        // Main menu controls
        if (inventoryTitle.equals(ChatColor.DARK_GREEN + "Main Shop")) {
            event.setCancelled(true);

            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                if (clickedItem.getType() == Material.CHEST) {
                    openItemShopGUI(player);
                } else if (clickedItem.getType() == Material.GRASS_BLOCK) {
                    openBlockUpgradeShopGUI(player);
                }
            }
        }

        // Item shop controls
        else if (inventoryTitle.equals(ChatColor.DARK_GREEN + "Item Shop")) {
            event.setCancelled(true);

            if (event.getClickedInventory() == event.getView().getTopInventory() && inventoryTitle.equals(ChatColor.DARK_GREEN + "Item Shop")) {
                ItemMeta meta = clickedItem.getItemMeta();
                if (meta != null) {
                    handleItemShopPurchase(player, clickedItem.getType());
                    player.closeInventory();
                    openItemShopGUI(player);
                }
            }
        }

        // Block unlock shop controls
        else if (inventoryTitle.equals(ChatColor.DARK_GREEN + "Block Upgrade Shop")) {
            event.setCancelled(true);

            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                ItemMeta meta = clickedItem.getItemMeta();
                if (meta != null && inventoryTitle.equals(ChatColor.DARK_GREEN + "Block Upgrade Shop")) {
                    OneBlockController.handleBlockUnlock(player, clickedItem);
                    player.closeInventory();
                    openBlockUpgradeShopGUI(player);
                }
            }
        }
    }

    public static void handleItemShopPurchase(Player player, Material item) {
        PlayerSaveData playerData = HQCsOneBlock.playerData.get(player.getUniqueId());
        int price = PricesSheet.getItemShopPrices(item);
        if (playerData.balance >= price) {
            playerData.balance -= price;
            ItemStack boughtItem = new ItemStack(item);
            player.getInventory().addItem(boughtItem);
            updateScoreboard(player);
        }
    }
}
