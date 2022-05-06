package ui;

import model.Event;
import model.EventLog;
import model.PacManGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends JFrame {

    private static final int INTERVAL = 10;
    private PacManGame game;
    private GamePanel gp;
    private InfoPanel ip;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/PacManGame.json";

    // Constructs main window
    // effects: sets up window in which Space Invaders game will be played
    public Main() {
        super("Pac Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        game = new PacManGame();
        gp = new GamePanel(game);
        ip = new InfoPanel(game);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        add(gp);
        add(ip, BorderLayout.NORTH);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();

        setVisible(true);
        addTimer();
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, ae -> {
            game.update();
            gp.repaint();
            ip.update();
        });

        t.start();
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            keyPressedExtended(e.getKeyCode());
        }
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWorkRoom() {
        try {
            game.changeGame(jsonReader.read());
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

    // EFFECTS: Controls the game, uses power if any in inventory if p is pressed, summons ghosts from inventory
    // if g is pressed, saves game with v, loads game with l, quits game with q, else moves pacman
    public void keyPressedExtended(int keyCode) {
        if (keyCode == KeyEvent.VK_P) {
            game.usePower("p");
        } else if (keyCode == KeyEvent.VK_G) {
            game.allyGhost("g");
        } else if (keyCode == KeyEvent.VK_V) {
            saveWorkRoom(); // v
        } else if (keyCode == KeyEvent.VK_L) {
            loadWorkRoom(); // l
        } else if (keyCode == KeyEvent.VK_Q) {
            quitGame(); // q
        } else {
            gp.pacControl(keyCode);
        }

    }

    // EFFETCS: Quits the game
    public void quitGame() {
        for (Event e: EventLog.getInstance()) {
            System.out.println(e.getDate() + " " +  e.getDescription());
        }
        System.exit(0);
    }



    public static void main(String[] args) {
        new Main();
    }
}
