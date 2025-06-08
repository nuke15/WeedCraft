package net.maxmushroom.weedcraft.high;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.maxmushroom.weedcraft.WeedCraft;

public class TokeListener implements Listener {
    WeedCraft plugin;

    public TokeListener(WeedCraft plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        // quick exits:
        // if player is not right clicking
        if (!event.getAction().isRightClick()) {
            return;
        }
        // if player isn't holding anything
        // prevents null comparison
        if (event.getItem() == null) {
            return;
        }

        if (event.getItem().isSimilar(plugin.items.joint)) {
            // cancel event
            event.setCancelled(true);
            if (event.getPlayer().getGameMode() == GameMode.ADVENTURE
                    || event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                event.getItem().setAmount(event.getItem().getAmount() - 1);
            }

            event.getPlayer().sendMessage(Component.text("You take a drag...").color(NamedTextColor.GREEN));
            spawnSmoke(event.getPlayer(), 0.125, 4, Particle.SMOKE);
            plugin.highManager.addSmoker(event.getPlayer(), HighManager.HIGHNESS_PER_JOINT);

        } else if (event.getItem().isSimilar(plugin.items.bong)) {
            event.setCancelled(true);
            if (event.getPlayer().getGameMode() == GameMode.ADVENTURE
                    || event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                if (event.getPlayer().getInventory().containsAtLeast(plugin.items.weed, 1)) {
                    // loop through the inventory and remove 1 concentrate from the first matching
                    // itemstack
                    for (ItemStack item : event.getPlayer().getInventory().getContents()) {
                        if (item != null && item.isSimilar(plugin.items.weed)) {
                            item.setAmount(item.getAmount() - 1);
                            break;
                        }
                    }
                } else {
                    event.getPlayer()
                            .sendMessage(Component.text("You don't have any weed!").color(NamedTextColor.RED));
                    return;
                }
            }

            event.getPlayer().sendMessage(Component.text("You take a rip...").color(NamedTextColor.GREEN));
            spawnSmoke(event.getPlayer(), 0.25, 5, Particle.CAMPFIRE_COSY_SMOKE);
            plugin.highManager.addSmoker(event.getPlayer(), HighManager.HIGHNESS_PER_BOWL);

        } else if (event.getItem().isSimilar(plugin.items.vape)) {
            event.setCancelled(true);

            if (event.getPlayer().getGameMode() == GameMode.ADVENTURE
                    || event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                if (event.getPlayer().getInventory().containsAtLeast(plugin.items.concentrate, 1)) {
                    // loop through the inventory and remove 1 concentrate from the first matching
                    // itemstack
                    for (ItemStack item : event.getPlayer().getInventory().getContents()) {
                        if (item != null && item.isSimilar(plugin.items.concentrate)) {
                            item.setAmount(item.getAmount() - 1);
                            break;
                        }
                    }
                } else {
                    event.getPlayer()
                            .sendMessage(Component.text("You don't have any concentrate!").color(NamedTextColor.RED));
                    return;
                }
            }

            event.getPlayer().sendMessage(Component.text("You take a hit...").color(NamedTextColor.GREEN));
            spawnSmoke(event.getPlayer(), 0.125, 2, Particle.SMOKE);
            plugin.highManager.addSmoker(event.getPlayer(), HighManager.HIGHNESS_PER_VAPE_HIT);

        } else if (event.getItem().isSimilar(plugin.items.dabRig)) {
            event.setCancelled(true);

            if (event.getPlayer().getGameMode() == GameMode.ADVENTURE
                    || event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                if (event.getPlayer().getInventory().containsAtLeast(plugin.items.concentrate, 1)) {
                    // loop through the inventory and remove 1 concentrate from the first matching
                    // itemstack
                    for (ItemStack item : event.getPlayer().getInventory().getContents()) {
                        if (item != null && item.isSimilar(plugin.items.concentrate)) {
                            item.setAmount(item.getAmount() - 1);
                            break;
                        }
                    }
                } else {
                    event.getPlayer()
                            .sendMessage(Component.text("You don't have any concentrate!").color(NamedTextColor.RED));
                    return;
                }
            }

            event.getPlayer().sendMessage(Component.text("You take a dab...").color(NamedTextColor.GREEN));
            spawnSmoke(event.getPlayer(), 0.25, 4, Particle.CAMPFIRE_COSY_SMOKE);
            plugin.highManager.addSmoker(event.getPlayer(), HighManager.HIGHNESS_PER_DAB);
        }
    }

    private void spawnSmoke(Player player, double radius, int amount, Particle particle) {
        Location location = player.getEyeLocation();
        Vector direction = location.getDirection();
        double distance = radius * 2;
        player.getWorld().spawnParticle(particle, location.add(direction.multiply(distance)),
                amount, radius,
                radius, radius, 0.0025);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (plugin.highManager.getHighLevel(event.getPlayer()) > HighTask.PARALYSIS_THRESHOLD) {
            event.setCancelled(true);
            event.getPlayer()
                    .sendActionBar(Component.text("You're so high, you can't move...?").color(NamedTextColor.WHITE));
        }
    }

    // prevent offline players from having their hightask ran
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.highManager.removeSmoker(event.getPlayer());
    }
}
