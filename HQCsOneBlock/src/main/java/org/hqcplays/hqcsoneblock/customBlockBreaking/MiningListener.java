package org.hqcplays.hqcsoneblock.customBlockBreaking;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.numberSheets.MiningSpeedSheet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MiningListener implements Listener {
    private final MiningManager miningManager;
    private final Map<UUID, Long> lastSwingTimes = new HashMap<>();
    private final Map<UUID, Boolean> isLeftClicking = new HashMap<>();
    private static final long SWING_TIMEOUT = 100; // X second timeout

    public MiningListener(MiningManager manager) {
        this.miningManager = manager;
        startSwingCheckTask();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            isLeftClicking.put(player.getUniqueId(), true);
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.PHYSICAL) {
            isLeftClicking.put(player.getUniqueId(), false);
        }
    }

    @EventHandler
    public void onMine(PlayerAnimationEvent e) {
        Player player = e.getPlayer();
        if (!e.getAnimationType().equals(PlayerAnimationType.ARM_SWING)) return;
        if (!player.getGameMode().equals(GameMode.SURVIVAL)) return;

        if (!isLeftClicking.getOrDefault(player.getUniqueId(), false)) return;

        lastSwingTimes.put(player.getUniqueId(), System.currentTimeMillis());

        Block block = player.getTargetBlockExact(4, FluidCollisionMode.NEVER);
        if (block == null || block.getType().equals(Material.AIR)) return;

        if (ToolChecker.isShearEffective(block.getType())) {
            Objects.requireNonNull(player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED)).setBaseValue(1);
            return;
        }

        Objects.requireNonNull(player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED)).setBaseValue(0);

        // Check if the tool is effective against this block type
        if (!ToolChecker.isToolEffective(player.getItemInHand(), block.getType())) {
            player.sendMessage(ChatColor.RED + "Your tool is not effective for this block!");
            return;
        }

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
                    playBlockBreakSound(block);
                    spawnBlockBreakParticles(block);
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
                            isLeftClicking.remove(playerId);
                        }
                        lastSwingTimes.remove(playerId);
                    }
                }
            }
        }.runTaskTimerAsynchronously(HQCsOneBlock.getPlugin(HQCsOneBlock.class), 0, 10);
    }

    // Function to play the block's breaking sound
    private void playBlockBreakSound(Block block) {
        Material blockType = block.getType();
        Sound breakSound = blockType.createBlockData().getSoundGroup().getBreakSound();
        block.getWorld().playSound(block.getLocation(), breakSound, 1.0f, 1.0f);
    }

    // Function to spawn particles when breaking the block
    private void spawnBlockBreakParticles(Block block) {
        BlockData blockData = block.getBlockData();
        block.getWorld().spawnParticle(Particle.BLOCK, block.getLocation().add(0.5, 0.5, 0.5), 100, 0.3, 0.3, 0.3, blockData);
    }
}
