package org.hqcplays.hqcsoneblock;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;

import static org.hqcplays.hqcsoneblock.items.TechItems.automationCore;

public class MobSpawnerController implements Listener {

    private final JavaPlugin plugin;

    public MobSpawnerController(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public static ItemStack pigSpawner = createSpawner(EntityType.PIG);
    public static ItemStack sheepSpawner = createSpawner(EntityType.SHEEP);
    public static ItemStack cowSpawner = createSpawner(EntityType.COW);
    public static ItemStack chickenSpawner = createSpawner(EntityType.CHICKEN);

    public static ItemStack spiderSpawner = createSpawner(EntityType.SPIDER);
    public static ItemStack zombieSpawner = createSpawner(EntityType.ZOMBIE);
    public static ItemStack creeperSpawner = createSpawner(EntityType.CREEPER);
    public static ItemStack skeletonSpawner = createSpawner(EntityType.SKELETON);
    public static ItemStack blazeSpawner = createSpawner(EntityType.BLAZE);
    public static ItemStack endermanSpawner = createSpawner(EntityType.ENDERMAN);

    public static void init() {
        defineSpawnerRecipe(Material.PORKCHOP, pigSpawner, "pig_spawner");
        defineSpawnerRecipe(Material.WHITE_WOOL, sheepSpawner, "sheep_spawner");
        defineSpawnerRecipe(Material.LEATHER, cowSpawner, "cow_spawner");
        defineSpawnerRecipe(Material.CHICKEN, chickenSpawner, "chicken_spawner");

        defineSpawnerRecipe(Material.STRING, spiderSpawner, "spider_spawner");
        defineSpawnerRecipe(Material.ROTTEN_FLESH, zombieSpawner, "zombie_spawner");
        defineSpawnerRecipe(Material.GUNPOWDER, creeperSpawner, "creeper_spawner");
        defineSpawnerRecipe(Material.BONE, skeletonSpawner, "skeleton_spawner");
        defineSpawnerRecipe(Material.BLAZE_ROD, blazeSpawner, "blaze_spawner");
        defineSpawnerRecipe(Material.ENDER_PEARL, endermanSpawner, "enderman_spawner");
    }

    public static void defineSpawnerRecipe(Material mobMaterial, ItemStack spawner, String craftingKey) {
        // Create the recipe
        NamespacedKey key = new NamespacedKey(HQCsOneBlock.getPlugin(HQCsOneBlock.class), craftingKey);
        ShapedRecipe recipe = new ShapedRecipe(key, spawner);

        // Define the shape of the recipe
        recipe.shape("PIP",
                "IAI",
                "PIP");

        // Define the ingredients
        recipe.setIngredient('P', mobMaterial);
        recipe.setIngredient('I', Material.IRON_BARS);
        recipe.setIngredient('A', automationCore.item);

        // Register the recipe
        Bukkit.addRecipe(recipe);
    }

    public static ItemStack createSpawner(EntityType entityType) {
        ItemStack spawner = new ItemStack(Material.SPAWNER);
        BlockStateMeta meta = (BlockStateMeta) spawner.getItemMeta();
        CreatureSpawner spawnerState = (CreatureSpawner) meta.getBlockState();
        spawnerState.setSpawnedType(entityType);
        meta.setBlockState(spawnerState);
        spawner.setItemMeta(meta);
        return spawner;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        if (item == null || item.getType() != Material.SPAWNER) return;

        Block placedBlock = event.getBlockPlaced();
        if (placedBlock.getType() != Material.SPAWNER) return;

        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        if (meta == null) return;

        CreatureSpawner spawnerState = (CreatureSpawner) meta.getBlockState();
        EntityType entityType = spawnerState.getSpawnedType();

        Bukkit.getScheduler().runTask(plugin, () -> {
            CreatureSpawner placedSpawnerState = (CreatureSpawner) placedBlock.getState();
            placedSpawnerState.setSpawnedType(entityType);
            placedSpawnerState.update();
        });
    }
}
