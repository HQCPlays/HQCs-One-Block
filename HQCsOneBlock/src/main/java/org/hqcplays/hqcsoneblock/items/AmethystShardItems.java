package org.hqcplays.hqcsoneblock.items;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import java.util.*;

import static org.hqcplays.hqcsoneblock.HQCsOneBlock.playerBalances;
import static org.hqcplays.hqcsoneblock.HQCsOneBlock.updateScoreboard;

public class AmethystShardItems implements Listener {
    // Shards
    public static ItemStack goldShard;
    public static ItemStack redShard;
    public static ItemStack blueShard;
    public static ItemStack greenShard;
    public static ItemStack purpleShard;
    public static ItemStack blackShard;
    public static ItemStack whiteShard;
    public static ItemStack effectShard;
    public static ItemStack rainbowShard;

    // Shard Swords
    public static ItemStack blackShardSword;
    public static ItemStack whiteShardSword;
    public static ItemStack redShardSword;

    // Shard Armor
    public static ItemStack redShardHelmet;
    public static ItemStack redShardChestplate;
    public static ItemStack redShardLeggings;
    public static ItemStack redShardBoots;

    private static final List<PotionEffectType> potionEffects = Arrays.asList(
            PotionEffectType.SPEED,
            PotionEffectType.SLOWNESS,
            PotionEffectType.HASTE,
            PotionEffectType.MINING_FATIGUE,
            PotionEffectType.STRENGTH,
            PotionEffectType.JUMP_BOOST,
            PotionEffectType.NAUSEA,
            PotionEffectType.REGENERATION,
            PotionEffectType.RESISTANCE,
            PotionEffectType.FIRE_RESISTANCE,
            PotionEffectType.WATER_BREATHING,
            PotionEffectType.INVISIBILITY,
            PotionEffectType.BLINDNESS,
            PotionEffectType.NIGHT_VISION,
            PotionEffectType.HUNGER,
            PotionEffectType.WEAKNESS,
            PotionEffectType.POISON,
            PotionEffectType.WITHER,
            PotionEffectType.HEALTH_BOOST,
            PotionEffectType.ABSORPTION,
            PotionEffectType.SATURATION,
            PotionEffectType.GLOWING,
            PotionEffectType.LEVITATION,
            PotionEffectType.LUCK,
            PotionEffectType.UNLUCK,
            PotionEffectType.SLOW_FALLING,
            PotionEffectType.CONDUIT_POWER,
            PotionEffectType.DOLPHINS_GRACE,
            PotionEffectType.BAD_OMEN,
            PotionEffectType.HERO_OF_THE_VILLAGE,
            PotionEffectType.DARKNESS
    );

    public static void init() {
        // Shards
        createGoldShard();
        createRedShard();
        createBlueShard();
        createGreenShard();
        createPurpleShard();
        createBlackShard();
        createWhiteShard();
        createEffectShard();
        createRainbowShard();

        // Swords
        createBlackShardSword();
        createWhiteShardSword();
        createRedShardSword();

        // Armors
        createRedShardHelmet();
        createRedShardChestplate();
        createRedShardLeggings();
        createRedShardBoots();
    }

    public static void createGoldShard() {
        ItemStack customItem = new ItemStack(Material.AMETHYST_SHARD, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Gold Shard");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Right-click to obtain +10 Block Coins!"));
        customItem.setItemMeta(meta);

        goldShard = customItem;
    }

    public static void createRedShard() {
        ItemStack customItem = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Red Shard");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Right-click to obtain Regeneration 2 for 10 seconds!"));
        customItem.setItemMeta(meta);

        redShard = customItem;
    }

    public static void createBlueShard() {
        ItemStack customItem = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "Blue Shard");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Right-click to spawn a 2x2 infinite water source directly below you!"));
        customItem.setItemMeta(meta);

        blueShard = customItem;
    }

    public static void createGreenShard() {
        ItemStack customItem = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Green Shard");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Right-click to instantly grow all plants around you!"));
        customItem.setItemMeta(meta);

        greenShard = customItem;
    }

    public static void createPurpleShard() {
        ItemStack customItem = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Purple Shard");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Right-click to obtain Haste 1 for 30 seconds!"));
        customItem.setItemMeta(meta);

        purpleShard = customItem;
    }

    public static void createBlackShard() {
        ItemStack customItem = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.BLACK + "Black Shard");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "I wonder what this one does..."));
        customItem.setItemMeta(meta);

        blackShard = customItem;
    }

    public static void createWhiteShard() {
        ItemStack customItem = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "White Shard");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Right-click to enter the upper atmosphere!"));
        customItem.setItemMeta(meta);

        whiteShard = customItem;
    }

    public static void createEffectShard() {
        ItemStack customItem = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Effect Shard");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Right-click to obtain a random potion effect for 20 seconds!"));
        customItem.setItemMeta(meta);

        effectShard = customItem;
    }

    public static void createRainbowShard() {
        ItemStack customItem = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Rainbow Shard");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Right-click to activate a random shard effect (including black)!"));
        customItem.setItemMeta(meta);

        rainbowShard = customItem;
    }

    public static void createBlackShardSword() {
        ItemStack customItem = new ItemStack(Material.NETHERITE_SWORD, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "Black Shard Sword");
        meta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "Voiding I"));
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "voiding_I"), PersistentDataType.STRING, "voiding_I");
        customItem.setItemMeta(meta);

        blackShardSword = customItem;

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("blackshardsword"), customItem);
        sr.shape(" B ",
                " B ",
                " S ");
        sr.setIngredient('B', blackShard);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void createWhiteShardSword() {
        ItemStack customItem = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "White Shard Sword");
        meta.setLore(Collections.singletonList(ChatColor.WHITE + "Turbulence I"));
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "turbulence_I"), PersistentDataType.STRING, "turbulence_I");
        customItem.setItemMeta(meta);

        whiteShardSword = customItem;

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("whiteshardsword"), customItem);
        sr.shape(" B ",
                " B ",
                " S ");
        sr.setIngredient('B', whiteShard);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void createRedShardSword() {
        ItemStack customItem = new ItemStack(Material.WOODEN_SWORD, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Red Shard Sword");
        meta.setLore(Collections.singletonList(ChatColor.DARK_RED + "Vampirism I"));
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "vampirism_I"), PersistentDataType.STRING, "vampirism_I");
        customItem.setItemMeta(meta);

        redShardSword = customItem;

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("redshardsword"), customItem);
        sr.shape(" B ",
                " B ",
                " S ");
        sr.setIngredient('B', redShard);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void createRedShardHelmet() {
        ItemStack customItem = new ItemStack(Material.DIAMOND_HELMET, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Red Shard Helmet");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Vitality I"));
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "vitality_I"), PersistentDataType.STRING, "vitality_I");
        customItem.setItemMeta(meta);

        redShardHelmet = customItem;

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("redshardhelmet"), customItem);
        sr.shape("RRR",
                "R R",
                "   ");
        sr.setIngredient('R', redShard);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void createRedShardChestplate() {
        ItemStack customItem = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Red Shard Chestplate");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Vitality I"));
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "vitality_I"), PersistentDataType.STRING, "vitality_I");
        customItem.setItemMeta(meta);

        redShardChestplate = customItem;

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("redshardchestplate"), customItem);
        sr.shape("R R",
                "RRR",
                "RRR");
        sr.setIngredient('R', redShard);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void createRedShardLeggings() {
        ItemStack customItem = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Red Shard Leggings");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Vitality I"));
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "vitality_I"), PersistentDataType.STRING, "vitality_I");
        customItem.setItemMeta(meta);

        redShardLeggings = customItem;

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(NamespacedKey.minecraft("redshardleggings"), customItem);
        sr.shape("RRR",
                "R R",
                "R R");
        sr.setIngredient('R', redShard);
        Bukkit.getServer().addRecipe(sr);
    }

    public static void createRedShardBoots() {
        ItemStack customItem = new ItemStack(Material.DIAMOND_BOOTS, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Red Shard Boots");
        meta.setLore(Collections.singletonList(ChatColor.YELLOW + "Vitality I"));
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), "vitality_I"), PersistentDataType.STRING, "vitality_I");
        customItem.setItemMeta(meta);

        redShardBoots = customItem;

        // Crafting recipe
        ShapedRecipe sr1 = new ShapedRecipe(NamespacedKey.minecraft("redshardboots"), customItem);
        sr1.shape("   ",
                "R R",
                "R R");
        sr1.setIngredient('R', redShard);
        Bukkit.getServer().addRecipe(sr1);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        UUID playerUUID = player.getUniqueId();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            // Check items
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Gold Shard")) {
                AmethystShardItems.goldShardEffect(event);
            }

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Red Shard")) {
                AmethystShardItems.redShardEffect(event);
            }

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.BLACK + "Black Shard")) {
                AmethystShardItems.blackShardEffect(event);
            }

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.WHITE + "White Shard")) {
                AmethystShardItems.whiteShardEffect(event);
            }

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Purple Shard")) {
                AmethystShardItems.purpleShardEffect(event);
            }

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Blue Shard") && player.getWorld().getName().contains("island_")) {
                AmethystShardItems.blueShardEffect(event);
            }

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Effect Shard")) {
                AmethystShardItems.effectShardEffect(event);
            }

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Green Shard") && player.getWorld().getName().contains("island_")) {
                //AmethystShardItems.greenShardEffect(growEvent);
            }

            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Rainbow Shard") && player.getWorld().getName().contains("island_")) {
                AmethystShardItems.rainbowShardEffect(event);
            }
        }
    }

    // Redundant variables caught by cflip_
    // Shard were Arinlol's idea
    // Gives BC
    public static void dropGoldShard(Player player) {
        player.getInventory().addItem(goldShard);
    }

    public static void goldShardEffect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        UUID playerUUID = player.getUniqueId();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Trigger custom event
            player.sendMessage(ChatColor.GREEN + "You have gained +10 Block Coin from the Gold Shard!");

            int currentBalance = playerBalances.getOrDefault(playerUUID, 0);
            playerBalances.put(playerUUID, currentBalance + 10);
            updateScoreboard(player);

            item.setAmount(item.getAmount() - 1);
        }
    }

    // Gives Regen
    public static void dropRedShard(Player player) {
        player.getInventory().addItem(redShard);
    }

    public static void redShardEffect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        UUID playerUUID = player.getUniqueId();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Trigger custom event
            player.sendMessage(ChatColor.GREEN + "You have Regeneration 2 for 10 seconds!");

            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 1));

            item.setAmount(item.getAmount() - 1);
        }
    }

    // Spawns an infinite water source
    public static void dropBlueShard(Player player) {
        player.getInventory().addItem(blueShard);
    }

    public static void blueShardEffect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        UUID playerUUID = player.getUniqueId();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Trigger custom event
            player.sendMessage(ChatColor.GREEN + "You now have an infinite water source!");

            Location blueLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ());
            blueLoc.getBlock().setType(Material.WATER);
            blueLoc = new Location(player.getWorld(), player.getLocation().getX() + 1, player.getLocation().getY() - 1, player.getLocation().getZ());
            blueLoc.getBlock().setType(Material.WATER);
            blueLoc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() - 1, player.getLocation().getZ() + 1);
            blueLoc.getBlock().setType(Material.WATER);
            blueLoc = new Location(player.getWorld(), player.getLocation().getX() + 1, player.getLocation().getY() - 1, player.getLocation().getZ() + 1);
            blueLoc.getBlock().setType(Material.WATER);

            item.setAmount(item.getAmount() - 1);
        }
    }

    // Makes all plants on the island grow
    public void dropGreenShard(Player player) {
        player.getInventory().addItem(greenShard);
    }

    public void greenShardEffect (BlockGrowEvent growEvent) {
        Block block = growEvent.getBlock();
        Material material = block.getType();

        // Check if the block is a plant that can grow
        if (material == Material.WHEAT || material == Material.CARROTS || material == Material.POTATOES ||
                material == Material.BEETROOTS || material == Material.NETHER_WART || material == Material.COCOA ||
                material == Material.MELON_STEM || material == Material.PUMPKIN_STEM || material == Material.SWEET_BERRY_BUSH) {

            // Cancel the event to prevent default growth
            growEvent.setCancelled(true);

            // Set the block to its fully-grown state
            if (block.getBlockData() instanceof Ageable ageable) {
                ageable.setAge(ageable.getMaximumAge());
                block.setBlockData(ageable);
            }
        } else if (material == Material.OAK_SAPLING || material == Material.SPRUCE_SAPLING ||
                material == Material.BIRCH_SAPLING || material == Material.JUNGLE_SAPLING ||
                material == Material.ACACIA_SAPLING || material == Material.DARK_OAK_SAPLING) {

            // Cancel the event to prevent default growth
            growEvent.setCancelled(true);

            // Schedule task to replace the sapling with the corresponding tree type
            new BukkitRunnable() {
                @Override
                public void run() {
                    switch (material) {
                        case OAK_SAPLING:
                            block.setType(Material.OAK_LOG);
                            break;
                        case SPRUCE_SAPLING:
                            block.setType(Material.SPRUCE_LOG);
                            break;
                        case BIRCH_SAPLING:
                            block.setType(Material.BIRCH_LOG);
                            break;
                        case JUNGLE_SAPLING:
                            block.setType(Material.JUNGLE_LOG);
                            break;
                        case ACACIA_SAPLING:
                            block.setType(Material.ACACIA_LOG);
                            break;
                        case DARK_OAK_SAPLING:
                            block.setType(Material.DARK_OAK_LOG);
                            break;
                        default:
                            break;
                    }
                }
            }.runTaskLater(HQCsOneBlock.getPlugin(HQCsOneBlock.class), 1L); // Schedule to run immediately (1 tick delay)
        }
    }

    // Gives Haste
    public static void dropPurpleShard(Player player) {
        player.getInventory().addItem(purpleShard);
    }

    public static void purpleShardEffect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        UUID playerUUID = player.getUniqueId();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            player.sendMessage(ChatColor.GREEN + "You now have Haste 1 for 30 seconds!");

            player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 600, 0));

            item.setAmount(item.getAmount() - 1);
        }
    }

    // Just straight up kills the player, because screw you
    public static void dropBlackShard(Player player) {
        player.getInventory().addItem(blackShard);
    }

    public static void blackShardEffect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        UUID playerUUID = player.getUniqueId();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            player.sendMessage(ChatColor.GREEN + "You have died!");

            player.addPotionEffect(new PotionEffect(PotionEffectType.INSTANT_DAMAGE, 200, 100));

            item.setAmount(item.getAmount() - 1);
        }
    }

    // Levitation 1 million
    public static void dropWhiteShard(Player player) {
        player.getInventory().addItem(whiteShard);
    }

    public static void whiteShardEffect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        UUID playerUUID = player.getUniqueId();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            player.sendMessage(ChatColor.GREEN + "You are now entering the upper atmosphere!");

            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 100));

            item.setAmount(item.getAmount() - 1);
        }
    }

    // Gives the player a random potion effect
    public static void dropEffectShard(Player player) {
        player.getInventory().addItem(effectShard);
    }

    public static void effectShardEffect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        UUID playerUUID = player.getUniqueId();

        if (event.getAction().name().contains("RIGHT_CLICK")) {
            // Generate random potion effect
            Random random = new Random(System.currentTimeMillis());
            PotionEffectType effectType = potionEffects.get(random.nextInt(potionEffects.size()));
            int duration = 400;
            int amplifier = random.nextInt(3); // Amplifier between 0 and 2 (Level I to III)

            // Apply random potion effect to the player
            event.getPlayer().addPotionEffect(new PotionEffect(effectType, duration, amplifier));

            // Notify the player
            event.getPlayer().sendMessage(ChatColor.GREEN + "You now have a level " + amplifier + " random effect for " + duration/20 + " seconds!");

            // Decrease the item amount by 1
            item.setAmount(item.getAmount() - 1);
        }
    }

    // Random effect from all the other shards
    public static void dropRainbowShard(Player player) {
        player.getInventory().addItem(rainbowShard);
    }

    public static void rainbowShardEffect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        UUID playerUUID = player.getUniqueId();

        Random rand = new Random(System.currentTimeMillis());
        int randomInt = rand.nextInt(7);

        switch (randomInt) {
            case 0:
                goldShardEffect(event);
                break;
            case 1:
                redShardEffect(event);
                break;
            case 2:
                blueShardEffect(event);
                break;
            case 3:
                purpleShardEffect(event);
                break;
            case 4:
                blackShardEffect(event);
                break;
            case 5:
                whiteShardEffect(event);
                break;
            case 6:
                effectShardEffect(event);
                break;
//            case 7:
//                greenShardEffect(growEvent);
//                break;
        }
    }
}
