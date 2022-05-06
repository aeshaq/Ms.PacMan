package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GamePanel;


import static org.junit.jupiter.api.Assertions.*;

public class PacManTest {
    PacMan pac;
    PacManGame pmc;
    //Level lvl;


    @BeforeEach
    void runBefore() {
        pac = new PacMan(8,11, PacMan.leftFace);
        pmc = new PacManGame();
      //  lvl = new Level(pmc);
      //  lvl.buildMap();
    }

  /*  @Test
    void moveTest() {
        pac.move("w");

        assertTrue(pac.getPosY() == 10);
        pac = new PacMan(28,3, PacMan.leftFace);
        pac.move("w");
        assertTrue(pac.getPosY() == 3);

        pac = new PacMan(28,3, PacMan.leftFace);
        pac.move("s");
        assertTrue(pac.getPosY() == 4);
        pac = new PacMan(8,11, PacMan.leftFace);
        pac.move("s");
        assertTrue(pac.getPosY() == 11);

        pac = new PacMan(28,3,PacMan.leftFace);
        pac.move("d");
        assertTrue(pac.getPosX() == 28);
        pac = new PacMan(8,11,PacMan.leftFace);
        pac.move("d");
        assertTrue(pac.getPosX() == 9);

        pac = new PacMan(28,3,PacMan.leftFace);
        pac.move("a");
        assertTrue(pac.getPosX() == 27);
        pac = new PacMan(8,11,PacMan.leftFace);
        pac.move("a");
        assertTrue(pac.getPosX() == 8);

    } */

    @Test
    void handleBoundaryTest() {
        pac = new PacMan(0, 0, PacMan.leftFace);
        PacMan pac2 = new PacMan(GamePanel.width, GamePanel.height, Ghost.ghostSymbol);

        pac.handleBoundary();
        assertEquals(PacMan.radius / 2, pac.getPosX());
        assertEquals(PacMan.radius, pac.getPosY());

        pac2.handleBoundary();
        assertEquals(GamePanel.width - PacMan.radius / 2, pac2.getPosX());
        assertEquals(GamePanel.height - PacMan.radius / 2, pac2.getPosY());

    }

}