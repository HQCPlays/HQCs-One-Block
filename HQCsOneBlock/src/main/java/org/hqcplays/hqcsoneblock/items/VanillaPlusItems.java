package org.hqcplays.hqcsoneblock.items;

import org.bukkit.inventory.ItemStack;
import org.hqcplays.hqcsoneblock.items.misc.CoalSwordItem;
import org.hqcplays.hqcsoneblock.items.misc.PlatforminatorItem;

public class VanillaPlusItems {
    // Swords
    public static CustomItem coalSword;

    // Misc.
    public static CustomItem cobblestonePlatforminator;

    public static void init() {
        coalSword = new CoalSwordItem();
        cobblestonePlatforminator = new PlatforminatorItem();
    }
}
