package me.chickenstyle.crafts.animation;

import me.chickenstyle.crafts.Main;
import me.chickenstyle.crafts.Recipe;
import me.chickenstyle.crafts.Sounds;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ExplosionAnimation {
    public ExplosionAnimation(Recipe recipe, Location loc, Player player) {
        FileConfiguration config = Main.getInstance().getConfig();
        loc.getWorld().spawnParticle(Particle.valueOf(config.getString("animations.EXPLOSION.particle").toUpperCase()), loc, 6);
        Sounds.valueOf(config.getString("animations.EXPLOSION.sound")).play(player, 0.7f, 1f);
        loc.getWorld().dropItemNaturally(loc, recipe.getResult());

    }
}
