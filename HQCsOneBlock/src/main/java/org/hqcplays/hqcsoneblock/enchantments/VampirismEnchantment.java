package org.hqcplays.hqcsoneblock.enchantments;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
    public Component getLoreName() {
        return Component.text("Vampirism I", NamedTextColor.DARK_RED);
    }
}
