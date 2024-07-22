package org.hqcplays.hqcsoneblock.enchantments;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

public class VoidingEnchantment extends ShardEnchantment {
    public VoidingEnchantment(HQCsOneBlock plugin) {
        super(plugin, "voiding_I");
    }

    @Override
    protected void applyHitEffect(EntityDamageByEntityEvent event, Player player) {
        // Apply extra damage
        double originalDamage = event.getDamage();
        event.setDamage(originalDamage + 4.0); // Example: add 2 hearts of damage

        // Damage the player
        player.damage(2); // Damage the player for 1 heart
    }

    @Override
    public String getLoreName() {
        return ChatColor.DARK_GRAY + "Voiding I";
    }
}
