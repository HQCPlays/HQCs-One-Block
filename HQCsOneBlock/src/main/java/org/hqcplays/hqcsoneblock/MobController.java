package org.hqcplays.hqcsoneblock;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MobController implements Listener {
    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            updateHealthNametag(entity);
        }
    }

    public static void updateHealthNametag(LivingEntity entity) {
        double health = entity.getHealth();
        double maxHealth = entity.getAttribute(org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH).getValue();
        String name = formatEntityName(entity.getType().name());
        entity.setCustomName(String.format("%s - %.1f/%.1f HP", name, health, maxHealth));
        entity.setCustomNameVisible(true);
    }

    private static String formatEntityName(String name) {
        name = name.replace("_", " ").toLowerCase();
        String[] words = name.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return formattedName.toString().trim();
    }
}
