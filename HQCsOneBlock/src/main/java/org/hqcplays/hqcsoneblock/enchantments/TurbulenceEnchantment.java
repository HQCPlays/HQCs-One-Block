package org.hqcplays.hqcsoneblock.enchantments;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

import java.util.Random;

public class TurbulenceEnchantment extends ShardEnchantment {
    public TurbulenceEnchantment(HQCsOneBlock plugin) {
        super(plugin, "turbulence_I");
    }

    @Override
    protected void applyHitEffect(EntityDamageByEntityEvent event, Player player) {
        Random rand = new Random(System.currentTimeMillis());
        int randomInt = rand.nextInt(5);

        if (randomInt == 0) {
            World world = player.getWorld();
            Location windChargeLoc = new Location(world, event.getEntity().getLocation().getX(), event.getEntity().getLocation().getY() - 0.2, event.getEntity().getLocation().getZ());

            Entity spawnedEntity = world.spawnEntity(windChargeLoc, EntityType.WIND_CHARGE); // Example: spawn a Zombie

            // Set its velocity to aim upwards
            if (spawnedEntity instanceof LivingEntity) {
                Vector upwardVelocity = new Vector(0, -1, 0); // Adjust the Y value for more/less upward force
                spawnedEntity.setVelocity(upwardVelocity);
            }
        }
    }

    @Override
    public String getLoreName() {
        return ChatColor.WHITE + "Turbulence I";
    }
}
