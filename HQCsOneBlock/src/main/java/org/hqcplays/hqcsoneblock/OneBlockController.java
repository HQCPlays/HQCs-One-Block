package org.hqcplays.hqcsoneblock;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.hqcplays.hqcsoneblock.items.AmethystShardItems;
import org.hqcplays.hqcsoneblock.numberSheets.PricesSheet;

import java.util.*;

import static org.hqcplays.hqcsoneblock.HQCsOneBlock.playerBalances;
import static org.hqcplays.hqcsoneblock.HQCsOneBlock.updateScoreboard;

public class OneBlockController implements Listener {
    private static final HashMap<UUID, HashMap<Material, Double>> playerBlockChances = new HashMap<>();
    private static final HashMap<UUID, Set<Material>> playerUnlockedBlocks = new HashMap<>();

    public static void initializeBlockChances(UUID playerUUID) {
        HashMap<Material, Double> blockChances = new HashMap<>();
        blockChances.put(Material.COBBLESTONE, 0.85);
        blockChances.put(Material.OAK_WOOD, 0.09);
        blockChances.put(Material.DIRT, 0.05);
        blockChances.put(Material.AMETHYST_BLOCK, 0.01);
        playerBlockChances.putIfAbsent(playerUUID, blockChances);
        playerUnlockedBlocks.putIfAbsent(playerUUID, new HashSet<>());
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (!playerBlockChances.containsKey(playerUUID)) {
            initializeBlockChances(playerUUID);
        }

        World world = event.getPlayer().getWorld();
        if (world.getName().contains("island_" + playerUUID.toString())) {
            Location loc = new Location(world, 0, 0, 0);
            loc.getBlock().setType(Material.COBBLESTONE);
            loc = new Location(world, 0, -1, 0);
            if (loc.getBlock().getType() == Material.AIR) {
                loc.getBlock().setType(Material.COBBLESTONE);
            }
            loc = new Location(world, 1, -1, 0);
            if (loc.getBlock().getType() == Material.AIR) {
                loc.getBlock().setType(Material.COBBLESTONE);
            }
            loc = new Location(world, 0, -1, 1);
            if (loc.getBlock().getType() == Material.AIR) {
                loc.getBlock().setType(Material.COBBLESTONE);
            }
            loc = new Location(world, 0, -1, -1);
            if (loc.getBlock().getType() == Material.AIR) {
                loc.getBlock().setType(Material.COBBLESTONE);
            }
            loc = new Location(world, -1, -1, 0);
            if (loc.getBlock().getType() == Material.AIR) {
                loc.getBlock().setType(Material.COBBLESTONE);
            }
        }
    }

    @EventHandler
    public void onPistonExtend(BlockPistonExtendEvent event) {
        World world = event.getBlock().getWorld();
        if (world.getName().contains("island_")) {
            for (org.bukkit.block.Block block : event.getBlocks()) {
                if (block.getLocation().equals(new Location(world, 0, 0, 0))) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onPistonRetract(BlockPistonRetractEvent event) {
        World world = event.getBlock().getWorld();
        if (world.getName().contains("island_")) {
            for (org.bukkit.block.Block block : event.getBlocks()) {
                if (block.getLocation().equals(new Location(world, 0, 0, 0))) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        World world = player.getWorld();

        if (world.getName().contains("island_" + playerUUID.toString())) {
            if (loc.getBlockX() == 0 && loc.getBlockY() == 0 && loc.getBlockZ() == 0) {
                int currentBalance = playerBalances.getOrDefault(playerUUID, 0);
                playerBalances.put(playerUUID, currentBalance + 1);
                updateScoreboard(player);

                if (loc.getBlock().getType() == Material.AMETHYST_BLOCK) {
                    event.setCancelled(true);

                    Random rand = new Random(System.currentTimeMillis());
                    int randomInt = rand.nextInt(9);

                    switch (randomInt) {
                        case 0:
                            AmethystShardItems.dropGoldShard(player);
                            break;
                        case 1:
                            AmethystShardItems.dropRedShard(player);
                            break;
                        case 2:
                            player.sendMessage(ChatColor.RED + "Fix the green shard ya nerd");
                            break;
                        case 3:
                            AmethystShardItems.dropBlackShard(player);
                            break;
                        case 4:
                            AmethystShardItems.dropBlueShard(player);
                            break;
                        case 5:
                            AmethystShardItems.dropPurpleShard(player);
                            break;
                        case 6:
                            AmethystShardItems.dropRainbowShard(player);
                            break;
                        case 7:
                            AmethystShardItems.dropWhiteShard(player);
                            break;
                        case 8:
                            AmethystShardItems.dropEffectShard(player);
                            break;
                        default:
                            AmethystShardItems.dropGoldShard(player);
                            break;
                    }
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Random rand = new Random();
                        HashMap<Material, Double> blockChances = playerBlockChances.get(playerUUID);
                        double totalChance = blockChances.values().stream().mapToDouble(Double::doubleValue).sum();
                        double randomDouble = rand.nextDouble() * totalChance;
                        double cumulativeChance = 0.0;

                        for (Map.Entry<Material, Double> entry : blockChances.entrySet()) {
                            cumulativeChance += entry.getValue();
                            if (randomDouble <= cumulativeChance) {
                                loc.getBlock().setType(entry.getKey());
                                break;
                            }
                        }
                    }
                }.runTaskLater(HQCsOneBlock.getPlugin(HQCsOneBlock.class), 1L);
            }
        }
    }

    public static void handleBlockUnlock(Player player, ItemStack item) {
        UUID playerUUID = player.getUniqueId();
        int playerBalance = playerBalances.getOrDefault(playerUUID, 0);
        Material itemType = item.getType();
        int price = PricesSheet.getBlockUnlockPrices(itemType);

        if (playerUnlockedBlocks.get(playerUUID).contains(itemType)) {
            player.sendMessage(ChatColor.RED + "You have already unlocked " + itemType.name() + ".");
            return;
        }

        if (playerBalance >= price) {
            playerBalances.put(playerUUID, playerBalance - price);
            updateBlockChances(playerUUID, itemType);
            updateScoreboard(player);
            playerUnlockedBlocks.get(playerUUID).add(itemType);
            player.sendMessage(ChatColor.GREEN + "You unlocked " + itemType.name() + " for " + price + " Block Coins.");
        } else {
            player.sendMessage(ChatColor.RED + "You don't have enough Block Coins.");
        }
    }

    public static void updateBlockChances(UUID playerUUID, Material newBlock) {
        HashMap<Material, Double> blockChances = playerBlockChances.get(playerUUID);
        double newBlockChance = 0.01;

        blockChances.replace(Material.COBBLESTONE, blockChances.get(Material.COBBLESTONE) - newBlockChance);
        blockChances.put(newBlock, newBlockChance);
    }
}
