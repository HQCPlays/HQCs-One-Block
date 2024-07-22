package org.hqcplays.hqcsoneblock.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

public class VoidingEnchantment implements Listener {
    private final NamespacedKey voidingKey;

    public VoidingEnchantment(HQCsOneBlock plugin) {
        this.voidingKey = new NamespacedKey(plugin, "voiding_I");
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
                    if (meta.getPersistentDataContainer().has(voidingKey, PersistentDataType.STRING)) {
                        // Apply extra damage
                        double originalDamage = event.getDamage();
                        event.setDamage(originalDamage + 4.0); // Example: add 2 hearts of damage

                        // Damage the player
                        player.damage(2); // Damage the player for 1 heart
                    }
                }
            }
        }
    }
}
