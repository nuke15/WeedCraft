package net.maxmushroom.weedcraft;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class TokeListener implements Listener {
    WeedCraft plugin;

    public TokeListener(WeedCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onUseJoint(PlayerInteractEvent event) {

        // quick exits:
        // if player is not right clicking
        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR) && !event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        // if player isn't holding joint
        if (!event.getItem().isSimilar(plugin.items.joint)) {
            return;
        }

        //cancel event
        event.setCancelled(true);
        // remove joint
        if (event.getPlayer().getGameMode() == GameMode.ADVENTURE || event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            event.getItem().setAmount(event.getItem().getAmount() - 1);
        }
        // spawn smoke
        Location location = event.getPlayer().getEyeLocation();
        Vector direction = location.getDirection();
        double radius = 0.0125;
        event.getPlayer().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location.add(direction.multiply(0.5)), 5, radius, radius, radius, 0.0125);
        plugin.highManager.addSmoker(event.getPlayer());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (plugin.highManager.getHighLevel(event.getPlayer()) > HighTask.PARALYSIS_THRESHOLD) {
            event.setCancelled(true);
            event.getPlayer().sendActionBar(Component.text("You're so high, you can't move...?").color(NamedTextColor.WHITE));
        }
    }
}
