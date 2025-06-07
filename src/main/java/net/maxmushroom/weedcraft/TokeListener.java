package net.maxmushroom.weedcraft;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.maxmushroom.weedcraft.high.HighManager;
import net.maxmushroom.weedcraft.high.HighTask;

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
        // if player isn't holding anything
        // prevents null comparison
        if (event.getItem() == null) {
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
        
        event.getPlayer().sendMessage(Component.text("You take a drag...").color(NamedTextColor.GREEN));
        spawnSmoke(event, 0.125, 2);
        plugin.highManager.addSmoker(event.getPlayer(),  HighManager.HIGHNESS_PER_JOINT);
    }

    private void spawnSmoke(PlayerEvent event, double radius, int amount) {
        Location location = event.getPlayer().getEyeLocation();
        Vector direction = location.getDirection();
        double distance = radius * 2;
        event.getPlayer().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, location.add(direction.multiply(distance)), amount, radius, radius, radius, 0.0025);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (plugin.highManager.getHighLevel(event.getPlayer()) > HighTask.PARALYSIS_THRESHOLD) {
            event.setCancelled(true);
            event.getPlayer().sendActionBar(Component.text("You're so high, you can't move...?").color(NamedTextColor.WHITE));
        }
    }

    // prevent offline players from having their hightask ran
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.highManager.removeSmoker(event.getPlayer());
    }
}
