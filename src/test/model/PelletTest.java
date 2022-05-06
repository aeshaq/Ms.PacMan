package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PelletTest {
    Pellet pellet;
    PacMan pac;

    @BeforeEach
    void runBefore() {
        pellet = new Pellet(1,1,Pellet.pelletSymbol);
        pac = new PacMan(21,21,PacMan.leftFace);
    }

    @Test
    void isPelletEatenTest() {
        assertTrue(pellet.isPelletEaten(pac));

        pac = new PacMan(22,22,PacMan.leftFace);
        assertFalse(pellet.isPelletEaten(pac));
    }
}
