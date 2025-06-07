package net.maxmushroom.weedcraft.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.RecipeChoice.ExactChoice;

import net.maxmushroom.weedcraft.WeedCraft;

public class Recipes {

    private static final float CONCENTRATE_EXP = 5f;
    private final NamespacedKey weedKey;
    private final NamespacedKey jointKey;
    private final NamespacedKey bongKey;
    private final NamespacedKey bongPackedKey;
    private final NamespacedKey concentrateKey;
    private final NamespacedKey vapeKey;
    private final NamespacedKey vapeFilledKey;
    private final NamespacedKey dabRigKey;
    private final NamespacedKey dabRigLoadedKey;

    public Recipes(WeedCraft plugin) {
        weedKey = new NamespacedKey(plugin, "weed_recipe");
        jointKey = new NamespacedKey(plugin, "joint_recipe");
        bongKey = new NamespacedKey(plugin, "bong_recipe");
        bongPackedKey = new NamespacedKey(plugin, "bong_packed_recipe");
        concentrateKey = new NamespacedKey(plugin, "concentrate_recipe");
        vapeKey = new NamespacedKey(plugin, "vape_recipe");
        vapeFilledKey = new NamespacedKey(plugin, "vape_filled_recipe");
        dabRigKey = new NamespacedKey(plugin, "dab_rig_recipe");
        dabRigLoadedKey = new NamespacedKey(plugin, "dab_rig_loaded_recipe");

        register(plugin);
    }

    public void register(WeedCraft plugin) {
        // recipe for weed
        ShapelessRecipe weedRecipe = new ShapelessRecipe(weedKey, plugin.items.weed.asQuantity(4));
        weedRecipe.addIngredient(Material.FERN);
        Bukkit.addRecipe(weedRecipe);

        // recipe for joint
        ShapelessRecipe jointRecipe = new ShapelessRecipe(jointKey, plugin.items.joint);
        jointRecipe.addIngredient(plugin.items.weed);
        jointRecipe.addIngredient(Material.PAPER);
        Bukkit.addRecipe(jointRecipe);

        // recipe for bong
        ShapedRecipe bongRecipe = new ShapedRecipe(bongKey, plugin.items.bong);
        bongRecipe.shape("GGG", " G ");
        bongRecipe.setIngredient('G', Material.GLASS);
        Bukkit.addRecipe(bongRecipe);

        // recipe for packed bong
        ShapelessRecipe bongPackedRecipe = new ShapelessRecipe(bongPackedKey, plugin.items.bongPacked);
        bongPackedRecipe.addIngredient(plugin.items.bong);
        bongPackedRecipe.addIngredient(plugin.items.weed);
        Bukkit.addRecipe(bongPackedRecipe);

        // recipe for concentrate
        FurnaceRecipe concentrateRecipe = new FurnaceRecipe(
                concentrateKey,
                plugin.items.concentrate.asQuantity(2),
                new ExactChoice(plugin.items.weed),
                CONCENTRATE_EXP,
                20 * 5);
        Bukkit.addRecipe(concentrateRecipe);

        // recipe for vape
        ShapedRecipe vapeRecipe = new ShapedRecipe(vapeKey, plugin.items.vape);
        vapeRecipe.shape(" G ", "TIT", " T ");
        vapeRecipe.setIngredient('G', Material.GLASS);
        vapeRecipe.setIngredient('T', Material.REDSTONE_TORCH);
        vapeRecipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(vapeRecipe);

        // recipe for filled vape
        ShapelessRecipe vapeFilledRecipe = new ShapelessRecipe(vapeFilledKey, plugin.items.vapeFilled);
        vapeFilledRecipe.addIngredient(plugin.items.concentrate);
        vapeFilledRecipe.addIngredient(plugin.items.weed);
        Bukkit.addRecipe(vapeFilledRecipe);

        // recipe for dab rig
        ShapedRecipe dabRigRecipe = new ShapedRecipe(dabRigKey, plugin.items.dabRig);
        dabRigRecipe.shape("GB");
        dabRigRecipe.setIngredient('G', Material.GLASS);
        dabRigRecipe.setIngredient('B', plugin.items.bong);
        Bukkit.addRecipe(dabRigRecipe);

        // recipe for loaded dab rig
        ShapelessRecipe dabRigLoadedRecipe = new ShapelessRecipe(dabRigLoadedKey, plugin.items.dabRigLoaded);
        dabRigLoadedRecipe.addIngredient(plugin.items.dabRig);
        dabRigLoadedRecipe.addIngredient(plugin.items.concentrate);
        Bukkit.addRecipe(dabRigLoadedRecipe);

    }

    public void unregister() {
        Bukkit.removeRecipe(weedKey);
        Bukkit.removeRecipe(jointKey);
        Bukkit.removeRecipe(bongKey);
        Bukkit.removeRecipe(bongPackedKey);
        Bukkit.removeRecipe(concentrateKey);
        Bukkit.removeRecipe(vapeKey);
        Bukkit.removeRecipe(vapeFilledKey);
        Bukkit.removeRecipe(dabRigKey);
        Bukkit.removeRecipe(dabRigLoadedKey);
    }
}
