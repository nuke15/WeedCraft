package net.maxmushroom.weedcraft;

import org.bukkit.plugin.java.JavaPlugin;

public final class WeedCraft extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(getPluginMeta().getName() + " version " + getPluginMeta().getVersion() + " has been enabled!");
        // Example: Register commands, listeners, etc.
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(getPluginMeta().getName() + " has been disabled.");
    }
}