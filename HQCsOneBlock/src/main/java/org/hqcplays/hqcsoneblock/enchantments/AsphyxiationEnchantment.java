package org.hqcplays.hqcsoneblock.enchantments;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

public class AsphyxiationEnchantment extends ShardEnchantment {
    public AsphyxiationEnchantment(HQCsOneBlock plugin) {
        super(plugin, "asphyxiation_I");
    }

    @Override
    protected void applyHitEffect(EntityDamageByEntityEvent event, Player player) {
        if (event.getEntity() instanceof LivingEntity) {
            ((LivingEntity) event.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 5));
        }
    }

    @Override
    public Component getLoreName() {
        return Component.text("Asphyxiation I", NamedTextColor.DARK_BLUE);
    }
}
