package me.cipher.cardwars.cards;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Draw {

    public ArrayList<MapMeta> AllCards = new ArrayList<>();

    public static void main(String[] args) {
        Draw draw = new Draw();
        draw.initializeCards();
    }

    public void initializeCards() {
        addCard("Gold", "1_GOLD", "", "", 20, 8);
        addCard("Gold", "2_GOLD", "", "", 18, 6);
        addCard("Gold", "5_GOLD", "", "", 19, 4);
        addCard("Gold", "10_GOLD", "", "", 17, 2);
        addCard("Gold", "20_GOLD", "", "", 16, 1);
        addCard("Reaction Card", "DIAMOND_CARD", "", "", 21, 1);

        addCard("Action Card", "Debt_Collector", "", "", 3, 3);
        addCard("Reaction Card", "Just_Say_No", "", "", 9, 3);
        addCard("Action Card", "Birthday_Present", "", "", 1, 3);
        addCard("Action Card", "Set_OverCharge", "", "", 1, 2);
        addCard("Action Card", "OverCharge", "", "", 1, 3);
        addCard("Action Card", "Swap", "", "", 1, 3);
        addCard("Action Card", "Draw_2", "", "", 1, 10);
        addCard("Action Addition Card", "Double_Rent", "", "", 1, 2);

        addCard("Property Addition Card", "Hotel", "", "", 1, 3);
        addCard("Property Addition Card", "House", "", "", 1, 3);

        addCard("Rent Card", "A_E", "A", "E", 1, 3);
        addCard("Rent Card", "B_H", "B", "H", 1, 3);
        addCard("Rent Card", "C_F", "C", "F", 1, 3);
        addCard("Rent Card", "D_G", "D", "G", 1, 3);
        addCard("Rent Card", "F_I", "F", "I", 1, 3);

        addPropertyCards("A", 5);
        addPropertyCards("B", 5);
        addPropertyCards("C", 5);
        addPropertyCards("D", 4);
        addPropertyCards("E", 4);
        addPropertyCards("F", 4);
        addPropertyCards("G", 3);
        addPropertyCards("H", 3);
        addPropertyCards("I", 3);

        addCard("Rainbow Card", "Rainbow_Card", "", "", 1, 1);

        addWildCards("A_E", "A", "E", 1, 1);
        addWildCards("B_H", "B", "H", 1, 1);
        addWildCards("C_F", "C", "F", 1, 1);
        addWildCards("D_G", "D", "G", 1, 1);
        addWildCards("F_I", "F", "I", 1, 1);

        addCard("Joke Card", "Joker_Card", "", "", 1, 2);
    }

    private void addPropertyCards(String prefix, int count) {
        for (int i = 1; i <= count; i++) {
            addCard("Property Card", prefix + i, "", "", 1, 1);
        }
    }

    private void addWildCards(String codeName, String setRent1, String setRent2, int id, int count) {
        for (int i = 0; i < count; i++) {
            addCard("WildCard", codeName, setRent1, setRent2, id, 1);
        }
    }

    public void addCard(String cardType, String codeName, String setRent1, String setRent2, int id, int count) {
        for (int i = 0; i < count; i++) {
            AllCards.add(createCard(cardType, codeName, setRent1, setRent2, id));
        }
    }

    public MapMeta createCard(String cardType, String codeName, String setRent1, String setRent2, int id) {
        ItemStack item = new ItemStack(Material.FILLED_MAP);
        MapMeta mm = (MapMeta) item.getItemMeta();

        mm.setLore(new ArrayList<>(Arrays.asList(cardType, codeName, setRent1, setRent2)));
        mm.setMapId(id);

        item.setItemMeta(mm);

        return mm;
    }
}