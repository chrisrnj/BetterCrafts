package me.chickenstyle.crafts;

import me.chickenstyle.crafts.configs.AltarCrafts;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CraftsTab implements TabCompleter {

    List<String> arguments = new ArrayList<String>();


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (arguments.isEmpty()) {
            arguments.add("addrecipe");
            arguments.add("reload");
            arguments.add("giveitem");
            arguments.add("help");
        }

        List<String> result = new ArrayList<String>();
        if (args.length == 1) {
            for (String str : arguments) {
                result.add(str);
            }
        }

        if (args.length == 2 && args[0].toLowerCase().equals("giveitem")) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                result.add(player.getName());
            }
        }

        if (args.length == 3 && args[0].toLowerCase().equals("giveitem")) {
            for (Recipe recipe : AltarCrafts.getRecipes()) {
                result.add(recipe.getId() + "");
            }
        }


        return result;


    }

}
