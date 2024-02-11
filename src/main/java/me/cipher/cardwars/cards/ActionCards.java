package me.cipher.cardwars.cards;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.stations.BlueTeam;
import me.cipher.cardwars.stations.GreenTeam;
import me.cipher.cardwars.stations.RedTeam;
import me.cipher.cardwars.stations.YellowTeam;
import me.cipher.cardwars.uitls.States;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

public class ActionCards implements Listener {

    private final CardWars plugin;

    public ActionCards(CardWars plugin) {
        this.plugin = plugin;
    }
    ;
    @EventHandler
    public void onAction(PlayerInteractAtEntityEvent e){

        States s = new States(plugin);
        Entity en = e.getRightClicked();
        Player p = e.getPlayer();

        if(/*!s.isInGame(p) || !s.activeTurn(p) || */!(en instanceof ItemFrame)) return;

        ItemFrame iframe = (ItemFrame) en;

        if(iframe.getItem().getType() != Material.FILLED_MAP) return;

        MapMeta mm = (MapMeta) iframe.getItem().getItemMeta();

        if(mm.getDisplayName().equalsIgnoreCase("Debt Collector")){

            e.setCancelled(true);
            iframe.setItem(new ItemStack(Material.AIR));

            ArrayList<Material> m = new ArrayList<>();
            m.add(Material.RED_CONCRETE);
            m.add(Material.BLUE_CONCRETE);
            m.add(Material.YELLOW_CONCRETE);
            m.add(Material.GREEN_CONCRETE);

            openGUI(p,m,9,"DebtCollector");

            p.sendMessage("It works!!!!!");
        }
    }
    public static void openGUI(Player p, ArrayList<Material> l, int slots, String name){

        Inventory customGUI = Bukkit.createInventory(p,slots,name);

        for(int i = 0; i < l.size(); i++){

            ItemStack item = new ItemStack(l.get(i));
            customGUI.setItem(i, item);
        }

        p.openInventory(customGUI);
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(!e.getView().getTitle().equalsIgnoreCase("DebtCollector")) return;
        e.setCancelled(true);

        if(e.getCurrentItem().getType() == Material.RED_CONCRETE){

            if(!plugin.getConfig().contains("Teams.RedTeam.Gold")) return;

            int gold = plugin.getConfig().getInt("Teams.RedTeam.Gold");

            if(gold > 4){

                plugin.getConfig().set("Teams.RedTeam.Gold",gold - 5);
                plugin.saveConfig();

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "scoreboard players remove "+new RedTeam(plugin).getPlayer().getName() +" Gold "+ 5);

            }else{

                //give property plus any leftover cash
            }

        }
        if(e.getCurrentItem().getType() == Material.BLUE_CONCRETE){

            if(!plugin.getConfig().contains("Teams.BlueTeam.Gold")) return;

            int gold = plugin.getConfig().getInt("Teams.Blueeam.Gold");

            if(gold > 4){

                plugin.getConfig().set("Teams.BlueTeam.Gold",gold - 5);
                plugin.saveConfig();

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "scoreboard players remove "+new BlueTeam(plugin).getPlayer().getName() +" Gold "+ 5);

            }else{

                //give property plus any leftover cash
            }

        }
        if(e.getCurrentItem().getType() == Material.YELLOW_CONCRETE){

            if(!plugin.getConfig().contains("Teams.YellowTeam.Gold")) return;

            int gold = plugin.getConfig().getInt("Teams.YellowTeam.Gold");

            if(gold > 4){

                plugin.getConfig().set("Teams.YellowTeam.Gold",gold - 5);
                plugin.saveConfig();

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "scoreboard players remove "+new YellowTeam(plugin).getPlayer().getName() +" Gold "+ 5);

            }else{

                //give property plus any leftover cash
            }

        }
        if(e.getCurrentItem().getType() == Material.GREEN_CONCRETE){

            if(!plugin.getConfig().contains("Teams.GreenTeam.Gold")) return;

            int gold = plugin.getConfig().getInt("Teams.GreenTeam.Gold");

            if(gold > 4){

                plugin.getConfig().set("Teams.GreenTeam.Gold",gold - 5);
                plugin.saveConfig();

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        "scoreboard players remove "+new GreenTeam(plugin).getPlayer().getName() +" Gold "+ 5);

            }else{

                //give property plus any leftover cash
            }

        }

        e.getWhoClicked().closeInventory();
    }
}
