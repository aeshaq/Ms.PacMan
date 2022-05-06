package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

/*
The Level in which the game is played
Structure influenced by SpaceInvaderBase B02
 */

/*public class Level {
    private static final String JSON_STORE = "./data/PacManGame.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public static final int width = 36;
    public static final int height = 16;
    public static final char[][] map = new char[width][height];

    private PacManGame2 game;

    // Constructs a Level
    // Effects: updates this with game to be displayed
    public Level(PacManGame2 pmg) {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        game = pmg;
    }

    // Builds the map of the level
    // MODIFIES: this
    // EFFECTS: clears the map , assigns the level borders, walls, pellets, pac ,ghosts to a place on the map
    //          then draws the map onto the console
    public void buildMap() {
        clearMap();
        buildBorder();
        buildWalls();
        placePelletsInitial();
        placePac();
        placeGhosts();
    }

    // Clears all items on the map
    // (Built this method because of a bug in Junit that doesn't make the map initially cleared)
    // MODIFIES: this
    // EFFECTS: Assigns every space on map to 0
    public void clearMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[j][i] = 0;
            }
        }
    }

    // Updates the level
    // MODIFIES: this
    // EFFECTS: prints the score, cleans up moved symbols, updates pellets, Pacman, ghosts, caspers, power positions
    //          on the map and then redraws the map and prints number of powers and caspers
    public void update() {
        System.out.println("Score: " + game.getScore());
        cleanup();
        placePellets();
        placePac();
        placeGhosts();
        placeCaspers();
        placePowers();
        drawAll();
        System.out.println("You have " + game.getInventory().getPowers().size() + " Powers");
        System.out.println("You have " + game.getInventory().getGhosts().size() + " Caspers");
    }

    // Draws the map
    // EFFECTS: prints everything on map to the console
    public void drawAll() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(map[j][i]);
            }
            System.out.println();
        }
    }

    // Places the borders on the map
    // MODIFIES: this
    // EFFECTS: Places border pieces represented by - and | around the border of the map
    public void buildBorder() {
        for (int i = 0; i <= width - 1; i++) {
            placeChar(i, 0, '-');
        }
        for (int i = 0; i <= width - 1; i++) {
            placeChar(i, height - 1, '-');
        }
        for (int i = 1; i < height - 1; i++) {
            placeChar(0, i, '|');
        }
        for (int i = 1; i < height - 1; i++) {
            placeChar(width - 1, i, '|');
        }
    }

    // Places the walls on the map
    // MODIFIES: this
    // EFFECTS: Places walls around the map represented by +
    public void buildWalls() {
        for (int i = 1; i < height / 3; i++) {
            placeChar(width / 2, i, '+');
        }
        for (int i = height - 2; i > height - height / 3 - 1; i--) {
            placeChar(width / 2, i, '+');
        }

        for (int i = 0; i < width / 4; i++) {
            placeChar(width / 2 - width / 8 + i, height / 2 + 1, '+');
            placeChar(width / 2 - width / 8 + i, height / 2 - 1, '+');
        }

        for (int i = 0; i < width / 6; i++) {
            placeChar(width / 5 + i, height / 6, '+');
            placeChar(width - width / 5 - i, height / 6, '+');
            placeChar(width / 5 + i, 3 * height / 7 + width / 6, '+');
            placeChar(width - width / 5 - i, 3 * height / 7 + width / 6, '+');
        }

        for (int i = 0; i < height / 3; i++) {
            placeChar(width / 5, height / 6 + i, '+');
            placeChar(width - width / 5, height / 6 + i, '+');
            placeChar(width / 5, height / 2 + i, '+');
            placeChar(width - width / 5, height / 2 + i, '+');
        }
    }

    // Place pellets on the map
    // MODIFIES: this
    // EFFECTS: Places all pellets p on the map
    public void placePellets() {
        for (Pellet p : game.getPellets()) {
            placePellet(p);
        }
    }

    // Place pellet on the map
    // MODIFIES: this
    // EFFECTS: Places p on the map at posX and posY of p
    public void placePellet(Pellet p) {
        map[p.getPosX()][p.getPosY()] = p.getSymbol();
    }

    public void placePelletsInitial() {
        for (Pellet p : game.getPellets()) {
            placePelletInitial(p);
        }
    }

    // Place pellet on the map initially
    // MODIFIES: this and p
    // EFFECTS: changes x and y coordinates of p to the first empty space on the map (represented by 0) and
    //          places it on the map
    public void placePelletInitial(Pellet p) {
        while (true) {
            if (map[p.getPosX()][p.getPosY()] == 0) {
                map[p.getPosX()][p.getPosY()] = p.getSymbol();
                break;
            } else if (p.getPosX() < width - 1) {
                p.setPosX(p.getPosX() + 1);
            } else if (p.getPosY() < height - 1) {
                p.setPosX(0);
                p.setPosY(p.getPosY() + 1);
            } else if (p.getPosX() >= width - 1 || p.getPosY() >= height - 1) {
                break;
            }
        }
    }

    // Places a char on the map
    // MODIFIES: this
    // EFFECTS: place char c on the map at x and y
    public void placeChar(int x, int y, char c) {
        map[x][y] = c;
    }

    // Places a level object on the map
    // MODIFIES: this
    // EFFECTS: place LevelObject lo on the map at the x and y of lo
    public void placeLevelObject(LevelObject lo) {
        map[lo.getPosX()][lo.getPosY()] = lo.getSymbol();
    }

    // Places Pacman on the map
    // MODIFIES: this
    // EFFECTS: place PacMan on the map at the x and y coordinates of PacMan
    public void placePac() {
        placeLevelObject(game.getPac());
    }

    // Places all ghosts g on the map
    // MODIFIES: this
    // EFFECTS: place ghosts on the map
    public void placeGhosts() {
        for (Ghost g : game.getGhosts()) {
            placeGhost(g);
        }
    }

    // Places ghost on the map
    // MODIFIES: this
    // EFFECTS: place ghost g on the map
    public void placeGhost(Ghost g) {
        placeLevelObject(g);
    }

    // cleans up the map
    // MODIFIES: this
    // EFFECTS: Removes PacMan, Ghosts, caspers, zeroes and replaces them with '_'
    //          this method is used to empty the map of duplicated characters
    public void cleanup() {
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (map[i][j] == '&' || map[i][j] == '>' || map[i][j] == '<' || map[i][j] == '^'
                        || map[i][j] == 'v' || map[i][j] == '0' || map[i][j] == '$' || map[i][j] == '#'
                        || map[i][j] == (char) 0) {
                    placeChar(i, j, '_');
                }
            }
        }
    }

    // Place powers on the map
    // MODIFIES: this
    // EFFECTS: Places all powers p in game.getPowers() on the map
    public void placePowers() {
        for (Power p : game.getPowers()) {
            p.placePower();
        }
    }

    // Place caspers on the map
    // MODIFIES: this
    // EFFECTS: Places all ghosts g in game.getCaspers() on the map
    public void placeCaspers() {
        for (Ghost g : game.getCaspers()) {
            placeGhost(g);
        }
    }

    public void guide() {
        System.out.println("Enter w,a,s,d to move Pacman, eat all the pellets and avoid the ghosts!");
        System.out.println("Eat * to get a power, use power with p, power will make ghosts edible for 10 moves");
        System.out.println("If you eat a ghost, you can use g to summon the eaten ghost as a friendly ghost (casper!)");
        System.out.println("that will eat pellets for 20 moves. After 20 moves the ghost will respawn as an enemy");
        System.out.println("Type save at any time to save your game, load to load the previously saved game");
        System.out.println("and quit to quit the game");
    }

    public void processCommand(String s) {
        if (s.equals("help")) {
            guide();
        } else if (s.equals("new")) {
            buildMap();
            update();
        } else if (s.equals("save")) {
            saveWorkRoom();
        } else if (s.equals("load")) {
            loadWorkRoom();
            clearMap();
            buildMap();
            update();

        } else if (s.equals("quit")) {
            game.setIsGameOver(true);
        } else {
            game.update(s);
            update();

        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded " + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveWorkRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved " + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

} */
