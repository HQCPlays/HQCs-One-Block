package org.hqcplays.hqcsoneblock.items;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class CustomItemMasterList {
    
    private static ArrayList<ItemStack> customWeapons = new ArrayList<>();
    private static ArrayList<ItemStack> customArmors = new ArrayList<>();
    private static ArrayList<ItemStack> customAccessories = new ArrayList<>();
    private static ArrayList<ItemStack> customTools = new ArrayList<>();
    private static ArrayList<ItemStack> customWands = new ArrayList<>();
    private static ArrayList<ItemStack> customAutomatons = new ArrayList<>();


    // WEAPON GETTER SETTERS
    public static ArrayList<ItemStack> getCustomWeapons() {
        return customWeapons;
    }
    public static void setCustomWeapons(ArrayList<ItemStack> customWeaponsList){
        customWeapons = customWeaponsList;
    }
    public static void clearCustomWeapons(){
        customWeapons.clear();
    }
    public static void addCustomWeapon(ItemStack weapon){
        customWeapons.add(weapon);
    }
    public static void removeCustomWeapon(ItemStack weapon){
        customWeapons.remove(weapon);
    }
    
    // ARMOR GETTER SETTERS
    public static ArrayList<ItemStack> getCustomArmors() {
        return customArmors;
    }
    public static void setCustomArmors(ArrayList<ItemStack> customArmorsList){
        customArmors = customArmorsList;
    }
    public static void clearCustomArmors(){
        customArmors.clear();
    }
    public static void addCustomArmor(ItemStack armor){
        customArmors.add(armor);
    }
    public static void removeCustomArmor(ItemStack armor){
        customArmors.remove(armor);
    }

    // ACCESSORY GETTER SETTERS
    public static ArrayList<ItemStack> getCustomAccessories() {
        return customAccessories;
    }
    public static void setCustomAccessories(ArrayList<ItemStack> customAccessoriesList){
        customAccessories = customAccessoriesList;
    }
    public static void clearCustomAccessories(){
        customAccessories.clear();
    }
    public static void addCustomAccessory(ItemStack accessory){
        customAccessories.add(accessory);
    }
    public static void removeCustomAccessory(ItemStack accessory){
        customAccessories.remove(accessory);
    }

    // TOOL GETTER SETTERS
    public static ArrayList<ItemStack> getCustomTools() {
        return customTools;
    }
    public static void setCustomTools(ArrayList<ItemStack> customToolsList){
        customTools = customToolsList;
    }
    public static void clearCustomTools(){
        customTools.clear();
    }
    public static void addCustomTool(ItemStack tool){
        customTools.add(tool);
    }
    public static void removeCustomTool(ItemStack tool){
        customTools.remove(tool);
    }

    // WAND GETTER SETTERS
    public static ArrayList<ItemStack> getCustomWands() {
        return customWands;
    }
    public static void setCustomWands(ArrayList<ItemStack> customWandsList){
        customWands = customWandsList;
    }
    public static void clearCustomWands(){
        customWands.clear();
    }
    public static void addCustomWand(ItemStack wand){
        customWands.add(wand);
    }
    public static void removeCustomWand(ItemStack wand){
        customWands.remove(wand);
    }

    // AUTOMATON GETTER SETTERS
    public static ArrayList<ItemStack> getCustomAutomatons() {
        return customAutomatons;
    }
    public static void setCustomAutomatons(ArrayList<ItemStack> customAutomatonsList){
        customAutomatons = customAutomatonsList;
    }
    public static void clearCustomAutomatons(){
        customAutomatons.clear();
    }
    public static void addCustomAutomaton(ItemStack automaton){
        customAutomatons.add(automaton);
    }
    public static void removeCustomAutomaton(ItemStack automaton){
        customAutomatons.remove(automaton);
    }
}
