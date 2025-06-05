package net.maxmushroom.weedcraft;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class HighTask implements Runnable {
    private Player player;
    private BossBar highBar;

    // constants
    private static final int NAUSEA_THRESHOLD = 50;
    public static final int PARALYSIS_THRESHOLD = 75;
    private static final int MAX_HIGHNESS = HighManager.MAX_HIGHNESS;
    private static final int HIGHNESS_DECAY = 100 / 60; // 1/100th highness per second
    private static final int EFFECT_DURATION = -1;

    // colors
    private final Color PARALYSIS_COLOR = Color.RED;
    private final Color NAUSEA_COLOR = Color.YELLOW;
    private final Color NORMAL_COLOR = Color.GREEN;

    private final WeedCraft plugin;

    public HighTask(WeedCraft plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        highBar = BossBar.bossBar(Component.text("Highness").color(NamedTextColor.GREEN), 1, Color.GREEN, BossBar.Overlay.PROGRESS);
        highBar.progress(getProgress());
        player.showBossBar(highBar);
    }

    @Override
    public void run() {

        // if player is no longer high
        if (plugin.highManager.getHighLevel(player) <= 0) {
            // remove effects
            player.removePotionEffect(PotionEffectType.SLOWNESS);
            player.removePotionEffect(PotionEffectType.NAUSEA);
            player.hideBossBar(highBar);
            // remove them from the high manager (cancel task)
            plugin.highManager.removeSmoker(player);
            return;
        }

        if (getHighness() >= PARALYSIS_THRESHOLD) {
            highBar.color(PARALYSIS_COLOR);
        } else if (getHighness() >= NAUSEA_THRESHOLD) {
            highBar.color(NAUSEA_COLOR);
        } else {
            highBar.color(NORMAL_COLOR);
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, EFFECT_DURATION, (int) Math.ceil(getProgress() * 3), true, false, true));
        
        if (getHighness() >= NAUSEA_THRESHOLD) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, EFFECT_DURATION, (int) Math.ceil(getProgress() * 3 - 0.5), true, false, true));
        } else if (player.hasPotionEffect(PotionEffectType.NAUSEA)) { // If below threshold and has Nausea, remove it
            player.removePotionEffect(PotionEffectType.NAUSEA);
        }

        highBar.progress(getProgress());
        highBar.name(Component.text("Highness: " + getHighness() + "%").color(NamedTextColor.GREEN));

        plugin.highManager.setHighLevel(player, getHighness() - HIGHNESS_DECAY);
    }

    private float getProgress() {
        return (float) plugin.highManager.getHighLevel(player) / MAX_HIGHNESS;
    }

    private int getHighness() {
        return plugin.highManager.getHighLevel(player);
    }
}
