package me.cipher.cardwars.uitls;

import me.cipher.cardwars.CardWars;
import me.cipher.cardwars.cards.Cards;
import me.cipher.cardwars.stations.BlueTeam;
import me.cipher.cardwars.stations.GreenTeam;
import me.cipher.cardwars.stations.RedTeam;
import me.cipher.cardwars.stations.YellowTeam;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.ArrayList;

public class SlotGetters {
    private final CardWars plugin;

    public SlotGetters(CardWars plugin) {
        this.plugin = plugin;
    }

    // Gets the next available slot for a card based on its group and team.
    public Slot getAvailableSlot(Cards.Card card, ArrayList<Cards.Group> groups, String teamId) {
        Cards cs = new Cards();
        Cards.Group cardGroup = cs.getGroup(card);
        int groupIndex = groups.indexOf(cardGroup);

        return (groupIndex != -1) ? new Slot(getNextRow(groupIndex, teamId), groupIndex) : null;
    }

    // Gets the next available row for a card within a group.
    private int getNextRow(int groupIndex, String teamId) {
        String groupPath = "Teams." + teamId + ".Properties.Groups.Group" + (groupIndex + 1);

        if (plugin.getConfig().contains(groupPath)) {
            ArrayList<Cards.Card> cards = (ArrayList<Cards.Card>) plugin.getConfig().getList(groupPath);
            return cards.size() + 1;
        }

        return 0;
    }

    // Gets all groups associated with a team.
    public ArrayList<Cards.Group> getTeamGroups(String teamId) {
        ArrayList<Cards.Group> groups = null;

        if (teamId.equals("RedTeam")) groups = new RedTeam(plugin).getAllGroups();
        if (teamId.equals("BlueTeam")) groups = new BlueTeam(plugin).getAllGroups();
        if (teamId.equals("YellowTeam")) groups = new YellowTeam(plugin).getAllGroups();
        if (teamId.equals("GreenTeam")) groups = new GreenTeam(plugin).getAllGroups();

        return groups;
    }

    // Gets the location of a slot within a team.
    public Location slotLocation(String teamId, Slot slot) {
        Location location = null;

        if (teamId.equals("RedTeam")) location = new RedTeam(plugin).translateCords(slot);
        if (teamId.equals("BlueTeam")) location = new BlueTeam(plugin).translateCords(slot);
        if (teamId.equals("YellowTeam")) location = new YellowTeam(plugin).translateCords(slot);
        if (teamId.equals("GreenTeam")) location = new GreenTeam(plugin).translateCords(slot);

        return location;
    }

    // Gets the direction a team's property is facing.
    public BlockFace getDirection(String teamId) {
        BlockFace direction = null;

        if (teamId.equals("RedTeam")) direction = new RedTeam(plugin).getPropertyDirection();
        if (teamId.equals("BlueTeam")) direction = new BlueTeam(plugin).getPropertyDirection();
        if (teamId.equals("YellowTeam")) direction = new YellowTeam(plugin).getPropertyDirection();
        if (teamId.equals("GreenTeam")) direction = new GreenTeam(plugin).getPropertyDirection();

        return direction;
    }
}
