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

public class BlueTeam {
    private final CardWars plugin;

    public BlueTeam(CardWars plugin) {
        this.plugin = plugin;
    }

    // Gets the Blue Team's ID.
    public String getTeamID() {
        return "BlueTeam";
    }

    // Gets the initial spawn point for the Blue Team.
    public Location getInitialPoint() {
        return new Location(Bukkit.getWorld("samer3abyt"), 78, 81, -50);
    }
    public Location getDeckSlot(int i){

        return new Location(Bukkit.getWorld("samer3abyt"),59 + i,77, -41);
    }

    // Translates coordinates based on the given slot.
    public Location translateCords(Slot s) {
        Location initialPoint = getInitialPoint();
        return new Location(initialPoint.getWorld(), initialPoint.getX() + s.getColumn(), initialPoint.getY() - s.getRow(), initialPoint.getZ());
    }

    // Gets the Blue Team's spawn location.
    public Location getSpawnLocation() {
        World world = Bukkit.getWorld("samer3abyt");
        return new Location(world, 81.37, 76.00, -54.55, 90, 0);
    }

    // Gets the direction the Blue Team's deck is facing.
    public BlockFace getDeckDirection() {
        return BlockFace.EAST;
    }

    // Gets the direction the Blue Team's property is facing.
    public BlockFace getPropertyDirection() {
        return BlockFace.NORTH;
    }

    // Gets the direction the Blue Team's scouting area is facing.
    public BlockFace getScoutingDirection() {
        return BlockFace.SOUTH;
    }

    // Sets the player associated with the Blue Team.
    public void setPlayer(Player p) {
        plugin.getConfig().set("Teams." + getTeamID() + ".Player", p.getName());
        plugin.saveConfig();
    }

    // Gets the player associated with the Blue Team.
    public Player getPlayer() {
        String playerName = plugin.getConfig().getString("Teams." + getTeamID() + ".Player");
        return Bukkit.getPlayer(playerName);
    }

    // Gets a list of all groups associated with the Blue Team.

}

