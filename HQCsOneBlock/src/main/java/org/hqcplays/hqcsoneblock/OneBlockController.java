package org.hqcplays.hqcsoneblock;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
import org.hqcplays.hqcsoneblock.progression.Progression;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static org.hqcplays.hqcsoneblock.HQCsOneBlock.updateScoreboard;
import static org.hqcplays.hqcsoneblock.items.RareOneBlockItems.stardust;

public class OneBlockController implements Listener {
    public static final Map<Material, Double> blockChances = new HashMap<>();

    public static void initializeBlockChances() {
        blockChances.put(Material.COBBLESTONE, 0.85);
        blockChances.put(Material.OAK_WOOD, 0.09);
        blockChances.put(Material.DIRT, 0.05);
        blockChances.put(Material.AMETHYST_BLOCK, 0.01);
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

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

        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);

        if (world.getName().contains("island_")) {
            if (loc.getBlockX() == 0 && loc.getBlockY() == 0 && loc.getBlockZ() == 0) {
                Progression.checkGoalUnlock(player, "mine_oneblock");
                playerData.balance++;
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
                            AmethystShardItems.dropGreenShard(player);
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

                if (loc.getBlock().getType() == Material.GLOWSTONE) {
                    event.setCancelled(true);

                    player.getInventory().addItem(stardust);
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Random rand = new Random();
                        double totalChance = playerData.blockChances.values().stream().mapToDouble(Double::doubleValue).sum();
                        double randomDouble = rand.nextDouble() * totalChance;
                        double cumulativeChance = 0.0;

                        for (Map.Entry<Material, Double> entry : playerData.blockChances.entrySet()) {
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
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);
        Material itemType = item.getType();
        int price = PricesSheet.getBlockUnlockPrices(itemType);

        if (playerData.unlockedBlocks.contains(itemType)) {
            player.sendMessage(ChatColor.RED + "You have already unlocked " + itemType.name() + ".");
            return;
        }

        if (playerData.balance >= price) {
            playerData.balance -= price;
            updateBlockChances(playerData, itemType);
            updateScoreboard(player);
            playerData.unlockedBlocks.add(itemType);
            player.sendMessage(ChatColor.GREEN + "You unlocked " + itemType.name() + " for " + price + " Block Coins.");

            switch (itemType) {
                case COAL_ORE:
                    Progression.checkGoalUnlock(player, "unlock_coal");
                    break;
                case IRON_ORE:
                    Progression.checkGoalUnlock(player, "unlock_iron");
                    break;
                case GOLD_ORE:
                    Progression.checkGoalUnlock(player, "unlock_gold");
                    break;
                case DIAMOND_ORE:
                    Progression.checkGoalUnlock(player, "unlock_diamond");
                    break;
                case ANCIENT_DEBRIS:
                    Progression.checkGoalUnlock(player, "unlock_netherite");
                    break;
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have enough Block Coins.");
        }
    }

    public static void updateBlockChances(PlayerSaveData playerData, Material newBlock) {
        Map<Material, Double> blockChances = playerData.blockChances;
        double newBlockChance = 0.01;

        blockChances.replace(Material.COBBLESTONE, blockChances.get(Material.COBBLESTONE) - newBlockChance);
        blockChances.put(newBlock, newBlockChance);
    }
}
