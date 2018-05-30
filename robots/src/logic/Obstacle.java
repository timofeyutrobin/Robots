package logic;

import java.awt.*;

public class Obstacle extends Rectangle {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 50;
    public static final double DIAGONAL = Math.sqrt(WIDTH*WIDTH + HEIGHT*HEIGHT);

    public Obstacle(int x, int y) {
        super(WIDTH, HEIGHT);
        setLocation(x - width / 2, y - height / 2);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }
}
