package org.hqcplays.hqcsoneblock.customBlockBreaking;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;

import static org.hqcplays.hqcsoneblock.numberSheets.MiningSpeedSheet.getPickaxeMiningSpeed;

public class MiningSpeedManager implements Listener {
    @EventHandler
    public void onItemSwitch(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();

        int lastItemSlot = event.getPreviousSlot();
        ItemStack lastItem = player.getInventory().getItem(lastItemSlot);

        int newItemSlot = event.getNewSlot();
        ItemStack newItem = player.getInventory().getItem(newItemSlot);

        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);

        playerData.miningSpeed = getPickaxeMiningSpeed(newItem);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        playerData.miningSpeed = getPickaxeMiningSpeed(player.getItemInHand());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        playerData.miningSpeed = getPickaxeMiningSpeed(player.getItemInHand());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        playerData.miningSpeed = getPickaxeMiningSpeed(player.getItemInHand());
    }
}
