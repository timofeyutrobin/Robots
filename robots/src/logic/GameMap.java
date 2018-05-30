package logic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private List<Robot> robots;
    private List<Obstacle> obstacles;
    private Target target;

    private static final double velocity = 0.1;

    public GameMap() {
        robots = new ArrayList<>();
        obstacles = new ArrayList<>();

        robots.add(new Robot(100, 100));
        target = new Target(150, 100);
    }

    public void setTargetPosition(Point position) {
        target.setPosition(position);
    }

    public void addRobot(Point point) {
        robots.add(new Robot(point.x, point.y));
    }

    public void update() {
        for (Robot robot : robots) {
            double distance = Utils.distance(target.getX(), target.getY(),
                    robot.getX(), robot.getY());
            if (distance < 0.5) {
                continue;
            }
            robot.setDirection(Utils.angleTo(robot.getX(), robot.getY(), target.getX(), target.getY()));
            robot.moveRobot(velocity, 50);
        }
    }

    public void draw(Graphics2D g) {
        for (Robot robot :
                robots) {
            robot.draw(g, robot.getDirection());
        }
        target.draw(g);
    }

    public void addObstacle(Point point) {
        // TODO: 30.05.2018 create method
    }
}
