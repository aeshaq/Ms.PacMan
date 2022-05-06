package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LevelObjectTest {
    LevelObject object;

    @BeforeEach
    void runBefore(){
        object = new LevelObject(1,1,'^');
    }

    @Test
    void getPosXTest() {
        assertTrue(object.getPosX() == 1);
        object.setPosX(2);
        assertTrue(object.getPosX() == 2);
    }

    @Test
    void getPosYTest() {
        assertTrue(object.getPosY() == 1);
        object.setPosY(2);
        assertTrue(object.getPosY() == 2);
    }

    @Test
    void setPosXTest() {
        object.setPosX(3);
        assertTrue(object.getPosX() == 3);

        object.setPosX(4);
        assertTrue(object.getPosX() == 4);
    }

    @Test
    void setPosYTest() {
        object.setPosY(3);
        assertTrue(object.getPosY() == 3);

        object.setPosY(4);
        assertTrue(object.getPosY() == 4);
    }

    @Test
    void getSymbolTest() {
        assertEquals('^', object.getSymbol());

        object.setSymbol('v');
        assertEquals('v',object.getSymbol());
    }

    @Test
    void setSymbolTest() {
        object.setSymbol('v');
        assertEquals('v',object.getSymbol());

        object.setSymbol('>');
        assertEquals('>',object.getSymbol());
    }

    @Test
    void toJsonTest() {
        JSONObject jo = object.toJson();
        assertTrue(jo.getInt("posX") == object.getPosX());
        assertTrue(jo.getInt("posY") == object.getPosY());
        assertTrue(jo.getInt("symbol") == object.getSymbol());
    }

    @Test
    void isEqualItself() {
        assertTrue(object.equals(object));
        assertFalse(object.equals(new Inventory()));
    }
}
