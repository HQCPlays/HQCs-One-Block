package org.hqcplays.hqcsoneblock.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.jetbrains.annotations.NotNull;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import static org.hqcplays.hqcsoneblock.HQCsOneBlock.updateScoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InboxCommand implements CommandExecutor, Listener {

    int pageNum;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // Checks if sender is a player, if so open the flea market, otherwise throw error message
        if (sender instanceof Player) {
            Player player = (Player) sender;
            openInboxGUI(player, 1);
            return true;
        } else {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }
    }

    public void openInboxGUI(Player player, int pageNumber) {
        pageNum = pageNumber;
        Inventory inboxGUI = Bukkit.createInventory(null, 54, ChatColor.DARK_GREEN + "INBOX                        " + ChatColor.RED + "PAGE: " + pageNum);
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        ArrayList<ItemStack> playerMail = playerData.mail;

        int startingIndex = (pageNum - 1) * 36;
        int endIndex = Math.min(startingIndex + 36, playerMail.size());
        for (int i = startingIndex; i < endIndex; i++) {
            ItemStack item = playerMail.get(i);
            inboxGUI.addItem(item);
        }

        // Create Information Item
        ItemStack inboxInformation = new ItemStack(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE);
        inboxInformation.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta inboxInformationMeta = inboxInformation.getItemMeta();
        inboxInformationMeta.setDisplayName(ChatColor.GOLD + "Inbox Information:");
        List<Component> inboxInformationlore = new ArrayList<>();
        inboxInformationlore.add(Component.text("- All items sent to you from the server, flea, or other players can be found above.").color(NamedTextColor.WHITE));
        inboxInformationlore.add(Component.text("- Click any item to claim it!").color(NamedTextColor.WHITE));
        inboxInformationMeta.lore(inboxInformationlore);
        inboxInformation.setItemMeta(inboxInformationMeta);

        // Create next and previous page buttons
        ItemStack previousPage = new ItemStack(Material.FIREWORK_ROCKET);
        previousPage.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta previousPageMeta = previousPage.getItemMeta();
        previousPageMeta.setDisplayName(ChatColor.GREEN + "Previous page");
        previousPage.setItemMeta(previousPageMeta);

        ItemStack nextPage = new ItemStack(Material.FIREWORK_ROCKET);
        nextPage.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        nextPageMeta.setDisplayName(ChatColor.GREEN + "Next page");
        nextPage.setItemMeta(nextPageMeta);

        // Create button to go to Flea Market
        ItemStack fleaButton = new ItemStack(Material.EMERALD);
        fleaButton.addItemFlags(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        ItemMeta fleaButtonMeta = fleaButton.getItemMeta();
        fleaButtonMeta.setDisplayName(ChatColor.GREEN + "Flea Market");
        fleaButton.setItemMeta(fleaButtonMeta);

        // Create and place line separating listings and menu controls
        for (int i = 36; i < 45; i++){
            ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            inboxGUI.setItem(i, pane);
        }



        // Place page controls
        if (pageNum > 1) {
            inboxGUI.setItem(48, previousPage);
        }
        if (playerMail.size() / (pageNum*36.0) > 1.0) {
            inboxGUI.setItem(50, nextPage);
        }

        // Place information item
        inboxGUI.setItem(49, inboxInformation);
        // Place flea button
        inboxGUI.setItem(45, fleaButton);

        player.openInventory(inboxGUI);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().startsWith(ChatColor.DARK_GREEN + "INBOX")) {
            event.setCancelled(true);

            // Check if the click is in the top inventory (custom inventory) only
            if (event.getClickedInventory() == event.getView().getTopInventory()) {

                ItemStack clickedItem = event.getCurrentItem();
                if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
                ItemMeta clickedItemMeta = clickedItem.getItemMeta();
                Player player = (Player) event.getWhoClicked();
                PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);

                // If player is trying to click on a purchaseable item (first 36 slots of the flea)
                if (event.getSlot() < 37) {
                    if (event.getClickedInventory() == event.getView().getTopInventory()) {
                        if (clickedItem != null) {
                            if (clickedItemMeta.hasDisplayName()) { // If player clicked on custom item
                                if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Block Coin")) { // If the player clicks on a block coin
                                    PersistentDataContainer dataContainer = clickedItemMeta.getPersistentDataContainer();
                                    NamespacedKey valueKey = new NamespacedKey(HQCsOneBlock.getPlugin(), "value");
                                    int value = dataContainer.get(valueKey, PersistentDataType.INTEGER);
                                    playerData.balance += value;
                                    updateScoreboard(player);
                                    playerData.mail.remove(clickedItem);
                                    openInboxGUI(player, pageNum);
                                    player.sendMessage(ChatColor.GREEN + "Claimed $" + value);
                                } else {
                                    if (!isInventoryFull(player)) {
                                        player.getInventory().addItem(clickedItem);
                                        playerData.mail.remove(clickedItem);
                                        openInboxGUI(player, pageNum);
                                    } else {
                                        player.sendMessage(ChatColor.GREEN + "Cannot claim item! Inventory is full!");
                                    }
                                }
                            } else { // if the player clicks on vanilla item
                                if (!isInventoryFull(player)) {
                                    player.getInventory().addItem(clickedItem);
                                    playerData.mail.remove(clickedItem);
                                    openInboxGUI(player, pageNum);
                                } else {
                                    player.sendMessage(ChatColor.GREEN + "Cannot claim item! Inventory is full!");
                                }
                            }
                        }
                    }
                    // if player is clicking on the menu controls
                } else if (event.getSlot() > 44){
                    if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Previous page")) {
                        openInboxGUI(player, pageNum-1);
                    }
                    else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Next page")) {
                        openInboxGUI(player, pageNum+1);
                    }
                    else if (PlainTextComponentSerializer.plainText().serialize(clickedItemMeta.displayName()).equals("Flea Market")) {
                        player.performCommand("flea");
                    }
                }
            }
        }
    }

    private boolean isInventoryFull(Player player) {
        // Iterate through the player's inventory excluding armor slots and off hand (last 5 slots)
        for (int i = 0; i < player.getInventory().getSize()-5; i++) {
            ItemStack item = player.getInventory().getItem(i);
            // Check if any slot is empty (null)
            if (item == null) {
                return false; // Inventory is not full
            }
        }
        return true; // Inventory is full
    }
}
