package me.cipher.cardwars.cards;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.mechanics.Turns;
import me.cipher.cardwars.uitls.Slot;
import me.cipher.cardwars.uitls.SlotGetters;
import me.cipher.cardwars.uitls.States;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import java.util.ArrayList;

import static me.cipher.cardwars.cards.Cards.Set.getSetID;

public class Properties implements Listener {
    private final CardWars plugin;
    private final States state;
    private final Turns turns;
    private final Cards cards;

    public Properties(CardWars plugin) {
        this.plugin = plugin;
        this.state = new States(plugin);
        this.turns = new Turns(plugin);
        this.cards = new Cards();
    }
    @EventHandler
    public void onUse(PlayerInteractAtEntityEvent e ){

        States s = new States(plugin);
        SlotGetters g = new SlotGetters(plugin);
        Entity en = e.getRightClicked();
        Player p = e.getPlayer();

        if (!(en instanceof ItemFrame)) return;

        ItemFrame iframe = (ItemFrame) en;

        if (iframe.getItem().getType() != Material.FILLED_MAP) return;

        MapMeta mm = (MapMeta) iframe.getItem().getItemMeta();
        ArrayList<String> lore = (ArrayList<String>) mm.getLore();

        if (!lore.get(0).equalsIgnoreCase("Property Card")) return;

        Cards.Card card = Cards.Card.getCardFromId(lore.get(1));
        String team = g.getPlayerTeam(p);

        // Find the column for the card's set
        int column = g.getSetColumn(card.getSet(), team);

        if (column == -1) {
            // If the set is not found in any column, find the first empty column
            for (int i = 1; i <= 9; i++) {
                if (!plugin.getConfig().contains("Teams." + team + ".Property.Column" + i + "Set")) {
                    column = i;
                    break;
                }
            }
        }
        int columnname = column + 1;
        // Add the card to the next available row in the column
        int row = g.getAvailableRow(column, team);
        ArrayList<String> cardList = (ArrayList<String>) plugin.getConfig().getList("Teams." + team + ".Property.Column" + columnname + "Cards");

        if (cardList == null) {
            cardList = new ArrayList<>();
        }

        cardList.add(card.name());

        plugin.getConfig().set("Teams." + team + ".Property.Column" + columnname + "Cards", cardList);
        plugin.getConfig().set("Teams." + team + ".Property.Column" + columnname + "Set", getSetID(card.getSet()));
        plugin.saveConfig();

        Slot slot = new Slot(row, column);
        displayProperty(team, slot, mm);
    }
    public void displayProperty(String teamId, Slot s,MapMeta m){

        Location l = new SlotGetters(plugin).getSlotLocation(teamId,s);

        ItemFrame itemFrame = l.getWorld().spawn(l, ItemFrame.class);
        itemFrame.setFacingDirection(new SlotGetters(plugin).getPropertyDirection(teamId));

        ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
        mapItem.setItemMeta(m);
        itemFrame.setItem(mapItem);
    }
}
