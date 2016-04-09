package de.festival.Bedwars.Helper;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ColoredGlass {
    public ColoredGlass() {

    }

    public ItemStack getColoredGlass(byte color, String title) {
        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, color);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(title);

        pane.setItemMeta(meta);
        return pane;
    }
}
