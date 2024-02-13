package me.cipher.cardwars.cards;

import java.util.ArrayList;
import java.util.Arrays;

public class Cards {

    public enum Set {
        A(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5))),
        B(new ArrayList<>(Arrays.asList(1, 2, 4, 6, 8))),
        C(new ArrayList<>(Arrays.asList(2, 4, 6, 8, 10))),
        D(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 4))),
        E(new ArrayList<>(Arrays.asList(2, 3, 4, 5, 5))),
        F(new ArrayList<>(Arrays.asList(2, 5, 8, 10, 10))),
        G(new ArrayList<>(Arrays.asList(1, 2, 3, 3, 3))),
        H(new ArrayList<>(Arrays.asList(3, 5, 7, 7, 7))),
        I(new ArrayList<>(Arrays.asList(4, 8, 12, 12, 12))),
        EMPTY(null);

        private final ArrayList<Integer> rent;

        Set(ArrayList<Integer> rent) {
            this.rent = rent;
        }

        public ArrayList<Integer> getRent() {
            return rent;
        }

        public static int getRent(Set set, int i) {
            return set.getRent().get(i - 1);
        }

        public static String getSetID(Set set) {
            return set.name();
        }
        public static Set getSetFromId(String setId) {
            try {
                return valueOf(setId);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public enum Card {
        A1(2, Set.A, 25), A2(2, Set.A, 25), A3(2, Set.A, 25), A4(2, Set.A, 25), A5(2, Set.A, 25),
        B1(3, Set.B, 25), B2(3, Set.B, 25), B3(3, Set.B, 25), B4(3, Set.B, 25), B5(3, Set.B, 25),
        C1(5, Set.C, 25), C2(5, Set.C, 25), C3(5, Set.C, 25), C4(5, Set.C, 25), C5(5, Set.C, 25),
        D1(1, Set.D, 25), D2(1, Set.D, 25), D3(1, Set.D, 25), D4(1, Set.D, 25),
        E1(2, Set.E, 25), E2(2, Set.E, 25), E3(2, Set.E, 25), E4(2, Set.E, 25),
        F1(5, Set.F, 25), F2(5, Set.F, 25), F3(5, Set.F, 25), F4(5, Set.F, 25),
        G1(1, Set.G, 25), G2(1, Set.G, 25), G3(1, Set.G, 25),
        H1(2, Set.H, 25), H2(2, Set.H, 25), H3(2, Set.H, 25),
        I1(5, Set.I, 25), I2(5, Set.I, 25), I3(5, Set.I, 25),
        PLACEHOLDER(0, null, 0);

        private final int propertyPrice;
        private final Set set;

        Card(int propertyPrice, Set set, int displayID) {
            this.propertyPrice = propertyPrice;
            this.set = set;
        }

        public int getPropertyPrice() {
            return propertyPrice;
        }

        public Set getSet() {
            return set;
        }

        public static Set getSet(Card card) {
            return card.getSet();
        }

        public static Card getCardFromId(String cardID) {
            try {
                return valueOf(cardID);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
        public static String getCardID(Card c) {
            return c.name();
        }
    }

    public enum Group {
        EMPTY(0),
        ADG(1),
        BEH(2),
        CFI(3);

        private final int groupNumber;

        Group(int groupNumber) {
            this.groupNumber = groupNumber;
        }

        public int getGroupNumber() {
            return groupNumber;
        }
    }

    public Group getGroup(Card c) {
        if (c.ordinal() <= Card.PLACEHOLDER.ordinal()) {
            return Group.values()[c.ordinal() / 6];
        }
        return Group.EMPTY;
    }

}

