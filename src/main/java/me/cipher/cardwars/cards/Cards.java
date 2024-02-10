package me.cipher.cardwars.cards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

public class Cards {

    // action cards
    public enum ActionCards{
        Deal_Breaker(2),//steal a compelete set of properties from another player
        Dept_Collector(3),//force anyplayer to pay you 5M
        Double_The_Rent(2),//Needs to be played with a rent card
        Forced_Deal(3),//Swap any property with another player(Cannot be part of a compelete set)
        Its_My_Birthday(3),//all players give you 2M as a gift
        Just_Say_no(3),//Use anytime when an action card is played agaonst you
        Pass_GO(10),//Draw extra 2 cards
        Sly_Deal(3)//Steal a property from the player of ur choice(Cannot be a part of a full set)



    }

    public enum Card {
        // jeddah
        alwaha_school





/*        FORTS, TOWERS, VILLAGERS, KINGDOM, ARMY, KNIGHTS,
        KING, QUEEN, PRINCE, PRINCESS, SQUIRE,
       RUNE, TREASURE, CROWN, ROYAL_STAFF, THRONE,

         Warrior cards
       NINJA, MERCENARY, HITMAN, SHADOWREAPER,
       DUELIST, HERO, BATTLEFRONT, MECHA, HIGHLANDER,
        WIZARD, WITCH, ORACLE, WARLOCK, JEST, ENCHANTER,*/

        // Placeholder card
        PLACEHOLDER,
    }

    // Enumeration for sets of cards
    public enum Set {
        ROYAL,
        WARRIOR,

    }

    // Enumeration for groups of cards
    public enum Group {
        EMPTY,
        NATION, ROYAL_FAMILY, HEIRLOOM,
        ASSASSINS, SPECIAL_FORCES, MAGES
    }

    // Get the group of a card
    public Group getGroup(Card c) {
        if (c.ordinal() <= Card.PLACEHOLDER.ordinal()) {
            // Divide ordinal value by 6 to determine the group
            return Group.values()[c.ordinal() / 6];
        }
        return Group.EMPTY;
    }

    // Get the set of a group
    public Set getSet(Group g) {
        if (g.ordinal() <= Group.MAGES.ordinal()) {
            // Divide ordinal value by 2 to determine the set
            return Set.values()[g.ordinal() / 2];
        }
        return null;
    }

    // Check if an ItemStack is a valid card ya 7ywan
    public boolean isCard(ItemStack i) {
        if (i.getType() == Material.FILLED_MAP) {
            MapMeta mm = (MapMeta) i.getItemMeta();
            int id = mm.getMapView().getId();
            // Check if the map ID falls within the valid card ID range
            return id >= 14 && id <= 45;
        }
        return false;
    }

    // Get the Card enum based on MapMeta
    public Card getCard(MapMeta m) {
        int id = m.getMapView().getId();
        if (id >= 14 && id <= 45) {
            // Calculate the Card enum based on the map ID
            return Card.values()[id - 14];
        }
        return null;
    }
}
