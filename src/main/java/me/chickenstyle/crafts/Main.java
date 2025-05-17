package me.chickenstyle.crafts;

import com.epicnicity322.epicpluginlib.bukkit.reflection.ReflectionUtil;
import com.epicnicity322.epicpluginlib.core.EpicPluginLib;
import me.chickenstyle.crafts.configs.AltarCrafts;
import me.chickenstyle.crafts.events.ArmorStandInteractEvent;
import me.chickenstyle.crafts.events.InteractEvent;
import me.chickenstyle.crafts.events.InventoryEvents;
import me.chickenstyle.crafts.events.SendMessageEvent;
import me.chickenstyle.crafts.versions.NonPesistentDataContainerIDHandler;
import me.chickenstyle.crafts.versions.PersitentDataContainerIDHandler;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static HashMap<UUID, Recipe> creatingRecipe;
    public static HashMap<UUID, Integer> animationNumber;
    public static Set<UUID> typeID;
    public static Set<UUID> opening;
    private static IDHandler versionHandler;
    private static Main instance;

    public static IDHandler getNMSHandler() {
        return versionHandler;
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        //Load Stuff
        instance = this;
        creatingRecipe = new HashMap<UUID, Recipe>();
        animationNumber = new HashMap<UUID, Integer>();
        typeID = new HashSet<UUID>();
        opening = new HashSet<UUID>();

        //Load configs
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
        new AltarCrafts(this);
        saveResource("lang.yml", false);

        //Check server version
        if (!setIDHandler()) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "BetterCrafts >>> Plugin will be disabled!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        loadListeners();

        //Load commands
        getCommand("bettercrafts").setExecutor(new BetterCraftsCommand());
        getCommand("bettercrafts").setTabCompleter(new CraftsTab());
        getCommand("recipes").setExecutor(new RecipesCommand());

        //Getting data
        int pluginId = 8954;
        Metrics metrics = new Metrics(this, pluginId);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));

        System.out.println(Utils.color("&aBetterCraft has been enabled!"));
    }

    public boolean setIDHandler() {
        try {
            if (ReflectionUtil.getClass("org.bukkit.persistence.PersistentDataContainer") != null) {
                versionHandler = new PersitentDataContainerIDHandler(this);
            } else {
                versionHandler = new NonPesistentDataContainerIDHandler();
                getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "BetterCrafts >>> NMS Version Detected: " + EpicPluginLib.Platform.getVersion());
            }
            return true;
        } catch (Throwable t) {
            t.printStackTrace();
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "BetterCrafts >>> This version isn't supported!");
            return false;
        }
    }

    public void loadListeners() {
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
        getServer().getPluginManager().registerEvents(new SendMessageEvent(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);
        getServer().getPluginManager().registerEvents(new ArmorStandInteractEvent(), this);
    }

}
