package org.hqcplays.hqcsoneblock.enchantments;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentEventsListener implements Listener {
    public final Map<NamespacedKey, ShardEnchantment> enchantments = new HashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            enchantments.forEach((key, enchantment) -> enchantment.applyArmor((Player) event.getEntity()));
        }
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            enchantments.forEach((key, enchantment) -> {
                if (enchantment.hasEnchantment(item))
                    enchantment.applyHitEffect(event, player);
            });
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        enchantments.forEach((key, enchantment) -> enchantment.applyArmor(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerArmorChange(PlayerArmorChangeEvent event) {
        enchantments.forEach((key, enchantment) -> enchantment.applyArmor(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        enchantments.forEach((key, enchantment) -> enchantment.applyArmor(event.getPlayer()));
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            enchantments.forEach((key, enchantment) -> enchantment.applyArmor((Player) event.getEntity()));
        }
    }
}
