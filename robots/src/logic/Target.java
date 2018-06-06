package logic;

import java.awt.*;

class Target {
    private int x;
    private int y;

    Target(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Point getPosition() {
        return new Point(x, y);
    }

    void setPosition(Point position) {
        x = position.x;
        y = position.y;
    }

    void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        Utils.fillOval(g, x, y, 5, 5);
        g.setColor(Color.BLACK);
        Utils.drawOval(g, x, y, 5, 5);
    }
}
