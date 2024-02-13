package me.cipher.cardwars.cards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;



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
    public enum Set  {
        A, B, C, D, E, F, G, H, I;

    }

    public enum Card   {


        A1(2,Set.A), A2(2,Set.A), A3(2,Set.A), A4(2,Set.A), A5(2,Set.A),
        B1(3,Set.B), B2(3,Set.B), B3(3,Set.B), B4(3,Set.B), B5(3,Set.B),
        C1(5,Set.C), C2(5,Set.C), C3(5,Set.C), C4(5,Set.C), C5(5,Set.C),

        D1(1,Set.D), D2(1,Set.D), D3(1,Set.D), D4(1,Set.D),
        E1(2,Set.E), E2(2,Set.E), E3(2,Set.E), E4(2,Set.E),
        F1(5,Set.F), F2(5,Set.F), F3(5,Set.F), F4(5,Set.F),
        G1(1,Set.G), G2(1,Set.G), G3(1,Set.G),
        H1(2,Set.H), H2(2,Set.H), H3(2,Set.H),
        I1(5,Set.I), I2(5,Set.I), I3(5,Set.I),

        // Placeholder card
        PLACEHOLDER(0,null),
        ;
        private int propertyPrice;
        private Set set;


        Card(int propertyPrice,Set set) {

            this.propertyPrice = propertyPrice;
            this.set = set;


        }

        public int getPropertyPrice() {
            return propertyPrice;
        }

        public Set getSet() {
            return set;
        }
        public static Set getSet(Card card){
            return card.getSet();

        }
    }




    // Enumeration for sets of cards





    // Enumeration for groups of cards
    public enum Group {
        EMPTY(0),
        ADG(1),
        BEH(2),
        CFI(3),;
        private int GroupNumber;

        Group(int groupNumber) {
            GroupNumber = groupNumber;
        }

        public int getGroupNumber() {
            return GroupNumber;
        }
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
        if (g.ordinal() <= Group.ADG.ordinal()) {
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
