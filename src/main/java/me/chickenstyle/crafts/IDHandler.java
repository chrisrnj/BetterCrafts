package me.chickenstyle.crafts;

import org.bukkit.inventory.ItemStack;

public interface IDHandler {
    ItemStack addIDTag(ItemStack item, int id);

    boolean hasIDTag(ItemStack item);

    int getID(ItemStack item);
}
