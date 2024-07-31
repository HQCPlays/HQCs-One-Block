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
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.hqcplays.hqcsoneblock.items.CustomPickaxes;
import org.hqcplays.hqcsoneblock.items.RareOneBlockItems;
import org.hqcplays.hqcsoneblock.items.VanillaPlusItems;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheatMenuCommand implements CommandExecutor, Listener {
    private final ArrayList<ItemStack> cheatItemList = new ArrayList<>();
    private final List<String> authorizedUsers = Arrays.asList("HQC_Plays", "Jestarin", "Cflip_", "Entitylght"); // Replace with actual usernames

    private void addItems() {
        cheatItemList.clear();

        // Shards
        cheatItemList.add(AmethystShardItems.goldShard);
        cheatItemList.add(AmethystShardItems.redShard);
        cheatItemList.add(AmethystShardItems.blueShard);
        cheatItemList.add(AmethystShardItems.greenShard);
        cheatItemList.add(AmethystShardItems.purpleShard);
        cheatItemList.add(AmethystShardItems.whiteShard);
        cheatItemList.add(AmethystShardItems.blackShard);
        cheatItemList.add(AmethystShardItems.rainbowShard);
        cheatItemList.add(AmethystShardItems.effectShard);

        // Swords
        cheatItemList.add(VanillaPlusItems.coalSword);
        cheatItemList.add(AmethystShardItems.blackShardSword);
        cheatItemList.add(AmethystShardItems.whiteShardSword);
        cheatItemList.add(AmethystShardItems.redShardSword);
        cheatItemList.add(AmethystShardItems.blueShardSword);

        // Armor
        cheatItemList.add(AmethystShardItems.redShardHelmet);
        cheatItemList.add(AmethystShardItems.redShardChestplate);
        cheatItemList.add(AmethystShardItems.redShardLeggings);
        cheatItemList.add(AmethystShardItems.redShardBoots);

        // Pickaxes
        cheatItemList.add(CustomPickaxes.woodPickaxe);
        cheatItemList.add(CustomPickaxes.stonePickaxe);
        cheatItemList.add(CustomPickaxes.ironPickaxe);
        cheatItemList.add(CustomPickaxes.goldenPickaxe);
        cheatItemList.add(CustomPickaxes.diamondPickaxe);
        cheatItemList.add(CustomPickaxes.netheritePickaxe);
        cheatItemList.add(CustomPickaxes.lapisPickaxe);
        cheatItemList.add(CustomPickaxes.redstonePickaxe);
        cheatItemList.add(CustomPickaxes.stardustPickaxe);

        // Misc. items
        cheatItemList.add(RareOneBlockItems.stardust);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (authorizedUsers.contains(player.getName())) {
                addItems();
                openMainShopGUI(player);
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return false;
            }
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }

    public void openMainShopGUI(Player player) {
        Inventory shopGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "CHEATER!!!!!");

        for (ItemStack item : cheatItemList) {
            shopGUI.addItem(item);
        }

        player.openInventory(shopGUI);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.DARK_GREEN + "CHEATER!!!!!")) {
            event.setCancelled(true);

            // Check if the click is in the top inventory (custom inventory) only
            if (event.getClickedInventory() == event.getView().getTopInventory()) {
                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

                // Ensure the click is only registered once by checking for left-click
                if (event.isLeftClick()) {
                    Player player = (Player) event.getWhoClicked();
                    player.getInventory().addItem(clickedItem.clone());
                }
            }
        }
    }
}
