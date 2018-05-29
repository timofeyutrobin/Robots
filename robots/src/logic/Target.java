package logic;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Target {
    private int x;
    private int y;

    public Target(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition (Point position) {
        x = position.x;
        y = position.y;
    }

    public void draw(Graphics2D g) {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0);
        g.setTransform(t);
        g.setColor(Color.GREEN);
        Utils.fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        Utils.drawOval(g, x, y, 5, 5);
    }
}
