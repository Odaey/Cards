package me.cipher.cardwars.uitls;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.cards.Cards;
import me.cipher.cardwars.stations.BlueTeam;
import me.cipher.cardwars.stations.GreenTeam;
import me.cipher.cardwars.stations.RedTeam;
import me.cipher.cardwars.stations.YellowTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import java.util.ArrayList;
import java.util.Objects;

import static me.cipher.cardwars.cards.Cards.Set.getSetFromId;

public class SlotGetters {
    private final CardWars plugin;

    public SlotGetters(CardWars plugin) {
        this.plugin = plugin;
    }


    public Location getSlotLocation(String teamId, Slot slot) {
        Location location = null;

        switch (teamId) {
            case "RedTeam":
                location = new RedTeam(plugin).translateCords(slot);
                break;
            case "BlueTeam":
                location = new BlueTeam(plugin).translateCords(slot);
                break;
            case "YellowTeam":
                location = new YellowTeam(plugin).translateCords(slot);
                break;
            case "GreenTeam":
                location = new GreenTeam(plugin).translateCords(slot);
                break;
        }

        return location;
    }

    public BlockFace getDeckDirection(String teamId) {
        BlockFace direction = null;

        switch (teamId) {
            case "RedTeam":
                direction = new RedTeam(plugin).getDeckDirection();
                break;
            case "BlueTeam":
                direction = new BlueTeam(plugin).getDeckDirection();
                break;
            case "YellowTeam":
                direction = new YellowTeam(plugin).getDeckDirection();
                break;
            case "GreenTeam":
                direction = new GreenTeam(plugin).getDeckDirection();
                break;
        }

        return direction;
    }
    public BlockFace getPropertyDirection(String teamId){
        BlockFace direction = null;

        switch (teamId) {
            case "RedTeam":
                direction = new RedTeam(plugin).getPropertyDirection();
                break;
            case "BlueTeam":
                direction = new BlueTeam(plugin).getPropertyDirection();
                break;
            case "YellowTeam":
                direction = new YellowTeam(plugin).getPropertyDirection();
                break;
            case "GreenTeam":
                direction = new GreenTeam(plugin).getPropertyDirection();
                break;
        }

        return direction;

    }

    public String getPlayerTeam(Player p) {
        String s = "";
        ArrayList<String> tl = new ArrayList<>();
        tl.add("RedTeam");
        tl.add("BlueTeam");
        tl.add("YellowTeam");
        tl.add("GreenTeam");

        for (String t : tl) {
            String name = plugin.getConfig().getString("Teams." + t + ".Player");

            if (name != null && name.equalsIgnoreCase(p.getName())) {
                s = t;
                break;
            }
        }

        return s;
    }

    public void setGold(Player p, int i) {
        String id = getPlayerTeam(p);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                "scoreboard players set " + p.getName() + " Gold " + i);

        plugin.getConfig().set("Teams." + id + ".Gold", i);
        plugin.saveConfig();
    }

    public void addDeckCard(MapMeta m, String s) {
        int slot = getNextDeckSlot(s);

        Location l = getLocationForDeckSlot(new Location(Bukkit.getWorld(""), 0, 0, 0), s, slot);

        ItemFrame itemFrame = l.getWorld().spawn(l, ItemFrame.class);
        itemFrame.setFacingDirection(getDeckDirection(s));

        ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
        mapItem.setItemMeta(m);
        itemFrame.setItem(mapItem);

        String slotN = "Slot" + slot;
        plugin.getConfig().set("Teams." + s + ".Deck." + slotN, m.getLore().get(1));
        plugin.saveConfig();
    }

    private Location getLocationForDeckSlot(Location baseLocation, String teamId, int slot) {
        switch (teamId) {
            case "RedTeam":
                return new RedTeam(plugin).getDeckSlot(slot - 1);
            case "BlueTeam":
                return new BlueTeam(plugin).getDeckSlot(slot - 1);
            case "YellowTeam":
                return new YellowTeam(plugin).getDeckSlot(slot - 1);
            case "GreenTeam":
                return new GreenTeam(plugin).getDeckSlot(slot - 1);
            default:
                return baseLocation; // Return base location for unknown team
        }
    }
    public int getNextDeckSlot(String teamId) {
        int maxSlots = 9;

        for (int slot = 1; slot <= maxSlots; slot++) {
            String slotPath = "Teams." + teamId + ".Deck.Slot" + slot;

            if (!isEmptySlot(slotPath)) {
                continue;
            }

            return slot;
        }
        Bukkit.broadcastMessage("The next empty slot for team "+teamId+" is " + (maxSlots + 1));
        return maxSlots + 1;
    }

    public enum Empty {
        EMPTY,
    }

    public void clearDeckSlot(Location l) {
        plugin.getConfig().set(getSlotPathId(l), Empty.EMPTY);
        plugin.saveConfig();

        Bukkit.broadcastMessage("Cleared Slot for: "+getSlotPathId(l));
    }

    public String getSlotPathId(Location loc) {
        String nl = null;
        World w = Bukkit.getWorld("samer3abyt");
        Location l = new Location(w,Math.floor(loc.getX()),Math.floor(loc.getY()),Math.floor(loc.getZ()));
        Bukkit.broadcastMessage("Clicked slot: " + l.getX() +" "+l.getY()+" "+l.getZ());
        if (Objects.equals(l, new Location(w, 59, 77, -41))) {
            nl = "Teams.RedTeam.Deck.Slot1";
        } else if (Objects.equals(l, new Location(w, 60, 77, -41))) {
            nl = "Teams.RedTeam.Deck.Slot2";
        } else if (Objects.equals(l, new Location(w, 61, 77, -41))) {
            nl = "Teams.RedTeam.Deck.Slot3";
        } else if (Objects.equals(l, new Location(w, 62, 77, -41))) {
            nl = "Teams.RedTeam.Deck.Slot4";
        } else if (Objects.equals(l, new Location(w, 63, 77, -41))) {
            nl = "Teams.RedTeam.Deck.Slot5";
        } else if (Objects.equals(l, new Location(w, 64, 77, -41))) {
            nl = "Teams.RedTeam.Deck.Slot6";
        } else if (Objects.equals(l, new Location(w, 65, 77, -41))) {
            nl = "Teams.RedTeam.Deck.Slot7";
        } else if (Objects.equals(l, new Location(w, 66, 77, -41))) {
            nl = "Teams.RedTeam.Deck.Slot8";
        } else if (Objects.equals(l, new Location(w, 67, 77, -41))) {
            nl = "Teams.RedTeam.Deck.Slot9";
        } else if (Objects.equals(l, new Location(w, 77, 77, -51))) {
            nl = "Teams.BlueTeam.Deck.Slot1";
        } else if (Objects.equals(l, new Location(w, 77, 77, -52))) {
            nl = "Teams.BlueTeam.Deck.Slot2";
        } else if (Objects.equals(l, new Location(w, 77, 77, -53))) {
            nl = "Teams.BlueTeam.Deck.Slot3";
        } else if (Objects.equals(l, new Location(w, 77, 77, -54))) {
            nl = "Teams.BlueTeam.Deck.Slot4";
        } else if (Objects.equals(l, new Location(w, 77, 77, -55))) {
            nl = "Teams.BlueTeam.Deck.Slot5";
        } else if (Objects.equals(l, new Location(w, 77, 77, -56))) {
            nl = "Teams.BlueTeam.Deck.Slot6";
        } else if (Objects.equals(l, new Location(w, 77, 77, -57))) {
            nl = "Teams.BlueTeam.Deck.Slot7";
        } else if (Objects.equals(l, new Location(w, 77, 77, -58))) {
            nl = "Teams.BlueTeam.Deck.Slot8";
        } else if (Objects.equals(l, new Location(w, 77, 77, -59))) {
            nl = "Teams.BlueTeam.Deck.Slot9";
        } else if (Objects.equals(l, new Location(w, 67, 77, -69))) {
            nl = "Teams.YellowTeam.Deck.Slot1";
        } else if (Objects.equals(l, new Location(w, 66, 77, -69))) {
            nl = "Teams.YellowTeam.Deck.Slot2";
        } else if (Objects.equals(l, new Location(w, 65, 77, -69))) {
            nl = "Teams.YellowTeam.Deck.Slot3";
        } else if (Objects.equals(l, new Location(w, 64, 77, -69))) {
            nl = "Teams.YellowTeam.Deck.Slot4";
        } else if (Objects.equals(l, new Location(w, 63, 77, -69))) {
            nl = "Teams.YellowTeam.Deck.Slot5";
        } else if (Objects.equals(l, new Location(w, 62, 77, -69))) {
            nl = "Teams.YellowTeam.Deck.Slot6";
        } else if (Objects.equals(l, new Location(w, 61, 77, -69))) {
            nl = "Teams.YellowTeam.Deck.Slot7";
        } else if (Objects.equals(l, new Location(w, 60, 77, -69))) {
            nl = "Teams.YellowTeam.Deck.Slot8";
        } else if (Objects.equals(l, new Location(w, 59, 77, -69))) {
            nl = "Teams.YellowTeam.Deck.Slot9";
        } else if (Objects.equals(l, new Location(w, 49, 77, -59))) {
            nl = "Teams.GreenTeam.Deck.Slot1";
        } else if (Objects.equals(l, new Location(w, 49, 77, -58))) {
            nl = "Teams.GreenTeam.Deck.Slot2";
        } else if (Objects.equals(l, new Location(w, 49, 77, -57))) {
            nl = "Teams.GreenTeam.Deck.Slot3";
        } else if (Objects.equals(l, new Location(w, 49, 77, -56))) {
            nl = "Teams.GreenTeam.Deck.Slot4";
        } else if (Objects.equals(l, new Location(w, 49, 77, -55))) {
            nl = "Teams.GreenTeam.Deck.Slot5";
        } else if (Objects.equals(l, new Location(w, 49, 77, -54))) {
            nl = "Teams.GreenTeam.Deck.Slot6";
        } else if (Objects.equals(l, new Location(w, 49, 77, -53))) {
            nl = "Teams.GreenTeam.Deck.Slot7";
        } else if (Objects.equals(l, new Location(w, 49, 77, -52))) {
            nl = "Teams.GreenTeam.Deck.Slot8";
        } else if (Objects.equals(l, new Location(w, 49, 77, -51))) {
            nl = "Teams.GreenTeam.Deck.Slot9";
        }

        return nl;
    }

    public boolean isEmptySlot(String path) {
        if (plugin.getConfig().get(path) == Empty.EMPTY) {

            return true;
        }
        return false;
    }
    public Slot getAvailablePSlot(String teamId, Cards.Card c){

        return new Slot(getAvailableRow(getSetColumn(c.getSet(),teamId),teamId),getSetColumn(c.getSet(),teamId));
    }
    public int getSetColumn(Cards.Set s, String teamId){

        ArrayList<Cards.Set> sl = new ArrayList<>();
        int n = 0;

        for(int i = 1; i <= 9; i++){
            if(plugin.getConfig().contains("Teams."+teamId+".Property.Column"+i+"Set")){
                Cards.Set ss = getSetFromId(plugin.getConfig().getString("Teams."+teamId+".Property.Column"+i+"Set"));
                sl.add(ss);
            }else{
                sl.add(Cards.Set.EMPTY);
            }
        }

        // Check if any column already has the same set
        if(sl.contains(s)){
            n = sl.indexOf(s);
        } else {
            // If the set is not found in any column, find the first empty column
            for (int i = 1; i <= 9; i++) {
                if (sl.get(i - 1) == Cards.Set.EMPTY) {
                    n = i - 1;  // Found an empty column
                    break;
                }
            }
        }

        return n;
    }
    public int getAvailableRow(int column, String teamId) {
        ArrayList<String> cardList = (ArrayList<String>) plugin.getConfig().getList("Teams." + teamId + ".Property.Column" + (column + 1) + "Cards");

        if (cardList != null) {
            int lastRow = cardList.size();
            // If there are cards in the column, return the next available row
            return lastRow;
        }

        // If the list is null or empty, start from the first row (0)
        return 0;
    }

}
