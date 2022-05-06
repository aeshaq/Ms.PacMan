package persistence;

import model.PacManGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    PacManGame pmg;

    @BeforeEach
    void runBefore() {
        pmg = new PacManGame();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void writerGeneralGameTest() {
        try {
            JsonWriter writer = new JsonWriter("./data/PacManGameTest.json");
            writer.open();
            writer.write(pmg);
            writer.close();

            JsonReader reader = new JsonReader("./data/PacManGameTest.json");
            PacManGame loadedGame = reader.read();

            assertTrue(pmg.getScore() == loadedGame.getScore());
            assertTrue(pmg.isGameOver() == loadedGame.isGameOver());
            assertTrue(pmg.getPac().equals(loadedGame.getPac()));
            assertTrue(pmg.getPellets().equals(loadedGame.getPellets()));
            assertTrue(pmg.getGhosts().equals(loadedGame.getGhosts()));
            assertTrue(pmg.getCaspers().equals(loadedGame.getCaspers()));
            assertTrue(pmg.getPowers().equals(loadedGame.getPowers()));
            assertTrue(pmg.getInventory().equals(loadedGame.getInventory()));


        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }
/*
    @Test
    void testWriterEmptyWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            assertEquals(0, wr.numThingies());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WorkRoom wr = new WorkRoom("My work room");
            wr.addThingy(new Thingy("saw", Category.METALWORK));
            wr.addThingy(new Thingy("needle", Category.STITCHING));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            assertEquals("My work room", wr.getName());
            List<Thingy> thingies = wr.getThingies();
            assertEquals(2, thingies.size());
            checkThingy("saw", Category.METALWORK, thingies.get(0));
            checkThingy("needle", Category.STITCHING, thingies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    } */
}