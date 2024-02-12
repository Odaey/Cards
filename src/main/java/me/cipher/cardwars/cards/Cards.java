package me.cipher.cardwars.cards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

public class Cards {

    {// action cards
    /*public enum ActionCards{
        Deal_Breaker,//steal a compelete set of properties from another player
        Dept_Collector,//force anyplayer to pay you 5M
        Double_The_Rent,//Needs to be played with a rent card
        Forced_Deal,//Swap any property with another player(Cannot be part of a compelete set)
        Its_My_Birthday,//all players give you 2M as a gift
        Just_Say_no,//Use anytime when an action card is played agaonst you
        Pass_GO,//Draw extra 2 cards
        Sly_Deal,//Steal a property from the player of ur choice(Cannot be a part of a full set)
    }*/}


    public enum Card {


        A1(), A2(), A3(), A4(), A5(),
        B1(), B2(), B3(), B4(), B5(),
        C1(), C2(), C3(), C4(), C5(),

        D1(), D2(), D3(), D4(),
        E1(), E2(), E3(), E4(),
        F1(), F2(), F3(), F4(),

        G1(), G2(), G3(),
        H1(), H2(), H3(),
        I1(), I2(), I3(),

        // Placeholder card
        PLACEHOLDER,
        ;
        private int propertyPrice;

        Card(int propteryPrice) {
            this.propertyPrice = propteryPrice;
        }

        public int getPropertyPrice() {
            return propertyPrice;
        }
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
    public int getRentPrice(Card c){

        int i = 0;



        return i;
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
            return id >= 15 && id <= 100;
        }
        return false;
    }

    // Get the Card enum based on MapMeta
    public Card getCard(MapMeta m) {
        int id = m.getMapView().getId();
        if (id >= 14 && id <= 100) {
            // Calculate the Card enum based on the map ID
            return Card.values()[id - 14];
        }
        return null;
    }
}
