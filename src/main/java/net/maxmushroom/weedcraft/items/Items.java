package net.maxmushroom.weedcraft.items;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionType;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Items {
    public final ItemStack weed;
    public final ItemStack joint;
    public final ItemStack bong;
    public final ItemStack concentrate;
    public final ItemStack vape;
    public final ItemStack dabRig;
    public final List<ItemStack> items;

    public Items() {
        // initialize weed item
        weed = new ItemStack(Material.GREEN_DYE);
        ItemMeta weedMeta = weed.getItemMeta();
        weedMeta.displayName(Component.text("Weed").color(NamedTextColor.GREEN));
        weedMeta.lore(List.of(Component.text("That good stuff.").color(NamedTextColor.DARK_GREEN)));
        weedMeta.setEnchantmentGlintOverride(true);
        weed.setItemMeta(weedMeta);

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

        // initialize bong item
        bong = new ItemStack(Material.POTION);
        PotionMeta bongMeta = (PotionMeta) bong.getItemMeta();
        bongMeta.setBasePotionType(PotionType.WATER);
        bongMeta.displayName(Component.text("Bong").color(NamedTextColor.GREEN));
        bongMeta.lore(List.of(
            Component.text("Water filtered").color(NamedTextColor.DARK_GREEN),
            Component.text("smoking apparatus.").color(NamedTextColor.DARK_GREEN)
        ));
        bongMeta.setEnchantmentGlintOverride(true);
        bongMeta.setMaxStackSize(1);
        bong.setItemMeta(bongMeta);

        // initialize concentrate item
        concentrate = new ItemStack(Material.RESIN_CLUMP);
        ItemMeta concentrateMeta = concentrate.getItemMeta();
        concentrateMeta.displayName(Component.text("THC Concentrate").color(NamedTextColor.GREEN));
        concentrateMeta.lore(List.of(
            Component.text("Freshly extracted").color(NamedTextColor.DARK_GREEN),
            Component.text("rosin.").color(NamedTextColor.DARK_GREEN)
        ));
        concentrateMeta.setEnchantmentGlintOverride(true);
        concentrate.setItemMeta(concentrateMeta);

        // initiliaze vape item
        vape = new ItemStack(Material.SOUL_TORCH);
        ItemMeta vapeMeta = vape.getItemMeta();
        vapeMeta.displayName(Component.text("THC Vape").color(NamedTextColor.GREEN));
        vapeMeta.lore(List.of(
            Component.text("Handheld extract").color(NamedTextColor.DARK_GREEN),
            Component.text("vaporzier.").color(NamedTextColor.DARK_GREEN)
        ));
        vapeMeta.setEnchantmentGlintOverride(true);
        vapeMeta.setMaxStackSize(1);
        vape.setItemMeta(vapeMeta);

        // initialize dab rig item
        dabRig = new ItemStack(Material.BREWING_STAND);
        ItemMeta dabRigMeta = dabRig.getItemMeta();
        dabRigMeta.displayName(Component.text("Dab Rig").color(NamedTextColor.GREEN));
        dabRigMeta.lore(List.of(
            Component.text("Exquisitely crafted").color(NamedTextColor.DARK_GREEN),
            Component.text("concentrate smoking rig.").color(NamedTextColor.DARK_GREEN)
        ));
        dabRigMeta.setEnchantmentGlintOverride(true);
        dabRig.setItemMeta(dabRigMeta);

        items = List.of(weed, joint, bong, concentrate, vape, dabRig);
    }


}
