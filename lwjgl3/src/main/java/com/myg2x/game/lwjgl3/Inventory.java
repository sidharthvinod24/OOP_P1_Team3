package com.myg2x.game.lwjgl3;

import java.util.ArrayList;

public class Inventory {
    private final ArrayList<MathOperatorObject> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(MathOperatorObject item) {
        if(item != null) {

            items.add(item);
        }
    }

    public ArrayList<MathOperatorObject> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }
}

