package net.maxmushroom.weedcraft.items;

import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import net.maxmushroom.weedcraft.WeedCraft;

public class RecipeBlocker implements Listener {
    WeedCraft plugin;

    public RecipeBlocker(WeedCraft plugin) {
        this.plugin = plugin;
    }

    // block custom items from being used in non-plugin recipes
    // (eg. dying wool with weed, crafting a lantern with a joint)
    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        boolean containsCustomItem = false;
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item == null) {
                continue;
            }

            for (ItemStack customItem : plugin.items.items) {
                if (item.isSimilar(customItem)) {
                    containsCustomItem = true;
                    break;
                }
            }
        }

        // check if recipe is from our namespace
        if (containsCustomItem) {
            if (event.getRecipe() instanceof Keyed) {
                Keyed recipe = (Keyed) event.getRecipe();
                if (!recipe.getKey().getNamespace().equals("weedcraft")) {
                    // not in our namespace, blockedt!
                    event.getInventory().setResult(new ItemStack(Material.AIR));
                }
            } else {
                // it's not keyed, so not ours
                event.getInventory().setResult(new ItemStack(Material.AIR));
            }
        }
    }

    // block bongs from being placed in a brewing stand
    @EventHandler
    public void onBrewingStandClick(InventoryClickEvent event) {
        if (event.getView().getType() != InventoryType.BREWING) {
            return;
        }

        InventoryAction action = event.getAction();

        // handle direct placement into brewing stand output slots
        if ((action == InventoryAction.PLACE_ALL || action == InventoryAction.PLACE_ONE
                || action == InventoryAction.PLACE_SOME)
                && event.getClickedInventory().getType() == InventoryType.BREWING) {
            ItemStack currentItem = event.getCursor();
            if (currentItem != null && currentItem.isSimilar(plugin.items.bong)) {
                event.setCancelled(true);
            }
        }

        // handle indirect placement (shift-clicking)
        if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem.isSimilar(plugin.items.bong)
                    && event.getClickedInventory().getType() == InventoryType.PLAYER) {
                event.setCancelled(true);
            }
        }

    }
}
