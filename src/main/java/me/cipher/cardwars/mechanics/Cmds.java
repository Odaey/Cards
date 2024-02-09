package me.cipher.cardwars.mechanics;

import me.cipher.cardwars.CardWars;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

import static org.bukkit.Bukkit.getServer;

public class Cmds implements CommandExecutor {
    private final CardWars plugin;

    public Cmds(CardWars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {
        // Check if the sender is a player
        if (cs instanceof Player) {
            Player p = (Player) cs;

            // Process the "addplayer" command
            if (s.equalsIgnoreCase("addplayer")) {
                if (args.length == 1) {
                    String playerName = args[0];
                    Player targetPlayer = plugin.getServer().getPlayer(playerName);

                    // Check if the target player exists
                    if (targetPlayer == null) {
                        p.sendMessage("Player " + playerName + " does not currently exist");
                        return true;
                    }

                    // Update or create the lobby list in the config
                    ArrayList<String> lobbyList = (ArrayList<String>) plugin.getConfig().getStringList("Game.Lobby");
                    lobbyList.add(playerName);
                    plugin.getConfig().set("Game.Lobby", lobbyList);
                    plugin.saveConfig();

                } else {
                    p.sendMessage("Usage: /addplayer <player name>");
                    return true;
                }
            }

            // Process the "removeplayer" command
            if (s.equalsIgnoreCase("removeplayer")) {
                if (args.length == 1) {
                    String playerName = args[0];
                    Player targetPlayer = plugin.getServer().getPlayer(playerName);

                    // Check if the target player exists
                    if (targetPlayer == null) {
                        p.sendMessage("Player " + playerName + " does not currently exist");
                        return true;
                    }

                    // Remove player from the lobby list in the config
                    if (plugin.getConfig().contains("Game.Lobby")) {
                        ArrayList<String> lobbyList = (ArrayList<String>) plugin.getConfig().getStringList("Game.Lobby");
                        lobbyList.remove(playerName);
                        plugin.getConfig().set("Game.Lobby", lobbyList);
                        plugin.saveConfig();
                    } else {
                        p.sendMessage("No players are currently on the list");
                    }

                } else {
                    p.sendMessage("Usage: /removeplayer <player name>");
                    return true;
                }
            }

            // Process the "startgame" command
            if (s.equalsIgnoreCase("startgame")) {
                if (plugin.getConfig().contains("Game.Lobby")) {
                    ArrayList<String> lobbyList = (ArrayList<String>) plugin.getConfig().getStringList("Game.Lobby");

                    // Check if there are enough players to start the game
                    if (lobbyList.size() < 2) {
                        p.sendMessage("Insufficient amount of players");

                        return true;
                    }
                    //Start the Game
                    new GameStart(plugin).startGame(lobbyList);
                } else {
                    p.sendMessage("No players are currently on the list");
                }
            }
        }
        return false;
    }
}
