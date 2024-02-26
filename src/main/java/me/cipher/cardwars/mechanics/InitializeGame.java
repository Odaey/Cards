package me.cipher.cardwars.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import java.util.ArrayList;

public class InitializeGame {

    public void summonDeck(ArrayList<MapMeta> l){

        Location loc1 = new Location(Bukkit.getWorld("samer3abyt"),0,-59,0);
        Location loc2 = new Location(Bukkit.getWorld("samer3abyt"),0,-60,0);

        ShulkerBox box1 = (ShulkerBox) loc1.getBlock().getState();
        ShulkerBox box2 = (ShulkerBox) loc2.getBlock().getState();

        for(MapMeta m : l){

            ItemStack map = new ItemStack(Material.FILLED_MAP);
            m.setMapId(m.getMapId());
            map.setItemMeta(m);

            Bukkit.broadcastMessage("Map Id is "+m.getMapId());

            if(hasSpaceFor(map,box1)){

                box1.getInventory().addItem(map);

            }else {

                box2.getInventory().addItem(map);
            }
        }
    }
    public boolean hasSpaceFor(ItemStack i, ShulkerBox b) {
        Inventory inv = b.getInventory();
        int maxStack = i.getMaxStackSize();

        for (ItemStack slotItem : inv.getContents()) {
            if (slotItem == null || slotItem.getType() == Material.AIR) {
                return true;
            }
        }
        for (ItemStack slotItem : inv.getContents()) {
            if (slotItem != null && slotItem.isSimilar(i)) {
                int space = maxStack - slotItem.getAmount();
                if (space >= i.getAmount()) {
                    return true;
                }
            }
        }
        return false;
    }
}
