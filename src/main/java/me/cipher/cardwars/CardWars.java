package me.cipher.cardwars;

import me.cipher.cardwars.cards.ActionCards;
import me.cipher.cardwars.cards.Gold;
import me.cipher.cardwars.cards.Properties;
import me.cipher.cardwars.mechanics.Cmds;
import me.cipher.cardwars.testingkit.QuickStart;
import me.cipher.cardwars.testingkit.Visuals;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;

public final class CardWars extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new Properties(this),this);
        getServer().getPluginManager().registerEvents(new ActionCards(this),this);
        getServer().getPluginManager().registerEvents(new Gold(this),this);

        getServer().getPluginCommand("addplayer").setExecutor(new Cmds(this));
        getServer().getPluginCommand("removeplayer").setExecutor(new Cmds(this));
        getServer().getPluginCommand("startgame").setExecutor(new Cmds(this));
        getServer().getPluginCommand("setproperty").setExecutor(new Visuals(this));
        getServer().getPluginCommand("setteam").setExecutor(new QuickStart(this));
        getServer().getPluginCommand("getteam").setExecutor(new QuickStart(this));
        getServer().getPluginCommand("setgold").setExecutor(new QuickStart(this));
        getServer().getPluginCommand("getgold").setExecutor(new QuickStart(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
