package org.hqcplays.hqcsoneblock.items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.hqcplays.hqcsoneblock.PlayerSaveData;
import org.hqcplays.hqcsoneblock.enchantments.ShardEnchantment;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
    public static ItemStack blueShardSword;

    // Shard Armor
    public static ItemStack redShardHelmet;
    public static ItemStack redShardChestplate;
    public static ItemStack redShardLeggings;
    public static ItemStack redShardBoots;

    public static ItemStack purpleShardHelmet;
    public static ItemStack purpleShardChestplate;
    public static ItemStack purpleShardLeggings;
    public static ItemStack purpleShardBoots;

    // Shard Wands
    public static ItemStack grassifyWand;
    public static ItemStack treensformWand;
    public static ItemStack cropformWand;

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
        blackShardSword = createShardSword(Material.NETHERITE_SWORD, ChatColor.DARK_GRAY, "Black Shard Sword", "black_shard_sword", blackShard, ShardEnchantment.voiding);
        whiteShardSword = createShardSword(Material.IRON_SWORD, ChatColor.WHITE, "White Shard Sword", "white_shard_sword", whiteShard, ShardEnchantment.turbulence);
        redShardSword = createShardSword(Material.WOODEN_SWORD, ChatColor.RED, "Red Shard Sword", "red_shard_sword", redShard, ShardEnchantment.vampirism);
        blueShardSword = createShardSword(Material.DIAMOND_SWORD, ChatColor.DARK_BLUE, "Blue Shard Sword", "blue_shard_sword", blueShard, ShardEnchantment.asphyxiation);

        // Armors
        redShardHelmet = createShardHelmet(Material.DIAMOND_HELMET, ChatColor.RED, "Red Shard Helmet", "red_shard_helmet", redShard, ShardEnchantment.vitality);
        redShardChestplate = createShardChestplate(Material.DIAMOND_CHESTPLATE, ChatColor.RED, "Red Shard Chestplate", "red_shard_chestplate", redShard, ShardEnchantment.vitality);
        redShardLeggings = createShardLeggings(Material.DIAMOND_LEGGINGS, ChatColor.RED, "Red Shard Leggings", "red_shard_leggings", redShard, ShardEnchantment.vitality);
        redShardBoots = createShardBoots(Material.DIAMOND_BOOTS, ChatColor.RED, "Red Shard Boots", "red_shard_boots", redShard, ShardEnchantment.vitality);

        // Wands
        grassifyWand = createGrassifyWand(Material.STICK, ChatColor.GREEN, "Grassify Wand", "grassify_wand");
        treensformWand = createTreensformWand(Material.STICK, ChatColor.GREEN, "Tree-nsform Wand", "treensform_wand");
        cropformWand = createCropformWand(Material.STICK, ChatColor.GREEN, "Cropform Wand", "cropform_wand");
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

    public static ItemStack createShardSword(Material material, ChatColor color, String name, String craftingKey, ItemStack craftingShard, ShardEnchantment enchantment) {
        ItemStack customItem = new ItemStack(material, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(color + name);
        if (enchantment != null) {
            meta.setLore(Collections.singletonList(enchantment.getLoreName()));
            meta.getPersistentDataContainer().set(enchantment.getItemMetaKey(), PersistentDataType.BOOLEAN, true);
        }
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem);
        sr.shape(" A ",
                " A ",
                " S ");
        sr.setIngredient('A', craftingShard);
        sr.setIngredient('S', Material.STICK);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomWeapon(customItem);

        return customItem;
    }

    public static ItemStack createShardHelmet(Material material, ChatColor color, String name, String craftingKey, ItemStack craftingShard, ShardEnchantment enchantment) {
        ItemStack customItem = new ItemStack(material, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(color + name);
        if (enchantment != null) {
            meta.setLore(Collections.singletonList(enchantment.getLoreName()));
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            dataContainer.set(enchantment.getItemMetaKey(), PersistentDataType.BOOLEAN, true);
        }
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem);
        sr.shape("AAA",
                "A A",
                "   ");
        sr.setIngredient('A', craftingShard);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomArmor(customItem);

        return customItem;
    }

    public static ItemStack createShardChestplate(Material material, ChatColor color, String name, String craftingKey, ItemStack craftingShard, ShardEnchantment enchantment) {
        ItemStack customItem = new ItemStack(material, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(color + name);
        if (enchantment != null) {
            meta.setLore(Collections.singletonList(enchantment.getLoreName()));
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            dataContainer.set(enchantment.getItemMetaKey(), PersistentDataType.BOOLEAN, true);
        }
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem);
        sr.shape("A A",
                "AAA",
                "AAA");
        sr.setIngredient('A', craftingShard);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomArmor(customItem);

        return customItem;
    }

    public static ItemStack createShardLeggings(Material material, ChatColor color, String name, String craftingKey, ItemStack craftingShard, ShardEnchantment enchantment) {
        ItemStack customItem = new ItemStack(material, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(color + name);
        if (enchantment != null) {
            meta.setLore(Collections.singletonList(enchantment.getLoreName()));
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            dataContainer.set(enchantment.getItemMetaKey(), PersistentDataType.BOOLEAN, true);
        }
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem);
        sr.shape("AAA",
                "A A",
                "A A");
        sr.setIngredient('A', craftingShard);
        Bukkit.getServer().addRecipe(sr);

        CustomItemMasterList.addCustomArmor(customItem);

        return customItem;
    }

    public static ItemStack createShardBoots(Material material, ChatColor color, String name, String craftingKey, ItemStack craftingShard, ShardEnchantment enchantment) {
        ItemStack customItem = new ItemStack(material, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(color + name);
        if (enchantment != null) {
            meta.setLore(Collections.singletonList(enchantment.getLoreName()));
            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            dataContainer.set(enchantment.getItemMetaKey(), PersistentDataType.BOOLEAN, true);
        }
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr1 = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem);
        sr1.shape("   ",
                "A A",
                "A A");
        sr1.setIngredient('A', craftingShard);
        Bukkit.getServer().addRecipe(sr1);

        CustomItemMasterList.addCustomArmor(customItem);

        return customItem;
    }

    public static ItemStack createGrassifyWand(Material material, ChatColor color, String name, String craftingKey) {
        ItemStack customItem = new ItemStack(material, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(color + name);
        meta.setLore(Collections.singletonList("Turns dirt into grass!"));
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr1 = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem);
        sr1.shape("  G",
                " D ",
                "S  ");
        sr1.setIngredient('S', Material.STICK);
        sr1.setIngredient('D', Material.DIRT);
        sr1.setIngredient('G', greenShard);
        Bukkit.getServer().addRecipe(sr1);

        CustomItemMasterList.addCustomWand(customItem);

        return customItem;
    }

    public static ItemStack createTreensformWand(Material material, ChatColor color, String name, String craftingKey) {
        ItemStack customItem = new ItemStack(material, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(color + name);
        meta.setLore(Collections.singletonList("Turns saplings into other types of saplings!"));
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr1 = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem);
        sr1.shape(" RG",
                " TR",
                "S  ");
        sr1.setIngredient('S', Material.STICK);
        sr1.setIngredient('T', Material.OAK_SAPLING);
        sr1.setIngredient('G', greenShard);
        sr1.setIngredient('R', rainbowShard);
        Bukkit.getServer().addRecipe(sr1);

        CustomItemMasterList.addCustomWand(customItem);

        return customItem;
    }

    public static ItemStack createCropformWand(Material material, ChatColor color, String name, String craftingKey) {
        ItemStack customItem = new ItemStack(material, 1);
        ItemMeta meta = customItem.getItemMeta();
        meta.setDisplayName(color + name);
        meta.setLore(Collections.singletonList("Turns crops into other crops!"));
        customItem.setItemMeta(meta);

        // Crafting recipe
        ShapedRecipe sr1 = new ShapedRecipe(new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey), customItem);
        sr1.shape(" RG",
                " TR",
                "S  ");
        sr1.setIngredient('S', Material.STICK);
        sr1.setIngredient('T', Material.WHEAT_SEEDS);
        sr1.setIngredient('G', greenShard);
        sr1.setIngredient('R', rainbowShard);
        Bukkit.getServer().addRecipe(sr1);

        CustomItemMasterList.addCustomWand(customItem);

        return customItem;
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
                AmethystShardItems.greenShardEffect(event);
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
        PlayerSaveData playerData = HQCsOneBlock.dataManager.getPlayerData(player);

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Trigger custom event
            player.sendMessage(ChatColor.GREEN + "You have gained +10 Block Coin from the Gold Shard!");

            playerData.balance += 10;
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

    // Makes all plants within 24 blocks of the player grow
    public static void dropGreenShard(Player player) {
        player.getInventory().addItem(greenShard);
    }

    public static void greenShardEffect(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location c = player.getLocation();
            int r = 24;
            boolean didUse = false;

            for (int x = c.getBlockX() - r; x <= c.getBlockX() + r; x++) {
                for (int y = c.getBlockY() - r; y <= c.getBlockY() + r; y++) {
                    for (int z = c.getBlockZ() - r; z <= c.getBlockZ() + r; z++) {
                        Block block = new Location(c.getWorld(), x, y, z).getBlock();
                        // Keep bonemealing 30 times or until the block can't be bonemealed anymore.
                        for (int i = 0; i < 30; i++) {
                            if (block.applyBoneMeal(BlockFace.NORTH))
                                didUse = true;
                            else
                                break;
                        }
                    }
                }
            }

            if (didUse) {
                player.sendMessage(ChatColor.GREEN + "Nearby plants are now fully grown!");
                item.setAmount(item.getAmount() - 1);
            } else {
                player.sendMessage(ChatColor.DARK_GREEN + "Use this shard near some growable plants");
            }
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

            player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 2));

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
            event.getPlayer().sendMessage(ChatColor.GREEN + "You now have a level " + amplifier + " random effect for " + duration / 20 + " seconds!");

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
        int randomInt = rand.nextInt(8);

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
            case 7:
                greenShardEffect(event);
                break;
        }
    }

    @EventHandler
    public static void grassifyWandEffect(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (event.getAction().isRightClick() && item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Grassify Wand")) {
            if (event.getClickedBlock().getType() == Material.DIRT) {
                event.getClickedBlock().setType(Material.GRASS_BLOCK);
            }
        }
    }

    @EventHandler
    public static void treensformWandEffect(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (event.getAction().isRightClick() && item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Tree-nsform Wand")) {
            if (event.getClickedBlock().getType() == Material.OAK_SAPLING || event.getClickedBlock().getType() == Material.ACACIA_SAPLING || event.getClickedBlock().getType() == Material.DARK_OAK_SAPLING || event.getClickedBlock().getType() == Material.CHERRY_SAPLING || event.getClickedBlock().getType() == Material.JUNGLE_SAPLING || event.getClickedBlock().getType() == Material.BIRCH_SAPLING || event.getClickedBlock().getType() == Material.SPRUCE_SAPLING) {
                Random rand = new Random(System.currentTimeMillis());
                int randomInt = rand.nextInt(8);

                switch (randomInt) {
                    case 0:
                        event.getClickedBlock().setType(Material.ACACIA_SAPLING);
                        break;
                    case 1:
                        event.getClickedBlock().setType(Material.DARK_OAK_SAPLING);
                        break;
                    case 2:
                        event.getClickedBlock().setType(Material.OAK_SAPLING);
                        break;
                    case 3:
                        event.getClickedBlock().setType(Material.CHERRY_SAPLING);
                        break;
                    case 4:
                        event.getClickedBlock().setType(Material.JUNGLE_SAPLING);
                        break;
                    case 5:
                        event.getClickedBlock().setType(Material.SPRUCE_SAPLING);
                        break;
                    case 6:
                        event.getClickedBlock().setType(Material.BIRCH_SAPLING);
                        break;
                }
            }
        }
    }

    @EventHandler
    public static void cropformWandEffect(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (event.getAction().isRightClick() && item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Cropform Wand")) {
            if (event.getClickedBlock().getType() == Material.WHEAT || event.getClickedBlock().getType() == Material.PUMPKIN_STEM || event.getClickedBlock().getType() == Material.MELON_STEM || event.getClickedBlock().getType() == Material.PUMPKIN_SEEDS || event.getClickedBlock().getType() == Material.MELON_SEEDS || event.getClickedBlock().getType() == Material.BEETROOT_SEEDS || event.getClickedBlock().getType() == Material.CARROTS || event.getClickedBlock().getType() == Material.POTATOES) {
                Random rand = new Random(System.currentTimeMillis());
                int randomInt = rand.nextInt(6);

                switch (randomInt) {
                    case 0:
                        event.getClickedBlock().setType(Material.WHEAT);
                        break;
                    case 1:
                        event.getClickedBlock().setType(Material.PUMPKIN_STEM);
                        break;
                    case 2:
                        event.getClickedBlock().setType(Material.MELON_STEM);
                        break;
                    case 3:
                        event.getClickedBlock().setType(Material.BEETROOT_SEEDS);
                        break;
                    case 4:
                        event.getClickedBlock().setType(Material.CARROTS);
                        break;
                    case 5:
                        event.getClickedBlock().setType(Material.POTATOES);
                        break;
                }
            }
        }
    }
}
