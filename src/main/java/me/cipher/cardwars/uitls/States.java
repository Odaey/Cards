package me.cipher.cardwars.uitls;

import me.cipher.cardwars.CardWars;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class States {
    private final CardWars plugin;

    public enum Dues{

        DEBT,
        NONE,
    }
    public States(CardWars plugin) {
        this.plugin = plugin;
    }

    // Checks if a player is in the game lobby.
    public boolean isInGame(Player player) {
        String lobbyPath = "Game.Lobby";

        if (plugin.getConfig().contains(lobbyPath)) {
            return plugin.getConfig().getStringList(lobbyPath).contains(player.getName());
        }
        return false;
    }

    // Checks if it is the active turn for the given player.
    public boolean activeTurn(Player player) {
        String currentPlayerPath = "Game.Turns.CurrentPlayer";

        if (plugin.getConfig().contains(currentPlayerPath)) {
            String currentPlayerName = plugin.getConfig().getString(currentPlayerPath);
            return player.getName().equals(currentPlayerName);
        }
        return false;
    }
    public boolean isHolding(Player p){

        return p.getInventory().getItemInMainHand().getType() == Material.FILLED_MAP;
    }
    public void addDue(Player p, Dues d){

        plugin.getConfig().set("Teams."+new SlotGetters(plugin).getPlayerTeam(p)+".DueStatus", d);
        plugin.saveConfig();
    }
    public boolean hasDebt(Player p){

        return  plugin.getConfig().get("Teams."+new SlotGetters(plugin).getPlayerTeam(p)+".DueStatus").equals(Dues.DEBT);
    }
}
