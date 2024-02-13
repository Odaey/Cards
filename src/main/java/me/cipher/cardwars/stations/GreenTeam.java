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
import java.util.Arrays;

public class GreenTeam {
    private final CardWars plugin;

    public GreenTeam(CardWars plugin) {
        this.plugin = plugin;
    }

    // Gets the Green Team's ID.
    public String getTeamID() {
        return "GreenTeam";
    }

    // Gets the initial spawn point for the Green Team.
    public Location getInitialPoint() {
        return new Location(Bukkit.getWorld("samer3abyt"), 49, 81, -60);
    }
    public Location getDeckSlot(int i){

        return new Location(Bukkit.getWorld("samer3abyt"),49,77, -59 + i);
    }

    // Translates coordinates based on the given slot.
    public Location translateCords(Slot s) {
        Location initialPoint = getInitialPoint();
        return new Location(initialPoint.getWorld(), initialPoint.getX() - s.getColumn(), initialPoint.getY() - s.getRow(), initialPoint.getZ());
    }

    // Gets the Green Team's spawn location.
    public Location getSpawnLocation() {
        World world = Bukkit.getWorld("samer3abyt");
        return new Location(world, 45.80, 76.00, -54.43, -90, 0);
    }

    // Sets the player associated with the Green Team.
    public void setPlayer(Player p) {
        plugin.getConfig().set("Teams." + getTeamID() + ".Player", p.getName());
        plugin.saveConfig();
    }

    // Gets the player associated with the Green Team.
    public Player getPlayer() {
        String playerName = plugin.getConfig().getString("Teams." + getTeamID() + ".Player");
        return Bukkit.getPlayer(playerName);
    }

    // Gets the direction the Green Team's deck is facing.
    public BlockFace getDeckDirection() {
        return BlockFace.WEST;
    }

    // Gets the direction the Green Team's property is facing.
    public BlockFace getPropertyDirection() {
        return BlockFace.SOUTH;
    }

    // Gets the direction the Green Team's scouting area is facing.
    public BlockFace getScoutingDirection() {
        return BlockFace.NORTH;
    }

    // Gets a list of all groups associated with the Green Team.

}


