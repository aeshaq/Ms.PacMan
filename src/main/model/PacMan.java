package model;

import ui.GamePanel;

import java.awt.*;

/*
 Model for Mr.PacMan (Player)
 */

public class PacMan extends LevelObject {

    public static final char leftFace = '>';
    public static final char rightFace = '<';
    public static final char upFace = 'v';
    public static final char downFace = '^';
    public static final int radius = 30;
    public static final Color COLOR = new Color(253, 214, 57);
    public static final int speed = 3;

    private int direction; // 0 up, 1 down, 2 right, 3 left

    // Constructs a PacMan
    // EFFECTS: Constructs a PacMan at x,y coordinates facing left
    public PacMan(int x, int y, char c) {
        super(x, y, c);
        direction = 2;
    }

    public void move() {
        switch (direction) {
            case 0:
                moveUp();
                break;
            case 1:
                moveDown();
                break;
            case 2:
                moveRight();
                break;
            case 3:
                moveLeft();
                break;
        }

        handleBoundary();
    }

    public void setDirection(int dir) {
        direction = dir;
    }

   /* // Moves PacMan
    // REQUIRES: s has to be one of 'w','s','a','d'
    // MODIFIES: this
    // EFFECTS: Depending on the input it calls the relevant helper move methods to move PacMan
    public void move(String s) {
        switch (s) {
            case "w":
                moveUp();
                break;
            case "s":
                moveDown();
                break;
            case "a":
                moveLeft();
                break;
            case "d":
                moveRight();
                break;
        }

        handleBoundary();
    } */

    // Moves PacMan to the Right
    // MODIFIES: this
    // EFFECTS: Moves PacMan to the right one space and changes the symbol to <
    private void moveRight() {
        setSymbol(rightFace);
        setPosX(getPosX() + speed);

        if (isTouchWall(PacManGame.verticalWalls, PacManGame.horizontalWalls, radius)) {
            setPosX(getPosX() - speed);
        }
    }

    // Moves PacMan to the Left
    // MODIFIES: this
    // EFFECTS: Moves PacMan to the right one space and changes the symbol to >
    private void moveLeft() {
        setSymbol(leftFace);
        setPosX(getPosX() - speed);

        if (isTouchWall(PacManGame.verticalWalls, PacManGame.horizontalWalls, radius)) {
            setPosX(getPosX() + speed);
        }
    }

    // Moves PacMan Up
    // MODIFIES: this
    // EFFECTS: Moves PacMan to the right one space and changes the symbol to v
    private void moveUp() {
        setSymbol(upFace);
        setPosY(getPosY() - speed);

        if (isTouchWall(PacManGame.verticalWalls, PacManGame.horizontalWalls, radius)) {
            setPosY(getPosY() + speed);
        }
    }

    // Moves PacMan Down
    // MODIFIES: this
    // EFFECTS: Moves PacMan to the right one space and changes the symbol to ^
    private void moveDown() {
        setSymbol(downFace);
        setPosY(getPosY() + speed);

        if (isTouchWall(PacManGame.verticalWalls, PacManGame.horizontalWalls, radius)) {
            setPosY(getPosY() - speed);
        }
    }

    // Constrains PacMan so that it doesn't leave the boundaries of the game
    // MODIFIES: this
    // EFFECTS: if PacMan moves outside the game boundaries, it returns it inside
    public void handleBoundary() {
        if (getPosX() < radius / 2) {
            setPosX(radius / 2);
        } else if (getPosX() > GamePanel.width - radius / 2) {
            setPosX(GamePanel.width - radius / 2);
        }
        if (getPosY() < radius) {
            setPosY(radius);
        } else if (getPosY() > GamePanel.height - radius / 2) {
            setPosY(GamePanel.height - radius / 2);
        }
    }
}
