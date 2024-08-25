package org.hqcplays.hqcsoneblock.enchantments;

import java.util.Set;
import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.hqcplays.hqcsoneblock.HQCsOneBlock;
import org.jetbrains.annotations.NotNull;

import io.papermc.paper.enchantments.EnchantmentRarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class VitalityEnchantment extends ShardEnchantment {
    private final NamespacedKey key;

    public VitalityEnchantment(HQCsOneBlock plugin) {
        super(plugin, "vitality");
        this.key = new NamespacedKey(plugin, "vitality");
    }

    @Override
    protected void applyArmor(Player player) {
        ItemStack[] armorItems = player.getInventory().getArmorContents();
        double extraHealth = 0;

        for (ItemStack armorItem : armorItems) {
            if (hasEnchantment(armorItem)) {
                extraHealth += 2; // Increase health by 2 for each enchanted armor piece
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
    public String getLoreName() {
        return ChatColor.YELLOW + "Vitality I";
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return this.key;
    }

    @Override
    public @NotNull String getTranslationKey() {
        return this.key.toString();
    }

    @Override
    public @NotNull String getName() {
        return "Vitality";
    }

    @Override
    public int getMaxLevel() {
        return 3; // Example maximum level
    }

    @Override
    public int getStartLevel() {
        return 1; // Example starting level
    }

    @Override
    public @NotNull EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR; // This enchantment applies to armor
    }

    @Override
    public boolean isTreasure() {
        return false; // Change if this enchantment is considered treasure
    }

    @Override
    public boolean isCursed() {
        return false; // Change if this enchantment is cursed
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        // Define conflicts with other enchantments, if any
        return false; // No conflicts with other enchantments by default
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        // Specifies which items can be enchanted with Vitality
        return item.getType().name().contains("ARMOR"); // Allow enchantment on armor items
    }

    @Override
    public @NotNull Component displayName(int level) {
        String name = "Vitality";
        Component nameComponent = Component.text(name, NamedTextColor.YELLOW);

        if (level > 1) {
            nameComponent = nameComponent.append(Component.text(" " + level, NamedTextColor.GRAY));
        }

        return nameComponent;
    }

    @Override
    public boolean isTradeable() {
        return true; // Define whether the enchantment can be obtained through trading
    }

    @Override
    public boolean isDiscoverable() {
        return true; // Define whether the enchantment can be discovered in-game
    }

    @Override
    public int getMinModifiedCost(int level) {
        return 10 + (level * 7); // Define the minimum XP cost for the enchantment
    }

    @Override
    public int getMaxModifiedCost(int level) {
        return 20 + (level * 14); // Define the maximum XP cost for the enchantment
    }

    @Override
    public int getAnvilCost() {
        return 2; // Define the cost of combining this enchantment in an anvil
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.UNCOMMON; // Define the rarity of the enchantment
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
        // Define damage increase per level against an entity category if applicable
        return 0;
    }

    @Override
    public float getDamageIncrease(int level, @NotNull EntityType entityType) {
        // Define damage increase per level against a specific entity type if applicable
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlotGroup> getActiveSlotGroups() {
        return Collections.singleton(EquipmentSlotGroup.ARMOR); // Active slot group for the enchantment
    }

    @Override
    public @NotNull String translationKey() {
        return this.getTranslationKey(); // Reuse the translation key implementation
    }
}
