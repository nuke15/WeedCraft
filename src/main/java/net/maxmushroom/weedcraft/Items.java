package net.maxmushroom.weedcraft;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Items {
    public final ItemStack weed;
    public final ItemStack joint;

    private final NamespacedKey weedKey;
    private final NamespacedKey weedKeyLarge;
    private final NamespacedKey jointKey;

    public Items(WeedCraft plugin) {
        weedKey = new NamespacedKey(plugin, "weed_recipe");
        weedKeyLarge = new NamespacedKey(plugin, "weed_recipe_large");
        jointKey = new NamespacedKey(plugin, "joint_recipe");


        // initialize weed item
        weed = new ItemStack(Material.GREEN_DYE);
        ItemMeta weedMeta = weed.getItemMeta();
        weedMeta.displayName(Component.text("Weed").color(NamedTextColor.GREEN));
        weedMeta.lore(List.of(Component.text("That good stuff.").color(NamedTextColor.DARK_GREEN)));
        weedMeta.setEnchantmentGlintOverride(true);
        weed.setItemMeta(weedMeta);

        // recipe for weed
        ShapelessRecipe weedRecipe = new ShapelessRecipe(weedKey, weed.asQuantity(4));
        weedRecipe.addIngredient(Material.FERN);
        Bukkit.addRecipe(weedRecipe);

        // recipe for large fern
        ShapelessRecipe weedRecipeLarge = new ShapelessRecipe(weedKeyLarge, weed.asQuantity(16));
        weedRecipeLarge.addIngredient(Material.LARGE_FERN);
        Bukkit.addRecipe(weedRecipeLarge);


        // initialize joint item
        joint = new ItemStack(Material.TORCH);
        ItemMeta jointMeta = joint.getItemMeta();
        jointMeta.displayName(Component.text("Joint").color(NamedTextColor.GREEN));
        jointMeta.lore(List.of(
            Component.text("Freshly rolled and").color(NamedTextColor.DARK_GREEN),
            Component.text("ready to smoke.").color(NamedTextColor.DARK_GREEN)
        ));
        jointMeta.setEnchantmentGlintOverride(true);
        joint.setItemMeta(jointMeta);

        // recipe for joint
        ShapelessRecipe jointRecipe = new ShapelessRecipe(jointKey, joint);
        jointRecipe.addIngredient(weed);
        jointRecipe.addIngredient(Material.PAPER);
        Bukkit.addRecipe(jointRecipe);
    }

    public void unregister() {
        Bukkit.removeRecipe(weedKey);
        Bukkit.removeRecipe(weedKeyLarge);
        Bukkit.removeRecipe(jointKey);
    }
}
