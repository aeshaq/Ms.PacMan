package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    PacManGame pmg;

    @BeforeEach
    void beforeach() {
        pmg = new PacManGame();
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            pmg = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    // can't realistically compare the pellets as I would have to add about 300 pellets manually to check all of them
    @Test
    void testReaderSlightlyPlayedGame() {
        JsonReader reader = new JsonReader("./data/PacManGameSlightlyPlayedTest.json");

        try {
            pmg = reader.read();
            assertTrue(pmg.getScore() == 6 );
            assertTrue(pmg.isGameOver() == false);
            assertTrue(pmg.getPac().equals(new PacMan(678,450,(char) 60)));
            assertTrue(pmg.getCaspers().equals(new ArrayList<Ghost>()));

            List<Ghost> ghostList = new ArrayList<>();
            ghostList.add(new Ghost(23,112,(char) 38));
            ghostList.add(new Ghost(15,76,(char) 38));
            ghostList.add(new Ghost(39,132,(char) 38));
            ghostList.add(new Ghost(35,92,(char) 38));

            assertTrue(ghostList.equals(pmg.getGhosts()));

            List<Power> powers = new ArrayList<>();
            assertTrue(powers.equals(pmg.getPowers()));

            Inventory inv = new Inventory();

            assertTrue(inv.equals(pmg.getInventory()));

        } catch (IOException e) {
            fail("Exception should not have been caught");
        }
    }
}