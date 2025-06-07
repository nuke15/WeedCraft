package net.maxmushroom.weedcraft;

import org.bukkit.plugin.java.JavaPlugin;

import net.maxmushroom.weedcraft.high.HighManager;
import net.maxmushroom.weedcraft.items.Items;
import net.maxmushroom.weedcraft.items.Recipes;

public final class WeedCraft extends JavaPlugin {
    public Items items;
    public Recipes recipes;
    public HighManager highManager;

    @Override
    public void onEnable() {
        items =  new Items();
        recipes = new Recipes(this);
        highManager = new HighManager(this);

        getServer().getPluginManager().registerEvents(new TokeListener(this), this);
    }

    @Override
    public void onDisable() {
        highManager.removeAllSmokers();
        recipes.unregister();
    }
    
}