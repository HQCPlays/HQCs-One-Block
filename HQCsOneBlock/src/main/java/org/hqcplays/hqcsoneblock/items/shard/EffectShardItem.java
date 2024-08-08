package org.hqcplays.hqcsoneblock.items.shard;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EffectShardItem extends AmethystShardItem {
    private static final List<PotionEffectType> potionEffects = Arrays.asList(
            PotionEffectType.SPEED,
            PotionEffectType.SLOWNESS,
            PotionEffectType.HASTE,
            PotionEffectType.MINING_FATIGUE,
            PotionEffectType.STRENGTH,
            PotionEffectType.JUMP_BOOST,
            PotionEffectType.NAUSEA,
            PotionEffectType.REGENERATION,
            PotionEffectType.RESISTANCE,
            PotionEffectType.FIRE_RESISTANCE,
            PotionEffectType.WATER_BREATHING,
            PotionEffectType.INVISIBILITY,
            PotionEffectType.BLINDNESS,
            PotionEffectType.NIGHT_VISION,
            PotionEffectType.HUNGER,
            PotionEffectType.WEAKNESS,
            PotionEffectType.POISON,
            PotionEffectType.WITHER,
            PotionEffectType.HEALTH_BOOST,
            PotionEffectType.ABSORPTION,
            PotionEffectType.SATURATION,
            PotionEffectType.GLOWING,
            PotionEffectType.LEVITATION,
            PotionEffectType.LUCK,
            PotionEffectType.UNLUCK,
            PotionEffectType.SLOW_FALLING,
            PotionEffectType.CONDUIT_POWER,
            PotionEffectType.DOLPHINS_GRACE,
            PotionEffectType.BAD_OMEN,
            PotionEffectType.HERO_OF_THE_VILLAGE,
            PotionEffectType.DARKNESS
    );

    public EffectShardItem() {
        super("Effect Shard", NamedTextColor.GOLD, "Right-click to obtain a random potion effect for 20 seconds!");
    }

    // Gives the player a random potion effect
    @Override
    public boolean doEffect(Player player) {
        // Generate random potion effect
        Random random = new Random(System.currentTimeMillis());
        PotionEffectType effectType = potionEffects.get(random.nextInt(potionEffects.size()));
        int duration = 400;
        int amplifier = random.nextInt(3); // Amplifier between 0 and 2 (Level I to III)

        // Apply random potion effect to the player
        player.addPotionEffect(new PotionEffect(effectType, duration, amplifier));

        // Notify the player
        player.sendMessage(ChatColor.GREEN + "You now have a level " + amplifier + " random effect for " + duration / 20 + " seconds!");

        return true;
    }
}
