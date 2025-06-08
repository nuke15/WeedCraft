package net.maxmushroom.weedcraft;

import org.bukkit.plugin.java.JavaPlugin;

import net.maxmushroom.weedcraft.high.HighManager;
import net.maxmushroom.weedcraft.high.TokeListener;
import net.maxmushroom.weedcraft.items.Items;
import net.maxmushroom.weedcraft.items.RecipeBlocker;
import net.maxmushroom.weedcraft.items.Recipes;

public final class WeedCraft extends JavaPlugin {
    public Items items;
    public Recipes recipes;
    public RecipeBlocker recipeBlocker;
    public HighManager highManager;

    @Override
    public void onEnable() {
        items =  new Items();
        recipes = new Recipes(this);
        highManager = new HighManager(this);

        getServer().getPluginManager().registerEvents(new TokeListener(this), this);
        getServer().getPluginManager().registerEvents(new RecipeBlocker(this), this);
    }

    @Override
    public void onDisable() {
        highManager.removeAllSmokers();
        recipes.unregister();
    }
    
}