package logic;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private Robot robot;
    private List<Obstacle> obstacles;
    private Target target;

    private Point robotPosition;
    private Point targetPosition;

    private static final double velocity = 0.1;

    //сейчас он создает дефолтную карту
    public GameMap() {
        obstacles = new ArrayList<>();

        robot = new Robot(100, 100);
        target = new Target(150, 100);
    }

    public void setTargetPosition(Point position) {
        target.setPosition(position);
    }

    public void update() {
        double distance = Utils.distance(target.getX(), target.getY(),
                robot.getX(), robot.getY());
        if (distance < 0.5)
        {
            return;
        }
        robot.setDirection(Utils.angleTo(robot.getX(), robot.getY(), target.getX(), target.getY()));
        robot.moveRobot(velocity,50);
    }

    public void draw(Graphics2D g) {
        robot.draw(g, robot.getDirection());
        target.draw(g);
    }
}
