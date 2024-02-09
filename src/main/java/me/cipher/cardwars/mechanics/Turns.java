package me.cipher.cardwars.mechanics;

import me.cipher.cardwars.CardWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Turns {
    private final CardWars plugin;

    public Turns(CardWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Gets the player associated with the active team.
     *
     * @return The player associated with the active team.
     */
    public Player getPlayer() {
        String playerName = plugin.getConfig().getString("Teams." + getTeam() + ".Player");
        return Bukkit.getPlayer(playerName);
    }

    /**
     * Gets the remaining turns.
     *
     * @return The remaining turns.
     */
    public int getTurns() {
        String path = "Game.Turns.TurnsLeft";
        return plugin.getConfig().getInt(path, 0);
    }

    /**
     * Gets the ID of the active team.
     *
     * @return The ID of the active team.
     */
    public String getTeam() {
        String path = "Game.Turns.ActiveTeam";
        return plugin.getConfig().getString(path, "");
    }

    /**
     * Checks if it's the turn of the specified player.
     *
     * @param player The player to check.
     * @return True if it's the turn of the specified player, false otherwise.
     */
    public boolean hasTurns(Player player) {
        return player == getPlayer();
    }

    /**
     * Sets the remaining turns.
     *
     * @param turns The number of turns to set.
     */
    public void setTurns(int turns) {
        plugin.getConfig().set("Game.Turns.TurnsLeft", turns);
        plugin.saveConfig();
    }

    /**
     * Sets the active team.
     *
     * @param teamId The ID of the team to set as active.
     */
    public void setTeam(String teamId) {
        plugin.getConfig().set("Game.Turns.ActiveTeam", teamId);
        plugin.saveConfig();
    }

    /**
     * Moves to the next team in a circular manner (RedTeam -> BlueTeam -> YellowTeam -> GreenTeam -> RedTeam).
     */
    public void nextTeam() {
        String currentTeam = getTeam();
        switch (currentTeam) {
            case "RedTeam":
                setTeam("BlueTeam");
                break;
            case "BlueTeam":
                setTeam("YellowTeam");
                break;
            case "YellowTeam":
                setTeam("GreenTeam");
                break;
            case "GreenTeam":
                setTeam("RedTeam");
                break;
        }
    }

    /**
     * Moves to the next turn and updates the active team accordingly.
     */
    public void nextTurn() {
        int remainingTurns = getTurns();
        if (remainingTurns == 3) {
            setTurns(2);
        } else if (remainingTurns == 2) {
            setTurns(1);
        } else if (remainingTurns == 1) {
            setTurns(3);
            nextTeam();
        }
    }
}
