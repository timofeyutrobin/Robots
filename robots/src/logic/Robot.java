package logic;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Robot {
    private volatile double x;
    private volatile double y;
    private volatile double direction;

    public Robot(double x, double y) {
        this.x = x;
        this.y = y;
        direction = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDirection() {
        return direction;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void moveRobot(double velocity, double duration) {
        double newX = x + velocity * duration * Math.cos(direction);
        double newY = y + velocity * duration * Math.sin(direction);
        x = newX;
        y = newY;
    }

    public void draw(Graphics2D g, double direction) {
        int robotCenterX = Utils.round(x);
        int robotCenterY = Utils.round(y);
        AffineTransform t = AffineTransform.getRotateInstance(direction, robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        Utils.fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        Utils.drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        Utils.fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        Utils.drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }
}
