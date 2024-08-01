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
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.hqcplays.hqcsoneblock.progression.Age;
import org.hqcplays.hqcsoneblock.progression.Goal;
import org.hqcplays.hqcsoneblock.progression.Progression;

public class ProgressionCommand implements CommandExecutor, Listener {
    private int selectedAge;

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
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);

        // Add age items
        for (int i = 0; i < playerData.currentAge + 1; i++) {
            progressionMenu.setItem(i, Progression.ages[i].createMenuItem());
        }

        // Add grey stained glass panes
        ItemStack glassPane1 = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta glassMeta1 = glassPane1.getItemMeta();
        glassMeta1.setDisplayName(" ");
        glassPane1.setItemMeta(glassMeta1);

        ItemStack glassPane2 = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta glassMeta2 = glassPane2.getItemMeta();
        glassMeta2.setDisplayName(" ");
        glassPane2.setItemMeta(glassMeta2);

        for (int i = 9; i < 18; i++) {
            progressionMenu.setItem(i, (i - 9 == selectedAge) ? glassPane2 : glassPane1);
        }

        // Add goals for the selected age
        addGoalsToMenu(progressionMenu, playerData, Progression.ages[selectedAge]);

        player.openInventory(progressionMenu);
    }

    private void addGoalsToMenu(Inventory menu, PlayerSaveData playerData, Age age) {
        for (int i = 18; i < 54; i++) {
            menu.setItem(i, null); // Clear previous goals
        }

        // Add goal items
        int index = 18;
        for (Goal goal : age.getGoals()) {
            menu.setItem(index, goal.createMenuItem(playerData));
            index++;
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatColor.GREEN + "Progression Menu")) {
            event.setCancelled(true);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PAPER) {
                Player player = (Player) event.getWhoClicked();
                this.selectedAge = event.getSlot();
                openProgressionMenu(player);
            }
        }
    }
}
