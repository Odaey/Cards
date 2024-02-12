package me.cipher.cardwars.testingkit;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.uitls.SlotGetters;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Draw implements CommandExecutor {

    private final CardWars plugin;

    private final ArrayList<MapMeta> m = new ArrayList<>(Arrays.asList(addCard("Debt Collector","","Debt_Collector",3),
            addCard("Just Say No","","Just_Say_No",9),addCard("1","Gold","1_GOLD",20),addCard("2","Gold","2_GOLD",18),
            addCard("5","Gold","5_GOLD",19),addCard("10","Gold","10_GOLD",17),addCard("20","Gold","20_GOLD",16),
            addCard("","Diamond","DIAMOND_CARD",21)));
    public Draw(CardWars plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if(commandSender instanceof Player){

            Player p = (Player) commandSender;

            if(s.equalsIgnoreCase("draw")){

                if(args.length == 1){

                    Player tp = Bukkit.getPlayer(args[0]);
                    String team = new SlotGetters(plugin).getPlayerTeam(tp);
                    Collections.shuffle(m);

                    MapMeta mp = m.get(0);

                    new SlotGetters(plugin).addDeckCard(mp,team);
                }
            }
        }
        return false;
    }
    public MapMeta addCard(String name, String lore, String codeName, int id){

        ItemStack item = new ItemStack(Material.FILLED_MAP);
        MapMeta mm = (MapMeta) item.getItemMeta();

        mm.setDisplayName(name);
        mm.setLore(new ArrayList<>(Arrays.asList(lore,codeName)));
        mm.setMapId(id);

        item.setItemMeta(mm);

        return mm;
    }
}
