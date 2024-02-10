package me.cipher.cardwars.testingkit;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.uitls.SlotGetters;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Visuals implements CommandExecutor {

    private final CardWars plugin;

    public Visuals(CardWars plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command c, String s, String[] args) {

        if(cs instanceof Player) {

            Player p = (Player) cs;

            if(s.equalsIgnoreCase("setProperty")){

                if (args.length == 2) {

                    int mapId = Integer.parseInt(args[0]);
                    String teamId = args[1];
                    Location l = p.getTargetBlock(null,5).getLocation();
                    BlockFace d = new SlotGetters(plugin).getDirection(teamId);

                    l.getBlock().setType(Material.AIR);

                    ItemFrame itemFrame = l.getWorld().spawn(l, ItemFrame.class);
                    itemFrame.setFacingDirection(d);

                    ItemStack mapItem = new ItemStack(Material.FILLED_MAP);
                    mapItem.setDurability((short) mapId);

                }else {

                    p.sendMessage("Usage: /setproperty <Id> <Team>");
                }
            }
        }
        return false;
    }
}
