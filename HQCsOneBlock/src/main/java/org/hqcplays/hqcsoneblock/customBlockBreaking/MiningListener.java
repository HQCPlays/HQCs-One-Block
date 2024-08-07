package org.hqcplays.hqcsoneblock.customBlockBreaking;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.numberSheets.MiningSpeedSheet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MiningListener implements Listener {
    private final MiningManager miningManager;
    private final Map<UUID, Long> lastSwingTimes = new HashMap<>();
    private static final long SWING_TIMEOUT = 1000; // 1 second timeout

    public MiningListener(MiningManager manager) {
        this.miningManager = manager;
        startSwingCheckTask();
    }

    @EventHandler
    public void onMine(PlayerAnimationEvent e) {
        Player player = e.getPlayer();
        if (!e.getAnimationType().equals(PlayerAnimationType.ARM_SWING)) return;
        if (!player.getGameMode().equals(GameMode.SURVIVAL)) return;

        lastSwingTimes.put(player.getUniqueId(), System.currentTimeMillis());

        Block block = player.getTargetBlockExact(4, FluidCollisionMode.NEVER);
        if (block == null || block.getType().equals(Material.AIR)) return;

        miningManager.updateAndNextPhase(player, block);

        int ticksToMine = MiningSpeedSheet.ticksToMine(player, block.getType());
        int blockStage = miningManager.getBlockStage(block.getLocation());
        float progress = blockStage / (float) ticksToMine; // Progress as a fraction of ticksToMine
        miningManager.sendBlockDamage(player, block.getLocation(), progress);
        blockStage = (blockStage + 1) % ticksToMine;
        miningManager.setBlockStage(block.getLocation(), blockStage);

        if (blockStage == 0) {
            miningManager.removeBlockStage(block.getLocation());

            if (MiningSpeedSheet.canMineBlock(player.getItemInHand(), block.getType())) {
                BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, player);
                org.bukkit.Bukkit.getPluginManager().callEvent(blockBreakEvent);

                if (!blockBreakEvent.isCancelled()) {
                    dropContainerContents(block);
                    block.getWorld().dropItemNaturally(block.getLocation(), block.getDrops().iterator().next());
                    block.setType(Material.AIR);
                }
            } else {
                player.sendMessage(ChatColor.RED + "Your pickaxe is not strong enough!");
            }
        }
    }

    private void dropContainerContents(Block block) {
        if (block.getState() instanceof Container) {
            Container container = (Container) block.getState();
            Inventory inventory = container.getInventory();
            for (ItemStack item : inventory.getContents()) {
                if (item != null && item.getType() != Material.AIR) {
                    block.getWorld().dropItemNaturally(block.getLocation(), item);
                }
            }
            inventory.clear();
        }
    }

    // Resets blocks after not mining them for a set amount of time
    private void startSwingCheckTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                for (UUID playerId : new HashMap<>(lastSwingTimes).keySet()) {
                    if (currentTime - lastSwingTimes.get(playerId) > SWING_TIMEOUT) {
                        Player player = Bukkit.getPlayer(playerId);
                        if (player != null) {
                            miningManager.resetPlayerBlockStages(player);
                        }
                        lastSwingTimes.remove(playerId);
                    }
                }
            }
        }.runTaskTimerAsynchronously(HQCsOneBlock.getPlugin(HQCsOneBlock.class), 0, 10);
    }
}
