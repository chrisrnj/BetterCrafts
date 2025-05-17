package me.chickenstyle.crafts.versions;

import me.chickenstyle.crafts.IDHandler;
import me.chickenstyle.crafts.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class PersitentDataContainerIDHandler implements IDHandler {

    private final @NotNull NamespacedKey idKey;

    public PersitentDataContainerIDHandler(@NotNull Main plugin) {
        idKey = new NamespacedKey(plugin, "IDTag");
    }

    @Override
    public @NotNull ItemStack addIDTag(@NotNull ItemStack item, int id) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(idKey, PersistentDataType.INTEGER, id);
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public boolean hasIDTag(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            return meta.getPersistentDataContainer().has(idKey);
        }
        return false;
    }

    @Override
    public int getID(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            return meta.getPersistentDataContainer().getOrDefault(idKey, PersistentDataType.INTEGER, 0);
        }
        return 0;
    }
}
