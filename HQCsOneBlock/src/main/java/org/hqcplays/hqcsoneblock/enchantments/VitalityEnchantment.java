package org.hqcplays.hqcsoneblock.enchantments;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;

public class VitalityEnchantment extends ShardEnchantment {
    public VitalityEnchantment(HQCsOneBlock plugin) {
        super(plugin, "vitality_I");
    }

    @Override
    protected void applyArmor(Player player) {
        ItemStack[] armorItems = player.getInventory().getArmorContents();
        double extraHealth = 0;

        for (ItemStack armorItem : armorItems) {
            if (hasEnchantment(armorItem)) {
                extraHealth += 2;
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

    @Override
    public Component getLoreName() {
        return Component.text("Vitality I", NamedTextColor.YELLOW);
    }
}
