package me.cipher.cardwars.cards;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.uitls.SlotGetters;
import me.cipher.cardwars.uitls.States;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class
Gold implements Listener {
    private final CardWars plugin;

    public Gold(CardWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAction(PlayerInteractAtEntityEvent e){

        States s = new States(plugin);
        SlotGetters g = new SlotGetters(plugin);
        Entity en = e.getRightClicked();
        Player p = e.getPlayer();

        if(/*!s.isInGame(p) || !s.activeTurn(p) || */!(en instanceof ItemFrame)) return;

        ItemFrame iframe = (ItemFrame) en;
        ArrayList<String> l = (ArrayList<String>) iframe.getItem().getItemMeta().getLore();

        if(iframe.getItem().getType() != Material.FILLED_MAP || !l.contains("Gold")) return;

        MapMeta mm = (MapMeta) iframe.getItem().getItemMeta();

        Bukkit.broadcastMessage("Slot location is at " + iframe.getLocation().getX() +" "+ iframe.getLocation().getY() +" "+iframe.getLocation().getZ());

        addGold(p, Integer.parseInt(mm.getDisplayName()));
        g.clearDeckSlot(iframe.getLocation());
        iframe.remove();
        e.setCancelled(true);

    }
    public void addGold(Player p, int i){

        String team = new SlotGetters(plugin).getPlayerTeam(p);
        int newg = 0;

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"scoreboard players add "+p.getName()+" Gold "+i);

        if(plugin.getConfig().contains("Teams."+team+".Gold")){

            newg = plugin.getConfig().getInt("Teams."+team+".Gold");
        }

        plugin.getConfig().set("Teams."+team+".Gold",newg + i);
        plugin.saveConfig();
    }
}
