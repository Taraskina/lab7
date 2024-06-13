package org.example.entity;

public enum Color {
    GREEN, RED, BLUE, YELLOW;

    public static Color choose(String s) {
        Color clr = null;
        for (Color value : Color.values()) {
            if (value.name().equals(s)) {
                clr = value;
            }
        }
        return clr;
    }
}
