package model;

import ui.GamePanel;

import java.awt.*;
import java.util.Random;

/*
 Represents a ghost (enemy of PacMan)
 */

public class Ghost extends LevelObject {

    public static final char ghostSymbol = '&'; // Symbols are how LevelObjects are represented on the map
    public static final char ghostAfraidSymbol = '$';
    public static final int radius = 30;
    public static final Color COLOR = new Color(220, 30, 5);
    public static final Color afraidColor = new Color(47, 74, 253);
    public static final Color casper = new Color(88, 241, 77);
    public static final Random RND = new Random();


    // Constructs a ghost
    // EFFECTS: a ghost is made with x,y coordinates and & symbol
    public Ghost(int x, int y, char c) {
        super(x, y, c);
    }

    // Gets a random number between 0 and 4
    // https://www.geeksforgeeks.org/java-math-random-method-examples/
    // EFFECTS: returns an int [0,4]
    public int getRandomNum() {
        return RND.nextInt(4);
    }

    // Moves the ghost randomly
    // MODIFIES: this
    // EFFECTS: moves the ghost one space depending on the random value. returns num to help with testing
    public void move(int num) {
        if (num == 0) {
            setPosX(getPosX() + 4);
            if (isTouchWall(PacManGame.verticalWalls, PacManGame.horizontalWalls, radius)) {
                setPosX(getPosX() - 4);
            }
        } else if (num == 1) {
            setPosX(getPosX() - 4);

            if (isTouchWall(PacManGame.verticalWalls, PacManGame.horizontalWalls, radius)) {
                setPosX(getPosX() + 4);
            }

        } else if (num == 2) {
            setPosY(getPosY() + 4);

            if (isTouchWall(PacManGame.verticalWalls, PacManGame.horizontalWalls, radius)) {
                setPosY(getPosY() - 4);
            }
        } else if (num == 3) {
            setPosY(getPosY() - 4);

            if (isTouchWall(PacManGame.verticalWalls, PacManGame.horizontalWalls, radius)) {
                setPosY(getPosY() + 4);
            }
        }
        handleBoundary();
    }


    // Constrains Ghost so that it doesn't leave the boundaries of the game
    // MODIFIES: this
    // EFFECTS: if Ghost moves outside the game boundaries, it returns it inside
    public void handleBoundary() {
        if (getPosX() < radius / 2) {
            setPosX(radius / 2);
        } else if (getPosX() > GamePanel.width - radius / 2) {
            setPosX(GamePanel.width - radius / 2);
        }
        if (getPosY() < radius) {
            setPosY(radius / 2);
        } else if (getPosY() > GamePanel.height - radius / 2) {
            setPosY(GamePanel.height - radius / 2);
        }
    }

    // Determines if the ghost ate PacMan
    // EFFECTS: returns true if PacMan's x and y positions are the same as the ghosts x and y positions
    //          otherwise returns false
    public Boolean ghostAtePac(LevelObject pac) {
        if (getPosX() + radius / 2 >= pac.getPosX() - PacMan.radius / 2 && getPosX()
                + radius / 2 <= pac.getPosX() + PacMan.radius / 2 && getPosY()
                + radius / 2 >= pac.getPosY() - PacMan.radius / 2
                && getPosY() + radius / 2 <= pac.getPosY() + PacMan.radius / 2) {
            return true;
        } else {
            return false;
        }
    }

    // Symbolises that the ghost is now edible by PacMan
    // MODIFIES: this
    // EFFECTS: Sets the ghosts symbol to $
    public void afraid() {
        setSymbol(ghostAfraidSymbol);
    }

    // Symbolises that the ghost is not edible by PacMan
    // MODIFIES: this
    // EFFECTS: Sets the ghosts symbol to &
    public void brave() {
        setSymbol(ghostSymbol);
    }


}
