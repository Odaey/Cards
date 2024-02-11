package me.cipher.cardwars.testingkit;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.stations.BlueTeam;
import me.cipher.cardwars.stations.GreenTeam;
import me.cipher.cardwars.stations.RedTeam;
import me.cipher.cardwars.stations.YellowTeam;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QuickStart implements CommandExecutor {

    private final CardWars plugin;

    public QuickStart(CardWars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {

        if(cs instanceof Player){

            Player p = (Player) cs;

            if(s.equalsIgnoreCase("setTeam")){

                if(args.length == 1){

                    String team = args[0];

                    if(team.equalsIgnoreCase("RedTeam")) new RedTeam(plugin).setPlayer(p);
                    if(team.equalsIgnoreCase("BlueTeam")) new BlueTeam(plugin).setPlayer(p);
                    if(team.equalsIgnoreCase("YellowTeam")) new YellowTeam(plugin).setPlayer(p);
                    if(team.equalsIgnoreCase("GreenTeam")) new GreenTeam(plugin).setPlayer(p);
                }
            }
        }
        return false;
    }
}
