package org.hqcplays.hqcsoneblock.enchantments;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import java.util.Random;

public class TurbulenceEnchantment extends ShardEnchantment {
    public TurbulenceEnchantment(HQCsOneBlock plugin) {
        super(plugin, "turbulence_I");
    }

    @Override
    protected void applyHitEffect(EntityDamageByEntityEvent event, Player player) {
        Random rand = new Random(System.currentTimeMillis());
        int randomInt = rand.nextInt(4);
        Entity hitEntity = event.getEntity();

        if (randomInt == 0) {
            if (hitEntity instanceof LivingEntity) {
                ((LivingEntity) hitEntity).addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20, 8));
            }
        }
    }

    @Override
    public Component getLoreName() {
        return Component.text("Turbulence I", NamedTextColor.WHITE);
    }
}
