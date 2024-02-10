package me.cipher.cardwars.cards;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.mechanics.Turns;
import me.cipher.cardwars.stations.BlueTeam;
import me.cipher.cardwars.stations.GreenTeam;
import me.cipher.cardwars.stations.RedTeam;
import me.cipher.cardwars.stations.YellowTeam;
import me.cipher.cardwars.uitls.Slot;
import me.cipher.cardwars.uitls.SlotGetters;
import me.cipher.cardwars.uitls.States;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

import java.io.File;

import static org.bukkit.entity.EntityType.ITEM_FRAME;

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

    // Event handler for player interaction
    @EventHandler
    public void addProperty(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        // Check game state, player turns, and item in hand
        if (!state.isInGame(p) || !turns.hasTurns(p) || e.getPlayer().getInventory().getItemInMainHand().getType() != Material.FILLED_MAP) {
            return;
        }

        // Check if the held item is a valid card
        if (!cards.isCard(e.getPlayer().getInventory().getItemInMainHand())) {
            return;
        }

        // Get card information
        MapMeta m = (MapMeta) e.getPlayer().getInventory().getItemInMainHand().getItemMeta();
        Cards.Card card = cards.getCard(m);
        String team = turns.getTeam();

        // Get direction, available slot, and location for property display
        BlockFace direction = new SlotGetters(plugin).getDirection(team);
        Slot s = new SlotGetters(plugin).getAvailableSlot(card, new SlotGetters(plugin).getTeamGroups(team), team);
        Location l = new SlotGetters(plugin).slotLocation(team, s);

        // Display property and play a note
        displayProperty(l, direction, m.getMapView());
        p.playNote(p.getLocation(), Instrument.CHIME, Note.flat(9, Note.Tone.B));
    }

    // Display property at the specified location with the given direction and map view
    private static void displayProperty(Location location, BlockFace direction, MapView mapView) {
        ItemFrame itemFrame = location.getWorld().spawn(location, ItemFrame.class);
        itemFrame.setFacingDirection(direction);

        ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
        MapMeta meta = (MapMeta) mapItem.getItemMeta();

        meta.setMapId(mapView.getId());

        mapItem.setItemMeta(meta);
        itemFrame.setItem(mapItem);

    }
}
