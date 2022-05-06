package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import ui.GamePanel;

import java.util.ArrayList;
import java.util.List;


/*
Represents a PacMan Game
Structure influenced by SpaceInvaderBase B02
 */

/*public class PacManGame implements Writable {
    public static final int MAX_PELLETS = 410;
    private static final int numGhosts = 4;

    private List<Pellet> pellets;
    private int score;
    private List<Ghost> ghosts;
    private PacMan pac;
    private Boolean isGameOver;
    private List<Power> powerList;
    private Inventory inventory;
    private List<Ghost> caspers; // friendly ghosts who eat pellets
    private int tick;
    private int casperTick;
    private List<Wall> walls;

    // Constructs a PacMan Game
    // EFFECTS: creates empty Lists of pellets,ghosts,powerList, and Caspers. Sets isGameOver to false
    //          , tick to 0, casperTick to 0, calls setUp for further setup and initializes inventory
    public PacManGame() {
        pellets = new ArrayList<>();
        ghosts = new ArrayList<>();
        isGameOver = false;
        powerList = new ArrayList<>();
        inventory = new Inventory();
        caspers = new ArrayList<>();
        tick = 0;
        casperTick = 0;
        setUp();
    }

    // Constructs a PacMan Game
    // EFFECTS: creates List of pellets p, ghosts g ,powerList powers, and Caspers. Sets this.isGameOver to isGameOver
    //          , this.tick to tick, this.casperTick to casperTick, and Inventory inv
    public PacManGame(List<Pellet> p, int score, List<Ghost> g, PacMan pac, Boolean isGameOver, List<Power> powers,
                      Inventory inv, List<Ghost> caspers, int tick, int casperTick) {
        pellets = p;
        this.score = score;
        this.pac = pac;
        ghosts = g;
        this.isGameOver = isGameOver;
        this.powerList = powers;
        inventory = inv;
        this.caspers = caspers;
        this.tick = tick;
        this.casperTick = casperTick;
    }

    // Sets up the game
    // MODIFIES: this
    // EFFECTS: readies pellets and ghosts. Places pac in the start position and sets score to 0.
    public void setUp() {
        addPellets();
        addGhosts();
        pac = new PacMan(Level.width / 2, Level.height / 2, PacMan.leftFace);
        score = 0;
    }

    // Updates game after every move
    // MODIFIES: this
    // EFFECTS: Updates pac, pellets, powers, caspers, ghosts
    public void update(String move) {
        ghostsAtePacOrPacAteGhost();
        pac.move();
        eatPellets(pac);
        moveGhosts();
        updatePowers(getRandNum());
        usePower(move);
        resolveTick();
        casper();
        allyGhost(move);
        checkIsGameOver();
    }

    // Moves all ghosts
    // MODIFIES: this
    // EFFECTS: applies move() to every Ghost g in ghosts
    public void moveGhosts() {
        for (Ghost g : ghosts) {
            g.move(g.getRandomNum());
        }
    }

    // Adds the initial pellets
    // MODIFIES: this
    // EFFECTS: Adds MAX_PELLETS number of Pellets to pellets
    public void addPellets() {
        for (int i = 0; i < MAX_PELLETS; i++) {
            pellets.add(new Pellet(0, 0, Pellet.pelletSymbol));
        }
    }

    // Adds the initial ghosts
    // MODIFIES: this
    // EFFECTS: Adds numGhosts number of Ghosts to ghosts
    public void addGhosts() {
        for (int i = 1; i <= numGhosts; i++) {
            ghosts.add(new Ghost(i, 1, Ghost.ghostSymbol));
        }
    }

    public PacMan getPac() {
        return pac;
    }

    public List<Pellet> getPellets() {
        return pellets;
    }

    public List<Ghost> getGhosts() {
        return ghosts;
    }

    public int getScore() {
        return score;
    }

    // Allows LevelObject to eat pellets
    // MODIFIES: this
    // EFFECTS: if los x and y positions are the same as the pellets x and y positions
    //          it will remove the Pellet p from pellets and add 1 to the score, does this for all p's in pellets
    public void eatPellets(LevelObject lo) {
        List<Pellet> toRemove = new ArrayList<>();
        for (Pellet p : pellets) {
            if (p.isPelletEaten(lo)) {
                toRemove.add(p);
                score += 1;
            }
        }
        pellets.removeAll(toRemove);
    }

    // Checks to see whether game is over or if PacMan ate a ghost (if ghost is set to afraid)
    // MODIFIES: this
    // EFFECTS: if ghosts are set to afraid and ghostAtePac returns true, it will set isGameOver to false
    //          and add the eaten ghost g to inventory and remove it from ghosts. Else if ghosts are not set
    //          to afraid and ghostAtePac returns true it will set isGameOver to true.
    public void ghostsAtePacOrPacAteGhost() {
        List<Ghost> toRemove = new ArrayList<>();
        for (Ghost g : ghosts) {
            if (g.ghostAtePac(pac) && g.getSymbol() == '$') {
                isGameOver = false;
                inventory.eatGhost(g);
                toRemove.add(g);
            } else if (g.ghostAtePac(pac) && g.getSymbol() == '&') {
                isGameOver = true;
            }
        }
        ghosts.removeAll(toRemove);
    }

    // Gets a random number between 0 and 10
    // https://www.geeksforgeeks.org/java-math-random-method-examples/
    // EFFECTS: returns an int [0,10]
    public int getRandNum() {
        int num = (int) (Math.random() * 10);
        return num;
    }

    // Updates Powers
    // MODIFIES: this
    // EFFECTS: Adds a new power to powerList with a chance of 1/10 at one of two positions, chance of being at either
    //          position is 50/50. For every power p in powerList if isPowerEaten returns true adds power to inventory
    //          and removes it from powerList (from map)
    public void updatePowers(int num) {
        List<Power> toRemove = new ArrayList<>();
        if (num == 7 && powerList.size() == 0) {
            num = getRandNum();
            if (num >= 5) {
                Power power = new Power(Level.width - 2, 1, Power.powerSymbol);
                powerList.add(power);
            } else if (num < 5) {
                Power power = new Power(Level.width - 2, Level.height - 2, Power.powerSymbol);
                powerList.add(power);
            }
        }
        for (Power p : powerList) {
            if (p.isPowerEaten(pac)) {
                inventory.eatPower(p);
                toRemove.add(p);
            }
        }
        powerList.removeAll(toRemove);
    }

    public List<Power> getPowers() {
        return powerList;
    }

    // Uses one power
    // REQUIRES: s is "p"
    // MODIFIES: this
    // EFFECTS: if the input is "p", and you have powers in inventory, removes power from inventory
    //          and sets all ghosts g in ghosts to afraid. Sets tick to 10 moves
    public void usePower(String s) {
        if (s.equals("p") && inventory.getPowers().size() != 0) {
            inventory.usePower();
            for (Ghost g : ghosts) {
                g.afraid();
            }
            tick = 10;
        }
    }


    // Resets the effect of the power
    // MODIFIES: this
    // EFFECTS: For every Ghost g in ghosts, sets them to brave.
    public void reversePower() {
        for (Ghost g : ghosts) {
            g.brave();
        }
    }


    // Ticks down till tick is 0 and the power time is over
    // MODIFIES: this
    // EFFECTS: Calls reversePower if tick is 0, else lowers tick by 1
    public void resolveTick() {
        if (tick == 0) {
            reversePower();
        } else {
            tick--;
        }
    }


    // Creates an allyGhost (A casper)
    // REQUIRES: s has to be "g"
    // MODIFIES: this
    // EFFECTS: removes ghost from inventory and adds a new ghost casper to caspers and sets its symbol
    //          and sets the amount of moves casperTick
    public void allyGhost(String s) {
        if (s.equals("g") && inventory.getGhosts().size() != 0) {
            inventory.useGhost();
            caspers.add(new Ghost(pac.getPosX(), pac.getPosY(), Ghost.ghostSymbol));
            caspers.get(caspers.size() - 1).setSymbol('#');
            casperTick = 20;
        }

    }

    public void checkIsGameOver() {
        if(isGameOver) {

        }
    }


    // Updates Casper
    // MODIFIES: this
    // EFFECTS: Turns caspers into an enemyGhost is the tick is 0, else moves all caspers and lets them eat pellets
    public void casper() {
        if (casperTick == 0) {
            enemyGhost();
        } else if (casperTick > 0) {
            for (Ghost g : caspers) {
                g.move(g.getRandomNum());
                eatPellets(g);
            }
            casperTick--;
        }
    }


    // Sets caspers back to normal enemy ghosts
    // MODIFIES: this
    // EFFECTS: Deletes caspers and creates a ghost for each casper deleted at x=1,y=1
    public void enemyGhost() {
        List<Ghost> toRemove = new ArrayList<>();
        for (Ghost g : caspers) {
            toRemove.add(g);
            ghosts.add(new Ghost(1, 1, Ghost.ghostSymbol));
        }
        caspers.removeAll(toRemove);
    }

    public Boolean isGameOver() {
        return isGameOver;
    }

    public List<Ghost> getCaspers() {
        return caspers;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getTick() {
        return tick;
    }

    public int getCasperTick() {
        return casperTick;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("score", score);
        json.put("tick", tick);
        json.put("casperTick", casperTick);
        json.put("isGameOver", isGameOver);
        json.put("pac", pac.toJson());
        json.put("ghosts", ghostsToJson(ghosts));
        json.put("pellets", pelletsToJson());
        json.put("inventory", inventory.toJson());
        json.put("powerList", powersToJson());
        json.put("caspers", ghostsToJson(caspers));

        return json;
    }

    //EFFECTS: returns pellets as a JSONArray
    private JSONArray pelletsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Pellet p : pellets) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns ghosts as a JSONArray
    public JSONArray ghostsToJson(List<Ghost> lg) {
        JSONArray jsonArray = new JSONArray();

        for (Ghost g : lg) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }

    //EFFECTS: returns powerList as a JSONArray
    public JSONArray powersToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Power p : powerList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // MODIFIES: this
    // EFFECTS: Sets isGameOver to b
    public void setIsGameOver(Boolean b) {
        isGameOver = b;
    }
} */
