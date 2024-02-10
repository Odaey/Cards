package me.cipher.cardwars;

import me.cipher.cardwars.cards.Properties;
import me.cipher.cardwars.mechanics.Cmds;
import me.cipher.cardwars.testingkit.Visuals;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;

public final class CardWars extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new Properties(this),this);

        getServer().getPluginCommand("addplayer").setExecutor(new Cmds(this));
        getServer().getPluginCommand("removeplayer").setExecutor(new Cmds(this));
        getServer().getPluginCommand("startgame").setExecutor(new Cmds(this));
        getServer().getPluginCommand("setProperty").setExecutor(new Visuals(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
