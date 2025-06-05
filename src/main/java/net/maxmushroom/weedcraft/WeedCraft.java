package net.maxmushroom.weedcraft;

import org.bukkit.plugin.java.JavaPlugin;

public final class WeedCraft extends JavaPlugin {
    public final Items items = new Items(this);
    public final HighManager highManager = new HighManager(this);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TokeListener(this), this);
    }

    @Override
    public void onDisable() {
        highManager.removeAllSmokers();
        items.unRegister();
    }
    
}