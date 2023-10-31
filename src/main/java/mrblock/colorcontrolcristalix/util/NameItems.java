package mrblock.colorcontrolcristalix.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class NameItems {
    public static ItemStack createItem(Material type, int amount, String displayName) {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setUnbreakable(true);
        item.setItemMeta(itemMeta);
        return item;
    }
}
