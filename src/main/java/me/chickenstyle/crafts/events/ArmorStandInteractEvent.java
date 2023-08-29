package me.chickenstyle.crafts.events;

import me.chickenstyle.crafts.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class ArmorStandInteractEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerArmorStandManipulateEvent e) {
        if (Main.opening.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }
}
