package me.cipher.cardwars.stations;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.cards.Cards;
import me.cipher.cardwars.uitls.Slot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class RedTeam {
    private final CardWars plugin;

    public RedTeam(CardWars plugin) {
        this.plugin = plugin;
    }

    // Gets the Red Team's ID.
    public String getTeamID() {
        return "RedTeam";
    }

    // Translates coordinates based on the given slot.
    // +Z direction is considered.
    public Location translateCords(Slot s) {
        Location initialPoint = getInitialPoint();
        return new Location(initialPoint.getWorld(), initialPoint.getX(), initialPoint.getY() - s.getRow(), initialPoint.getZ() + s.getColumn());
    }

    // Gets the initial spawn point for the Red Team.
    public Location getInitialPoint() {
        return new Location(Bukkit.getWorld("samer3abyt"), 58, 81, -41);
    }

    public Location getDeckSlot(int i){

        return new Location(Bukkit.getWorld("samer3abyt"),59 + i,77, -41);
    }

    // Gets the direction the Red Team's deck is facing.
    public BlockFace getDeckDirection() {
        return BlockFace.SOUTH;
    }

    // Gets the direction the Red Team's property is facing.
    public BlockFace getPropertyDirection() {
        return BlockFace.EAST;
    }

    // Gets the direction the Red Team's scouting area is facing.
    public BlockFace getScoutingDirection() {
        return BlockFace.WEST;
    }

    // Gets the Red Team's spawn location.
    public Location getSpawnLocation() {
        World world = Bukkit.getWorld("samer3abyt");
        return new Location(world, 63, 73, -36, 180, 0);
    }

    // Sets the player associated with the Red Team.
    public void setPlayer(Player p) {
        plugin.getConfig().set("Teams." + getTeamID() + ".Player", p.getName());
        plugin.saveConfig();
    }

    // Gets the player associated with the Red Team.
    public Player getPlayer() {
        String playerName = plugin.getConfig().getString("Teams." + getTeamID() + ".Player");
        return Bukkit.getPlayer(playerName);
    }

    // Gets a list of all groups associated with the Red Team.

}
