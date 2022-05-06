package model;

/*
 Represents a pellet (Food that PacMan eats, which also determines the score)
 */

import java.awt.*;

public class Pellet extends LevelObject {

    public static final char pelletSymbol = '.';
    public static final int radius = 10;
    public static final Color COLOR = new Color(253, 214, 57);

    // Constructs a Pellet
    // MODIFIES: this
    // EFFECTS: Constructs a pellet at x,y coordinates and . as a symbol
    public Pellet(int x, int y, char c) {
        super(x, y, c);
    }

    // Indicates whether a pellet is eaten by PacMan
    // EFFECTS: returns true if PacMan's x and y positions are the same as the Pellets x and y positions
    //          otherwise returns false
    public Boolean isPelletEaten(LevelObject pac) {

        if (getPosX() + radius / 2 >= pac.getPosX() - PacMan.radius / 2 && getPosX()
                + radius / 2 <= pac.getPosX() + PacMan.radius / 2 && getPosY()
                + radius / 2 >= pac.getPosY() - PacMan.radius / 2
                && getPosY() + radius / 2 <= pac.getPosY() + PacMan.radius / 2) {
            return true;
        } else {
            return false;
        }
    }
}