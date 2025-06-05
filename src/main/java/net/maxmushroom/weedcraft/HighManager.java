package net.maxmushroom.weedcraft;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class HighManager {
    // hashmap of high players and their high tasks
    // hashmap of high players and how high they are
    private final Map<Player, BukkitTask> highTasks = new ConcurrentHashMap<>();
    private final Map<Player, Integer> highLevels = new ConcurrentHashMap<>();

    // constants
    public static final int HIGHNESS_PER_JOINT = 25;
    public static final int MAX_HIGHNESS = 100;

    private final WeedCraft plugin;

    public HighManager(WeedCraft plugin) {
        this.plugin = plugin;
    }

    public void addSmoker(Player player) {
        // if player is not high,
        if (highLevels.get(player) == null) {
            highLevels.put(player, HIGHNESS_PER_JOINT);
            highTasks.put(player, plugin.getServer().getScheduler().runTaskTimer(plugin, new HighTask(plugin, player), 0, 20));
        } else {
            highLevels.put(player, highLevels.get(player) + HIGHNESS_PER_JOINT);
        }

        if (highLevels.get(player) > MAX_HIGHNESS) {
            highLevels.put(player, MAX_HIGHNESS);
        }
    }

    public void removeSmoker(Player player) {
        highTasks.get(player).cancel();
        highTasks.remove(player);
        highLevels.remove(player);
    }

    public void removeAllSmokers() {
        highTasks.keySet().forEach((player) -> {
            removeSmoker(player);
        });
        highTasks.clear();
        highLevels.clear();
    }

    public int getHighLevel(Player player) {
        return highLevels.getOrDefault(player, 0);
    }

    public void setHighLevel(Player player, int level) {
        highLevels.put(player, level);
    }
}
