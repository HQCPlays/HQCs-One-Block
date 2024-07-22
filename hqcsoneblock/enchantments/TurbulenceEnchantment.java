package org.hqcplays.hqcsoneblock.enchantments;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import java.util.Random;

public class TurbulenceEnchantment implements Listener {
    private final NamespacedKey turbulenceKey;

    public TurbulenceEnchantment(HQCsOneBlock plugin) {
        this.turbulenceKey = new NamespacedKey(plugin, "turbulence_I");
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();

            // Check if the item has ItemMeta
            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    // Get the PersistentDataContainer from the ItemMeta
                    if (meta.getPersistentDataContainer().has(turbulenceKey, PersistentDataType.STRING)) {
                        Random rand = new Random(System.currentTimeMillis());
                        int randomInt = rand.nextInt(5);

                        if (randomInt == 0) {
                            World world = player.getWorld();
                            Location windChargeLoc = new Location(world, event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY() - 0.2, event.getEntity().getLocation().getZ());

                            Entity spawnedEntity = world.spawnEntity(windChargeLoc, EntityType.WIND_CHARGE); // Example: spawn a Zombie

                            // Set its velocity to aim upwards
                            if (spawnedEntity instanceof LivingEntity) {
                                Vector upwardVelocity = new Vector(0, -1, 0); // Adjust the Y value for more/less upward force
                                spawnedEntity.setVelocity(upwardVelocity);
                            }
                        }
                    }
                }
            }
        }
    }
}
