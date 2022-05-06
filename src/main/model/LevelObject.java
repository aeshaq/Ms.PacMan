package model;

/*
 Represent objects with x,y positions and a symbol that will show up in the map
 */

import org.json.JSONObject;
import persistence.Writable;

import java.util.List;

public class LevelObject implements Writable {
    private int posX;
    private int posY;
    private char symbol;

    public LevelObject(int x, int y, char c) {
        posX = x;
        posY = y;
        symbol = c;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setPosX(int x) {
        posX = x;
    }

    public void setPosY(int y) {
        posY = y;
    }

    public void setSymbol(char c) {
        symbol = c;
    }

    public Boolean isTouchWall(List<Wall> vert, List<Wall> horiz, int rad) {
        return isTouchWallHorizontal(horiz, rad) || isTouchWallVertical(vert, rad);
    }

    public Boolean isTouchWallHorizontal(List<Wall> hori, int radius) {
        for (Wall w : hori) {
            if (posX + radius / 2 >= w.getPosX() && posX - radius / 2 <= w.getPosX() + Wall.Longer) {
                if (posY >= w.getPosY() - radius / 2 && posY <= w.getPosY() + Wall.Shorter + radius / 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean isTouchWallVertical(List<Wall> vert, int radius) {
        for (Wall w : vert) {
            if (posX + radius / 2 >= w.getPosX() && posX - radius / 2 <= w.getPosX() + Wall.Shorter) {
                if (posY + radius / 2 >= w.getPosY()  && posY - radius / 2 <= w.getPosY() + Wall.Longer) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("posX", posX);
        json.put("posY", posY);
        json.put("symbol", symbol);

        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LevelObject that = (LevelObject) o;

        if (posX != that.posX) {
            return false;
        }
        if (posY != that.posY) {
            return false;
        }
        return symbol == that.symbol;
    }
}
