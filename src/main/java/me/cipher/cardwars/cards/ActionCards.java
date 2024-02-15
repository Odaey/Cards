package me.cipher.cardwars.cards;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.stations.BlueTeam;
import me.cipher.cardwars.stations.GreenTeam;
import me.cipher.cardwars.stations.RedTeam;
import me.cipher.cardwars.stations.YellowTeam;
import me.cipher.cardwars.uitls.SlotGetters;
import me.cipher.cardwars.uitls.States;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.A;

import javax.swing.plaf.nimbus.State;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ActionCards implements Listener {
    private BukkitRunnable timer;
    private final CardWars plugin;
    private ArrayList<String> list = new ArrayList<>(Arrays.asList("RedTeam","BlueTeam","YellowTeam","GreenTeam"));
    public ActionCards(CardWars plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAction(PlayerInteractAtEntityEvent e){

        States s = new States(plugin);
        SlotGetters g = new SlotGetters(plugin);
        Entity en = e.getRightClicked();
        Player p = e.getPlayer();

        ArrayList<Material> m = new ArrayList<>();
        m.add(Material.RED_CONCRETE);
        m.add(Material.BLUE_CONCRETE);
        m.add(Material.YELLOW_CONCRETE);
        m.add(Material.GREEN_CONCRETE);

        ArrayList<String> t = new ArrayList<>();
        t.add("RedTeam");
        t.add("BlueTeam");
        t.add("YellowTeam");
        t.add("GreenTeam");

        if(/*!s.isInGame(p) || !s.activeTurn(p) || */!(en instanceof ItemFrame)) return;

        ItemFrame iframe = (ItemFrame) en;


        if(iframe.getItem().getType() != Material.FILLED_MAP) return;

        MapMeta mm = (MapMeta) iframe.getItem().getItemMeta();
        String cardType = mm.getLore().get(0);
        String cname = mm.getLore().get(1);

        if(!cardType.equalsIgnoreCase("Action Card")) return;

        if(cname.equalsIgnoreCase("Debt_Collector")){
            g.clearDeckSlot(iframe.getLocation());
            iframe.remove();

            openGUI(p,m,9,"DebtCollector");
            e.setCancelled(true);
        }
        if(cname.equalsIgnoreCase("Birthday_Present")) {
            g.clearDeckSlot(iframe.getLocation());
            iframe.remove();

            Bukkit.broadcastMessage(
                    "You owe " + p.getName() + " a birthday present, If uninterrupted in 10 seconds, 2 Gold will be taken from your bank");

            t.remove(g.getPlayerTeam(p));
            for(String teamN : t){

                plugin.getConfig().set("Teams."+teamN+".DueStatus", States.Dues.DEBT);
                plugin.saveConfig();
            }
            timer = new BukkitRunnable() {
                @Override
                public void run() {
                    for(String teamN : t){

                        if(plugin.getConfig().contains("Teams."+teamN+".DueStatus")){

                            if(plugin.getConfig().get("Teams."+teamN+".DueStatus") == States.Dues.DEBT) t.remove(teamN);
                        }
                    }
                    int goldOwed = 0;
                    for (String teamN : t) {

                        if (!plugin.getConfig().contains("Teams." + teamN + ".Gold")) return;

                        if (plugin.getConfig().getInt("Teams." + teamN + ".Gold") > 1) {

                            int theirGold = plugin.getConfig().getInt("Teams." + teamN + ".Gold");
                            int newGold = theirGold - 2;

                            plugin.getConfig().set("Teams." + teamN + ".Gold", newGold);
                            plugin.saveConfig();

                            goldOwed = goldOwed + 2;
                        }
                    }
                    plugin.getConfig().set("Teams." + g.getPlayerTeam(p) + ".Gold", goldOwed);
                    plugin.saveConfig();
                }
            };
            timer.runTaskLater(plugin, 10 * 20L);
            e.setCancelled(true);
        }
        if(cname.equalsIgnoreCase("Set_OverCharge")){
            g.clearDeckSlot(iframe.getLocation());
            iframe.remove();

            openGUI(p,m,9,"Set OverCharge Selection");
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onReaction(PlayerInteractAtEntityEvent e){

        States s = new States(plugin);
        SlotGetters g = new SlotGetters(plugin);
        Entity en = e.getRightClicked();
        Player p = e.getPlayer();

        if(/*!s.isInGame(p) || */!s.hasDebt(p) || !(en instanceof ItemFrame)) return;

        ItemFrame iframe = (ItemFrame) en;

        if(iframe.getItem().getType() != Material.FILLED_MAP) return;

        MapMeta mm = (MapMeta) iframe.getItem().getItemMeta();
        ArrayList<String> l = (ArrayList<String>) mm.getLore();

        if(mm.getDisplayName().equalsIgnoreCase("Just Say No")){
            Bukkit.broadcastMessage("Slot location is at " + iframe.getLocation().getX() +" "+ iframe.getLocation().getY() +" "+iframe.getLocation().getZ());
            g.clearDeckSlot(iframe.getLocation());
            iframe.remove();

            new States(plugin).addDue(p, States.Dues.NONE);
            e.setCancelled(true);
            Bukkit.broadcastMessage(p.getName()+" just said no");
        }else if(l.contains("Diamond")){
            Bukkit.broadcastMessage("Slot location is at " + iframe.getLocation().getX() +" "+ iframe.getLocation().getY() +" "+iframe.getLocation().getZ());
            g.clearDeckSlot(iframe.getLocation());
            iframe.remove();

            new States(plugin).addDue(p, States.Dues.NONE);
            Bukkit.broadcastMessage(p.getName()+" Used the "+ ChatColor.BLUE + "Diamond Card");

            e.setCancelled(true);
        }
    }
    public void openGUI(Player p, ArrayList<Material> l, int slots, String name){

        Inventory customGUI = Bukkit.createInventory(p,slots,name);

        l.remove(new SlotGetters(plugin).getTeamBlock(new SlotGetters(plugin).getPlayerTeam(p)));

        for(int i = 0; i < l.size(); i++){

            ItemStack item = new ItemStack(l.get(i));
            customGUI.setItem(i, item);
        }

        p.openInventory(customGUI);
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        SlotGetters s = new SlotGetters(plugin);

        if(e.getCurrentItem() == null) return;

        list.remove(new SlotGetters(plugin).getPlayerTeam(p));

        if(e.getView().getTitle().equalsIgnoreCase("DebtCollector")){

            e.setCancelled(true);

            if(e.getCurrentItem().getType() == Material.RED_CONCRETE){

                if(!plugin.getConfig().contains("Teams.RedTeam.Gold")) return;

                Player ep = new RedTeam(plugin).getPlayer();

                int gold = plugin.getConfig().getInt("Teams.RedTeam.Gold");
                int ogg = plugin.getConfig().getInt("Teams."+new SlotGetters(plugin).getPlayerTeam(p)+".Gold");

                if(gold > 4){

                    ep.sendMessage("You owe "+p.getName()+" 5 Gold, you have 10 seconds to counter this debt");
                    new States(plugin).addDue(ep, States.Dues.DEBT);
                    timer = new BukkitRunnable() {
                        @Override
                        public void run() {

                            if(new States(plugin).hasDebt(ep)){
                                plugin.getConfig().set("Teams.RedTeam.Gold",gold - 5);
                                plugin.getConfig().set("Teams."+new SlotGetters(plugin).getPlayerTeam(p)+".Gold", ogg + 5);
                                plugin.saveConfig();

                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                        "scoreboard players remove "+ep.getName() +" Gold "+ 5);
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                        "scoreboard players add "+p.getName() +" Gold "+ 5);
                                ep.sendMessage("5 Gold had been deducted from your balance");
                            }
                        }
                    };
                    timer.runTaskLater(plugin, 10*20L);

                }else{

                    //give property plus any leftover cash
                }

            }
            if(e.getCurrentItem().getType() == Material.BLUE_CONCRETE){

                if(!plugin.getConfig().contains("Teams.BlueTeam.Gold")) return;

                Player ep = new BlueTeam(plugin).getPlayer();

                int gold = plugin.getConfig().getInt("Teams.BlueTeam.Gold");
                int ogg = plugin.getConfig().getInt("Teams."+new SlotGetters(plugin).getPlayerTeam(p)+".Gold");

                if(gold > 4){

                    ep.sendMessage("You owe "+p.getName()+" 5 Gold, you have 10 seconds to counter this debt");
                    new States(plugin).addDue(ep, States.Dues.DEBT);
                    timer = new BukkitRunnable() {
                        @Override
                        public void run() {

                            if(new States(plugin).hasDebt(ep)){
                                plugin.getConfig().set("Teams.BlueTeam.Gold",gold - 5);
                                plugin.getConfig().set("Teams."+new SlotGetters(plugin).getPlayerTeam(p)+".Gold", ogg + 5);
                                plugin.saveConfig();

                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                        "scoreboard players remove "+ep.getName() +" Gold "+ 5);
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                        "scoreboard players add "+p.getName() +" Gold "+ 5);

                                ep.sendMessage("5 Gold had been deducted from your balance");

                            }
                        }
                    };
                    timer.runTaskLater(plugin, 10*20L);

                }else{

                    //give property plus any leftover cash
                }

            }
            if(e.getCurrentItem().getType() == Material.YELLOW_CONCRETE){

                if(!plugin.getConfig().contains("Teams.YellowTeam.Gold")) return;
                Player ep = new BlueTeam(plugin).getPlayer();

                int gold = plugin.getConfig().getInt("Teams.YellowTeam.Gold");
                int ogg = plugin.getConfig().getInt("Teams."+new SlotGetters(plugin).getPlayerTeam(p)+".Gold");

                if(gold > 4){

                    ep.sendMessage("You owe "+p.getName()+" 5 Gold, you have 10 seconds to counter this debt");
                    new States(plugin).addDue(ep, States.Dues.DEBT);
                    timer = new BukkitRunnable() {
                        @Override
                        public void run() {

                            if(new States(plugin).hasDebt(ep)){
                                plugin.getConfig().set("Teams.YellowTeam.Gold",gold - 5);
                                plugin.getConfig().set("Teams."+new SlotGetters(plugin).getPlayerTeam(p)+".Gold", ogg + 5);
                                plugin.saveConfig();

                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                        "scoreboard players remove "+ep.getName() +" Gold "+ 5);
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                        "scoreboard players add "+p.getName() +" Gold "+ 5);

                                ep.sendMessage("5 Gold had been deducted from your balance");

                            }
                        }
                    };
                    timer.runTaskLater(plugin, 10*20L);

                }else{

                    //give property plus any leftover cash
                }

            }
            if(e.getCurrentItem().getType() == Material.GREEN_CONCRETE){

                if(!plugin.getConfig().contains("Teams.GreenTeam.Gold")) return;
                Player ep = new BlueTeam(plugin).getPlayer();

                int gold = plugin.getConfig().getInt("Teams.GreenTeam.Gold");
                int ogg = plugin.getConfig().getInt("Teams."+new SlotGetters(plugin).getPlayerTeam(p)+".Gold");

                if(gold > 4){

                    ep.sendMessage("You owe "+p.getName()+" 5 Gold, you have 10 seconds to counter this debt");
                    new States(plugin).addDue(ep, States.Dues.DEBT);
                    timer = new BukkitRunnable() {
                        @Override
                        public void run() {

                            if(new States(plugin).hasDebt(ep)){
                                plugin.getConfig().set("Teams.GreenTeam.Gold",gold - 5);
                                plugin.getConfig().set("Teams."+new SlotGetters(plugin).getPlayerTeam(p)+".Gold", ogg + 5);
                                plugin.saveConfig();

                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                        "scoreboard players remove "+ep.getName() +" Gold "+ 5);
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                                        "scoreboard players add "+p.getName() +" Gold "+ 5);

                                ep.sendMessage("5 Gold had been deducted from your balance");

                            }
                        }
                    };
                    timer.runTaskLater(plugin, 10*20L);

                }else{

                    //give property plus any leftover cash
                }

            }
            e.getWhoClicked().closeInventory();
        }
        if(e.getView().getTitle().equalsIgnoreCase("Set OverCharge Selection")){

            Material m = e.getCurrentItem().getType();
            ArrayList<Cards.Set> setList = new SlotGetters(plugin).getTeamSets(new SlotGetters(plugin).getTeamFromBlock(m));
            ArrayList<ItemStack> itemList = new ArrayList<>();

            for (int i = 0; i <= 8; i++) {

                ItemStack item = new ItemStack(Material.WHITE_CONCRETE);
                ItemMeta itemM = item.getItemMeta();
                itemM.setDisplayName(setList.get(i).name());
                itemM.setLore(new ArrayList<>(Arrays.asList(s.getTeamFromBlock(m))));
                item.setItemMeta(itemM);

                itemList.add(item);
            }

            open2ndGUI(p,itemList,"Set OverCharge");
        }
        if(e.getView().getTitle().equalsIgnoreCase("Set OverCharge")){

            ItemStack item = e.getCurrentItem();
            String team = item.getItemMeta().getLore().get(0);
            Cards.Set set = Cards.Set.getSetFromId(item.getItemMeta().getDisplayName());
            int column = e.getSlot() + 1;
            int newColumn = 0;

            ArrayList<String> cardNameList = (ArrayList<String>) plugin.getConfig().getList("Teams."+team+".Property.Column"+column+"Cards");
            ArrayList<Cards.Card> cardList = new ArrayList<>();
            ArrayList<String> empty = new ArrayList<>();

            plugin.getConfig().set("Teams."+team+".Property.Column"+column+"Cards",empty);
            plugin.saveConfig();

            for(String cardName : cardNameList){

                cardList.add(Cards.Card.getCardFromId(cardName));
            }

            ArrayList<Cards.Set> playerSets = s.getTeamSets(s.getPlayerTeam(p));
            if(playerSets.contains(set)){

                newColumn = playerSets.indexOf(set) + 1;

                ArrayList<String> oldCardList = (ArrayList<String>) plugin.getConfig().getList("Teams."+s.getPlayerTeam(p)+".Property.Column"+newColumn+"Cards");
                ArrayList<Cards.Card> finalCardList = new ArrayList<>();
                ArrayList<String> finalList = new ArrayList<>();

                for(String cardN : oldCardList) finalCardList.add(Cards.Card.getCardFromId(cardN));
                finalCardList.addAll(cardList);
                for(Cards.Card card : finalCardList) finalList.add(card.name());

                plugin.getConfig().set("Teams."+s.getPlayerTeam(p)+".Property.Column"+newColumn+"Cards",finalList);
                plugin.saveConfig();
            }
            else{

                newColumn = playerSets.indexOf(Cards.Set.EMPTY) + 1;

                plugin.getConfig().set("Teams."+s.getPlayerTeam(p)+".Property.Column"+newColumn+"Cards",cardNameList);
                plugin.saveConfig();
            }

        }
        e.setCancelled(true);
    }
    public void open2ndGUI(Player p, ArrayList<ItemStack> l, String name){

        Inventory gui = Bukkit.createInventory(p,9,name);

        for(int i = 0; i < l.size(); i++){

            gui.setItem(i, l.get(i));
        }
        p.openInventory(gui);
    }
}
