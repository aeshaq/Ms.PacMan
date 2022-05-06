package model;

import java.awt.*;


/*
 Represents the power PacMan can use, when used will make PacMan able to eat ghosts and store them in Inventory
 */
public class Power extends LevelObject {

    public static final char powerSymbol = '*';
    public static final int radius = 30;
    public static final Color COLOR = new Color(224, 197, 218);

    // Constructs a Power
    // MODIFIES: This
    // EFFECTS: Creates a Power with x,y coordinates and * as a symbol
    public Power(int x, int y, char c) {
        super(x, y, c);
    }

    // Places power at x,y coordinates on map
    // MODIFIES: map[][]
    // EFFECTS: places Power at x,y coordinates on map as *
    /*public void placePower() {
        map[getPosX()][getPosY()] = powerSymbol;
    }*/

    // Indicates whether a power is eaten by PacMan
    // EFFECTS: returns true if PacMan's x and y positions are the same as the Powers x and y positions
    //          otherwise returns false
    public Boolean isPowerEaten(LevelObject pac) {
        return getPosX() == pac.getPosX() && getPosY() == pac.getPosY();
    }
}
