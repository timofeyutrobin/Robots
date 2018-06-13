package logic;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

class Obstacle extends Rectangle implements Serializable {
    static final int WIDTH = 100;
    static final int HEIGHT = 50;
    static final double DIAGONAL = Math.sqrt(WIDTH*WIDTH + HEIGHT*HEIGHT);

    //точки для создания графа
    private Point leftUp;
    private Point leftDown;
    private Point rightUp;
    private Point rightDown;

    Obstacle(int x, int y) {
        super(WIDTH, HEIGHT);
        leftUp = new Point((x - width / 2) - 5, (y - height / 2) - 5);
        leftDown = new Point((x - width / 2) - 5, (y + height / 2) + 5);
        rightUp = new Point((x + width / 2) + 5, (y - height / 2) - 5);
        rightDown = new Point((x + width / 2) + 5, (y + height / 2) + 5);

        setLocation(x - width / 2, y - height / 2);
    }

    public ArrayList<Point> getCornerPoints() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(leftUp);
        points.add(rightUp);
        points.add(leftDown);
        points.add(rightDown);
        return points;
    }

    void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLUE);
    }
}
