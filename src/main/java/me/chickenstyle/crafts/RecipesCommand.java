package me.chickenstyle.crafts;

import me.chickenstyle.crafts.configs.AltarCrafts;
import me.chickenstyle.crafts.guis.RecipesGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RecipesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("BetterCrafts.Admin") || player.hasPermission("BetterCrafts.recipes")) {
                new RecipesGUI(player, AltarCrafts.getRecipes(), 1);
                return true;
            }
        }
        return false;
    }

}
