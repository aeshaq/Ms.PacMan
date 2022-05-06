package model;

import java.awt.*;

public class Wall extends LevelObject {

    public static final int Longer = 150;
    public static final int Shorter = 5;
    public static final Color COLOR = new Color(47, 74, 253);

    public Wall(int x, int y) {
        super(x,y,'/');
    }
}
