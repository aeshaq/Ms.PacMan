package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    public static final int width = 900;
    public static final int height = 900;
    private static final String OVER = "Game Over!";
    private static final String REPLAY = "R to replay";
    private PacManGame game;

    // Constructs a Level
    // Effects: updates this with game to be displayed
    public GamePanel(PacManGame pmg) {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        game = pmg;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);
        if (game.isGameOver()) {
            game.clearAll();
            gameOver(g);
        }
    }

    public void drawGame(Graphics g) {
        drawPac(g);
        drawPowers(g);
        drawGhosts(g);
        drawCaspers(g);
        drawWalls(g);
        drawPellets(g);
    }

    public void drawPac(Graphics g) {
        PacMan pm = game.getPac();
        Color savedCol = g.getColor();
        g.setColor(pm.COLOR);
        g.fillOval(pm.getPosX() - pm.radius / 2, pm.getPosY() - pm.radius / 2, pm.radius, pm.radius);
        g.setColor(savedCol);
    }

    public void drawCaspers(Graphics g) {
        for (Ghost h : game.getCaspers()) {
            drawCasper(g, h);
        }
    }

    public void drawGhosts(Graphics g) {
        for (Ghost h : game.getGhosts()) {
            //drawGhost(g, h);
            if (h.getSymbol() == '$') {
                drawGhostAfraid(g, h);
            } else {
                drawGhost(g, h);
            }
        }
    }

    public void drawCasper(Graphics g, Ghost h) {
        Color savedCol = g.getColor();
        g.setColor(h.casper);
        g.fillOval(h.getPosX() - h.radius / 2, h.getPosY() - h.radius / 2, h.radius, h.radius);
        g.setColor(savedCol);
    }

    public void drawGhostAfraid(Graphics g, Ghost h) {
        Color savedCol = g.getColor();
        g.setColor(h.afraidColor);
        g.fillOval(h.getPosX() - h.radius / 2, h.getPosY() - h.radius / 2, h.radius, h.radius);
        g.setColor(savedCol);
    }

    public void drawGhost(Graphics g, Ghost h) {
        Color savedCol = g.getColor();
        g.setColor(h.COLOR);
        g.fillOval(h.getPosX() - h.radius / 2, h.getPosY() - h.radius / 2, h.radius, h.radius);
        g.setColor(savedCol);
    }

    public void drawPellets(Graphics g) {
        for (Pellet p : game.getPellets()) {
            drawPellet(g, p);
        }
    }

    public void drawPellet(Graphics g, Pellet p) {
        Color savedCol = g.getColor();
        g.setColor(p.COLOR);
        g.fillOval(p.getPosX() - p.radius / 2, p.getPosY() - p.radius / 2, p.radius, p.radius);
        g.setColor(savedCol);
    }

    public void drawPowers(Graphics g) {
        for (Power p : game.getPowers()) {
            drawPower(g, p);
        }
    }

    public void drawPower(Graphics g, Power p) {
        Color savedCol = g.getColor();
        g.setColor(p.COLOR);
        g.fillOval(p.getPosX() - p.radius / 2, p.getPosY() - p.radius / 2, p.radius, p.radius);
        g.setColor(savedCol);
    }

    public void drawWalls(Graphics g) {
        for (Wall w : game.getHorizontalWalls()) {
            drawHorizontalWall(g, w);
        }

        for (Wall w : game.getVerticalWalls()) {
            drawVerticalWall(g, w);
        }
    }

    private void drawHorizontalWall(Graphics g, Wall w) {
        Color savedCol = g.getColor();
        g.setColor(w.COLOR);
        //g.fillOval(p.getPosX() - p.radius, p.getPosY() - p.radius, p.radius, p.radius);
        g.fillRect(w.getPosX(), w.getPosY(), w.Longer, w.Shorter);
        g.setColor(savedCol);
    }

    private void drawVerticalWall(Graphics g, Wall w) {
        Color savedCol = g.getColor();
        g.setColor(w.COLOR);
        //g.fillOval(p.getPosX() - p.radius, p.getPosY() - p.radius, p.radius, p.radius);
        g.fillRect(w.getPosX(), w.getPosY(), w.Shorter, w.Longer);
        g.setColor(savedCol);
    }

    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(232, 47, 47));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, height / 2);
        centreString(REPLAY, g, fm, height / 2 + 50);
        g.setColor(saved);
    }

    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (this.width - width) / 2, posY);
    }

    public void pacControl(int keyCode) {
        if (keyCode == KeyEvent.VK_W) {
            game.getPac().setDirection(0);
        } else if (keyCode == KeyEvent.VK_S) {
            game.getPac().setDirection(1);
        } else if (keyCode == KeyEvent.VK_D) {
            game.getPac().setDirection(2);
        } else if (keyCode == KeyEvent.VK_A) {
            game.getPac().setDirection(3);
        }
    }
}
