package me.cipher.cardwars.mechanics;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.stations.BlueTeam;
import me.cipher.cardwars.stations.GreenTeam;
import me.cipher.cardwars.stations.RedTeam;
import me.cipher.cardwars.stations.YellowTeam;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

import static org.bukkit.Bukkit.getLogger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.logging.Logger;

public class GameStart {
    private final CardWars plugin;

    public GameStart(CardWars plugin) {
        this.plugin = plugin;
    }

    public void startGame(ArrayList<String> players) {
        // Ensure there are at least 4 players to start the game
        if (players.size() < 4) {
            // Iterate through the player list
            for (int i = 0; i < players.size(); i++) {
                String playerName = players.get(i);
                Player player = Bukkit.getPlayer(playerName);

                // Check if the player is online
                if (player != null) {
                    // Teleport the player to the designated station
                    player.teleport(station(i));

                    // Set player to the corresponding team based on the index
                    assignPlayerToTeam(player, i);

                } else {
                    // Log a warning if the player is not found or offline
                    getLogger().warning("Player " + playerName + " not found or offline.");
                }
            }
        }
    }

    // Assign player to a team based on the index
    private void assignPlayerToTeam(Player player, int index) {
        switch (index) {
            case 0:
                new RedTeam(plugin).setPlayer(player);
                break;
            case 1:
                new BlueTeam(plugin).setPlayer(player);
                break;
            case 2:
                new YellowTeam(plugin).setPlayer(player);
                break;
            case 3:
                new GreenTeam(plugin).setPlayer(player);
                break;
            // Add more cases if additional teams are introduced
            // ...
        }
    }

    // Get the Logger instance from the plugin
    private Logger getLogger() {
        return plugin.getLogger();
    }

    // Get the location of the station based on the index
    public Location station(int index) {
        World world = Bukkit.getWorld("samer3abyt");
        Location location = null;

        // Define station locations based on the index
        switch (index) {
            case 0:
                location = new Location(world, 63.5, 73, -36.5, 180, 0);
                break;
            case 1:
                location = new Location(world, 81.5, 76.00, -54.5, 90, 0);
                break;
            case 2:
                location = new Location(world, 63.5, 76.00, -72.5, 0, 0);
                break;
            case 3:
                location = new Location(world, 45.5, 76.00, -54.5, -90, 0);
                break;
            // Add more cases if additional stations are introduced
            // ...
        }
        return location;
    }
}