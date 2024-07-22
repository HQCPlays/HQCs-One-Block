package org.hqcplays.hqcsoneblock.enchantments;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

public class VitalityEnchantment implements Listener {
    private final NamespacedKey vitalityKey;

    public VitalityEnchantment(HQCsOneBlock plugin) {
        this.vitalityKey = new NamespacedKey(plugin, "vitality_I");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        checkArmor(event.getPlayer());
    }

    @EventHandler
    public void onPlayerArmorChange(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        checkArmor(player);
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        checkArmor(player);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            checkArmor((Player) event.getEntity());
        }
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            checkArmor((Player) event.getEntity());
        }
    }

    private void checkArmor(Player player) {
        ItemStack[] armorItems = player.getInventory().getArmorContents();
        double extraHealth = 0;

        for (ItemStack armorItem : armorItems) {
            if (armorItem != null && armorItem.hasItemMeta()) {
                ItemMeta meta = armorItem.getItemMeta();
                if (meta != null) {
                    if (meta.getPersistentDataContainer().has(vitalityKey, PersistentDataType.STRING)) {
                        extraHealth += 2;
                    }
                }
            }
        }

        double baseHealth = 20; // Default health
        double newMaxHealth = baseHealth + extraHealth;
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newMaxHealth);

        // Ensure player doesn't exceed max health
        if (player.getHealth() > newMaxHealth) {
            player.setHealth(newMaxHealth);
        }
    }
}
