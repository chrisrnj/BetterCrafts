package me.chickenstyle.crafts.holders;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class NoClickHolder implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        return null;
    }
}
