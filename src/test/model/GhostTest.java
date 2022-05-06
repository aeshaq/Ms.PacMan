package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.GamePanel;

import static org.junit.jupiter.api.Assertions.*;

public class GhostTest {
    private Ghost ghost;
    private Ghost anotherGhost;

    @BeforeEach
    void runBefore() {
        ghost = new Ghost(1, 1, Ghost.ghostSymbol);
        anotherGhost = new Ghost(2, 2, Ghost.ghostSymbol);
    }

    @Test
    void moveTest() {
        PacManGame pmc = new PacManGame();
        // Level lvl = new Level(pmc);
        // lvl.buildMap();
        int num;
        ghost = new Ghost(15, 11, Ghost.ghostSymbol);
        anotherGhost = new Ghost(885, 3, Ghost.ghostSymbol);

        num = 0;
        ghost.move(num);
        anotherGhost.move(num);
        assertEquals(19, ghost.getPosX());
        assertEquals(885, anotherGhost.getPosX());

        ghost = new Ghost(15, 11, Ghost.ghostSymbol);
        anotherGhost = new Ghost(28, 3, Ghost.ghostSymbol);
        num = 1;
        ghost.move(num);
        anotherGhost.move(num);
        assertEquals(15, ghost.getPosX());
        assertEquals(24, anotherGhost.getPosX());

        ghost = new Ghost(8, 30, Ghost.ghostSymbol);
        anotherGhost = new Ghost(28, 885, Ghost.ghostSymbol);
        num = 2;
        ghost.move(num);
        anotherGhost.move(num);
        assertEquals(34, ghost.getPosY());
        assertEquals(885, anotherGhost.getPosY());

        ghost = new Ghost(8, 15, Ghost.ghostSymbol);
        anotherGhost = new Ghost(28, 100, Ghost.ghostSymbol);
        num = 3;
        ghost.move(num);
        anotherGhost.move(num);
        assertEquals(15, ghost.getPosY());
        assertEquals(96, anotherGhost.getPosY());
    }

    @Test
    void handleBoundaryTest() {
        ghost = new Ghost(0, 0, Ghost.ghostSymbol);
        anotherGhost = new Ghost(GamePanel.width, GamePanel.height, Ghost.ghostSymbol);

        ghost.handleBoundary();
        assertEquals(Ghost.radius / 2, ghost.getPosX());
        assertEquals(Ghost.radius / 2, ghost.getPosY());

        anotherGhost.handleBoundary();
        assertEquals(GamePanel.width - Ghost.radius / 2, anotherGhost.getPosX());
        assertEquals(GamePanel.height - Ghost.radius / 2, anotherGhost.getPosY());

    }

    @Test
    void ghostAtePacTest() {
        PacMan pac = new PacMan(32, 32, PacMan.leftFace);

        assertFalse(ghost.ghostAtePac(pac));
        assertTrue(anotherGhost.ghostAtePac(pac));
    }

    @Test
    void afraidTest() {
        ghost.afraid();
        assertEquals('$', ghost.getSymbol());
    }

    @Test
    void braveTest() {
        ghost.afraid();
        assertFalse('&' == ghost.getSymbol());

        ghost.brave();
        assertTrue('&' == ghost.getSymbol());
    }
}
