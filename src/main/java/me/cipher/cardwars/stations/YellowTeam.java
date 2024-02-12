package me.cipher.cardwars.stations;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.cards.Cards;
import me.cipher.cardwars.uitls.Slot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class YellowTeam {
    private final CardWars plugin;

    public YellowTeam(CardWars plugin) {
        this.plugin = plugin;
    }

    // Gets the Yellow Team's ID.
    public String getTeamID() {
        return "YellowTeam";
    }

    // Gets the initial spawn point for the Yellow Team.
    public Location getInitialPoint() {
        return new Location(Bukkit.getWorld("samer3abyt"), 68, 81, -71);

    }
    public Location getDeckSlot(int i){

        return new Location(Bukkit.getWorld("samer3abyt"),67 - i,77, -69);
    }

    // Translates coordinates based on the given slot.
    // -Z direction is considered.
    public Location translateCords(Slot s) {
        Location initialPoint = getInitialPoint();
        return new Location(initialPoint.getWorld(), initialPoint.getX(), initialPoint.getY() - s.getRow(), initialPoint.getZ() - s.getColumn());
    }

    // Gets the Yellow Team's spawn location.
    public Location getSpawnLocation() {
        World world = Bukkit.getWorld("samer3abyt");
        return new Location(world, 63.55, 76.00, -72.43, 0, 0);
    }

    // Sets the player associated with the Yellow Team.
    public void setPlayer(Player p) {
        plugin.getConfig().set("Teams." + getTeamID() + ".Player", p.getName());
        plugin.saveConfig();
    }

    // Gets the direction the Yellow Team's deck is facing.
    public BlockFace getDeckDirection() {
        return BlockFace.NORTH;
    }

    // Gets the direction the Yellow Team's property is facing.
    public BlockFace getPropertyDirection() {
        return BlockFace.WEST;
    }

    // Gets the direction the Yellow Team's scouting area is facing.
    public BlockFace getScoutingDirection() {
        return BlockFace.EAST;
    }

    // Gets the player associated with the Yellow Team.
    public Player getPlayer() {
        String playerName = plugin.getConfig().getString("Teams." + getTeamID() + ".Player");
        return Bukkit.getPlayer(playerName);
    }

    // Gets a list of all groups associated with the Yellow Team.
    public ArrayList<Cards.Group> getAllGroups() {
        ArrayList<Cards.Group> groups = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            String groupPath = "Teams." + getTeamID() + ".Properties.Groups.Group" + i + ".Type";

            if (plugin.getConfig().contains(groupPath)) {
                Cards.Group group = (Cards.Group) plugin.getConfig().get(groupPath);
                groups.add(group);
            } else {
                groups.add(Cards.Group.EMPTY);
            }
        }

        return groups;
    }
}