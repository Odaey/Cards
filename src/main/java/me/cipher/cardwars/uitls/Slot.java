package me.cipher.cardwars.uitls;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.cards.Cards;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;

public class Slot {

    private CardWars plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;
    private int row;
    private int column;

    public Slot(int row, int column) {
        this.row = row;
        this.column = column;
        this.plugin = plugin;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}