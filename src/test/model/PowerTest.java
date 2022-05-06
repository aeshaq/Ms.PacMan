package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PowerTest {
    Power power;
    PacMan pac;
    PacManGame pmc;
    //Level lvl;

    @BeforeEach
    void runBefore() {
        power = new Power(3,3,Power.powerSymbol);
        pac = new PacMan(8,11,PacMan.leftFace);
        pmc = new PacManGame();
       // lvl = new Level(pmc);
       // lvl.buildMap();
    }

    @Test
    void placePowerTest() {
        //assertFalse(lvl.map[power.getPosX()][power.getPosY()] == power.getSymbol());
       // power.placePower();
      //  assertTrue(lvl.map[power.getPosX()][power.getPosY()] == power.getSymbol());
    }

    @Test
    void isPowerEatenTest() {
        assertFalse(power.isPowerEaten(pac));
        pac = new PacMan(3,3,PacMan.leftFace);

        assertTrue(power.isPowerEaten(pac));
    }
}
