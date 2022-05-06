package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GamePanel;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PacManGameTest {
    int numGhosts = 4;
    PacManGame pmg;

    @BeforeEach
    void runBefore() {
        pmg = new PacManGame();

    }

    @Test
    void setUpTest() {
       assertTrue(pmg.getScore() == 0);
       assertTrue(pmg.getGhosts().size() == 4);
       assertTrue(pmg.getPac().getPosX() == GamePanel.width / 2);
       assertTrue(pmg.getPac().getPosY() == GamePanel.height / 2);
    }

    @Test
    void getPacTest() {
        assertTrue(pmg.getPac().getPosX() == GamePanel.width / 2);
        assertTrue(pmg.getPac().getPosY() == GamePanel.height / 2);
        pmg.update();
        assertTrue(pmg.getPac().getPosX() == GamePanel.width / 2 + 3);
        assertTrue(pmg.getPac().getPosY() == GamePanel.height / 2);
    }

    @Test
    void getPelletsTest() {
        int size = pmg.getPellets().size();
        pmg.getPellets().add(new Pellet(1, 2, Pellet.pelletSymbol));
        assertTrue(pmg.getPellets().size() == size + 1);
    }

    @Test
    void getGhostsTest() {
        assertTrue(pmg.getGhosts().size() == numGhosts);
        pmg.getGhosts().add(new Ghost(1, 1, Ghost.ghostSymbol));
        assertTrue(pmg.getGhosts().size() == numGhosts + 1);
    }

    @Test
    void getScore() {
        assertTrue(pmg.getScore() == 0);
    }

    @Test
    void eatPelletsTest() {
        int size = pmg.getPellets().size();
       pmg.getPac().setPosX(15);
       pmg.getPac().setPosY(15);
       pmg.update();
       assertEquals(size - 1, pmg.getPellets().size() );
    }

    @Test
    void isGameOverTest() {
        assertFalse(pmg.isGameOver());
    }

    @Test
    void getCaspersTest() {
        assertTrue(pmg.getCaspers().size() == 0);
    }

    @Test
    void getInventoryTest() {
        assertTrue(pmg.getInventory().getPowers().size() == 0);
        assertTrue(pmg.getInventory().getGhosts().size() == 0);
    }

    @Test
    void toJsonTest() {
        JSONObject jo = pmg.toJson();
        int score = jo.getInt("score");
        Boolean isGameOver = jo.getBoolean("isGameOver");
        PacMan pac = loadPacMan(jo);
        Inventory inv = loadInventory(jo);
        List<Ghost> ghosts = loadGhosts(jo, "ghosts");
        List<Pellet> pellets = loadPellets(jo);
        List<Power> powers = loadPowers(jo, "powerList");
        List<Ghost> caspers = loadGhosts(jo, "caspers");

        assertTrue(score == pmg.getScore());
        assertTrue(isGameOver == pmg.isGameOver());
        assertTrue(pac.equals(pmg.getPac()));
        assertTrue(inv.equals(pmg.getInventory()));
        assertTrue(ghosts.equals(pmg.getGhosts()));
        assertTrue(pellets.equals(pmg.getPellets()));
        assertTrue(powers.equals(pmg.getPowers()));
        assertTrue(caspers.equals(pmg.getCaspers()));
    }

    private Inventory loadInventory(JSONObject jsonObject) {
        JSONObject jo = jsonObject.getJSONObject("inventory");

        List<Power> powers = loadPowers(jo, "powers");
        List<Ghost> ghosts = loadGhosts(jo, "eatenGhosts");

        Inventory inv = new Inventory();

        for (Power p : powers) {
            inv.eatPower(p);
        }
        for (Ghost g : ghosts) {
            inv.eatGhost(g);
        }
        return inv;
    }

    private List<Power> loadPowers(JSONObject jsonObject, String s) {
        List<Power> powers = new ArrayList<>();
        JSONArray ja = jsonObject.getJSONArray(s);

        for (Object jo : ja) {
            JSONObject todo = (JSONObject) jo;
            LevelObject lo = loadLevelObject(todo);
            powers.add(new Power(lo.getPosX(), lo.getPosY(), lo.getSymbol()));
        }
        return powers;
    }

    private List<Ghost> loadGhosts(JSONObject jsonObject, String s) {
        List<Ghost> ghosts = new ArrayList<>();
        JSONArray ja = jsonObject.getJSONArray(s);

        for (Object jo : ja) {
            JSONObject todo = (JSONObject) jo;
            LevelObject lo = loadLevelObject(todo);
            ghosts.add(new Ghost(lo.getPosX(), lo.getPosY(), lo.getSymbol()));
        }
        return ghosts;
    }

    private List<Pellet> loadPellets(JSONObject jsonObject) {
        List<Pellet> pellets = new ArrayList<>();
        JSONArray ja = jsonObject.getJSONArray("pellets");

        for (Object jo : ja) {
            JSONObject todo = (JSONObject) jo;
            LevelObject lo = loadLevelObject(todo);
            pellets.add(new Pellet(lo.getPosX(), lo.getPosY(), lo.getSymbol()));
        }
        return pellets;
    }

    private LevelObject loadLevelObject(JSONObject jsonObject) {
        int posX = jsonObject.getInt("posX");
        int posY = jsonObject.getInt("posY");
        char symbol = (char) jsonObject.getInt("symbol");

        return new LevelObject(posX, posY, symbol);
    }

    private PacMan loadPacMan(JSONObject jo) {
        JSONObject js = jo.getJSONObject("pac");
        int posX = js.getInt("posX");
        int posY = js.getInt("posY");
        char symbol = (char) js.getInt("symbol");

        return new PacMan(posX, posY, symbol);
    }


}
