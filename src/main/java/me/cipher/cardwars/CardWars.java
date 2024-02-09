package me.cipher.cardwars;

import me.cipher.cardwars.cards.Properties;
import org.bukkit.plugin.java.JavaPlugin;

public final class CardWars extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new Properties(this),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
