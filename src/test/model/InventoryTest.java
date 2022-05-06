package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InventoryTest {
    private Inventory inventory;
    Power power;
    Ghost ghost;

    @BeforeEach
    void runBefore() {
        inventory = new Inventory();
        power = new Power(3,2,Power.powerSymbol);
        ghost = new Ghost(3,2,Ghost.ghostSymbol);
    }

    @Test
    void eatPowerTest() {
        assertTrue(inventory.getPowers().size() == 0);

        inventory.eatPower(power);
        assertTrue(inventory.getPowers().size() == 1);

        assertTrue(inventory.getPowers().get(0).getPosX() == 3);
        assertTrue(inventory.getPowers().get(0).getPosY() == 2);
    }

    @Test
    void usePowerTest() {
        inventory.eatPower(power);
        assertTrue(inventory.getPowers().size() == 1);

        inventory.usePower();
        assertTrue(inventory.getPowers().size() == 0);

    }

    @Test
    void eatGhostTest() {
        assertTrue(inventory.getGhosts().size() == 0);

        inventory.eatGhost(ghost);
        assertTrue(inventory.getGhosts().size() == 1);

        assertTrue(inventory.getGhosts().get(0).getPosX() == 3);
        assertTrue(inventory.getGhosts().get(0).getPosY() == 2);
    }

    @Test
    void useGhostTest() {
        inventory.eatGhost(ghost);
        assertTrue(inventory.getGhosts().size() == 1);

        inventory.useGhost();
        assertTrue(inventory.getGhosts().size() == 0);

    }

    @Test
    void getPowersTest() {
        List<Power> powerList;
        powerList = inventory.getPowers();

        assertTrue(powerList.size() == 0);

        inventory.eatPower(power);
        powerList = inventory.getPowers();

        assertTrue(powerList.size() == 1);
    }

    @Test
    void getGhostsTest() {
        List<Ghost> ghosts;
        ghosts = inventory.getGhosts();

        assertTrue(ghosts.size() == 0);

        inventory.eatGhost(ghost);
        ghosts = inventory.getGhosts();

        assertTrue(ghosts.size() == 1);
    }

    @Test
    void toJsonTest() {
        inventory.eatGhost(ghost);
        inventory.eatPower(power);
        JSONObject jo = inventory.toJson();
        List<Ghost> ghosts = loadGhosts(jo,"eatenGhosts");
        List<Power> powerList = loadPowers(jo, "powers");

        int i = 0;
        for(Ghost g : ghosts) {
            assertTrue(g.equals(inventory.getGhosts().get(i)));
            i++;
        }

        i = 0;

        for (Power p : powerList) {
            assertTrue(p.equals(inventory.getPowers().get(i)));
            i++;
        }
    }

    private LevelObject loadLevelObject(JSONObject jsonObject) {
        int posX = jsonObject.getInt("posX");
        int posY = jsonObject.getInt("posY");
        char symbol = (char) jsonObject.getInt("symbol");

        return new LevelObject(posX, posY, symbol);
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

    @Test
    void isEqualTest() {
        Inventory inv2 = new Inventory();
        inventory.eatGhost(ghost);
        inv2.eatPower(power);

        assertTrue(inv2.equals(inv2));
        assertFalse(inv2.equals(inventory));
        assertFalse(inv2.equals(ghost));

        inventory.eatPower(power);
        assertFalse(inv2.equals(inventory));
    }
}
