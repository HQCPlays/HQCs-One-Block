package org.hqcplays.hqcsoneblock.enchantments;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

public class VampirismEnchantment extends ShardEnchantment {
    public VampirismEnchantment(HQCsOneBlock plugin) {
        super(plugin, "vampirism_I");
    }

    @Override
    protected void applyHitEffect(EntityDamageByEntityEvent event, Player player) {
        player.heal(event.getDamage() / 4);
    }

    @Override
    public String getLoreName() {
        return ChatColor.DARK_RED + "Vampirism I";
    }
}
