package net.maxmushroom.weedcraft.high;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.maxmushroom.weedcraft.WeedCraft;

public class HighTask implements Runnable {
    private Player player;
    public BossBar highBar;

    // constants
    private static final int NAUSEA_THRESHOLD = 50;
    public static final int PARALYSIS_THRESHOLD = 75;
    private static final int MAX_HIGHNESS = HighManager.MAX_HIGHNESS;
    private static final int HIGHNESS_DECAY = 100 / 60; // 1% highness per second
    private static final int EFFECT_DURATION = 5 * 20; // 2 seconds

    // colors
    private final Color PARALYSIS_COLOR = Color.RED;
    private final Color NAUSEA_COLOR = Color.YELLOW;
    private final Color NORMAL_COLOR = Color.GREEN;

    private final TextColor PARALYSIS_TEXT_COLOR = NamedTextColor.RED;
    private final TextColor NAUSEA_TEXT_COLOR = NamedTextColor.YELLOW;
    private final TextColor NORMAL_TEXT_COLOR = NamedTextColor.GREEN;

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
        if (getHighness() <= 0) {
            plugin.highManager.removeSmoker(player);
            return;
        }

        // update boss bar
        Component name = Component.text("Highness: " + getHighness() + "%");

        if (getHighness() >= PARALYSIS_THRESHOLD) {
            highBar.color(PARALYSIS_COLOR);
            name = name.color(PARALYSIS_TEXT_COLOR);
        } else if (getHighness() >= NAUSEA_THRESHOLD) {
            highBar.color(NAUSEA_COLOR);
            name = name.color(NAUSEA_TEXT_COLOR);
        } else {
            highBar.color(NORMAL_COLOR);
            name = name.color(NORMAL_TEXT_COLOR);
        }

        highBar.progress(getProgress());
        highBar.name(name);

        // update effects
        int slownessAmplifier = (int) Math.floor(getProgress() * 3);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, EFFECT_DURATION, slownessAmplifier, true, false, true));
        int resistanceAmplifier = (int) Math.floor(getProgress() * 3);
        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, EFFECT_DURATION, resistanceAmplifier, true, false, true));
        int hungerAmplifier = (int) (Math.floor(getProgress() * 3) - 1);
        if (hungerAmplifier >= 0) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, EFFECT_DURATION, hungerAmplifier, true, false, true));
        }        
        if (getHighness() >= NAUSEA_THRESHOLD) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, EFFECT_DURATION, 0, true, false, true));
        }

        // decay high
        plugin.highManager.setHighLevel(player, getHighness() - HIGHNESS_DECAY);
    }

    private float getProgress() {
        return (float) plugin.highManager.getHighLevel(player) / MAX_HIGHNESS;
    }

    private int getHighness() {
        return plugin.highManager.getHighLevel(player);
    }
    
}
