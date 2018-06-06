package logic;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class GameMap {
    private static ArrayList<Robot> robots;
    private static ArrayList<Obstacle> obstacles;
    private static Target target;

    private static final double velocity = 0.1;

    public GameMap() {
        robots = new ArrayList<>();
        obstacles = new ArrayList<>();

        target = new Target(150, 100);
        addRobot(new Point(100, 100));
    }

    public synchronized void setTargetPosition(Point position) {
        if (obstacles.stream().anyMatch(o -> o.contains(position))) {
            return;
        }
        target.setPosition(position);
        robots.forEach(robot -> robot.updateRoute(obstacles, target));
    }

    public synchronized void addRobot(Point position) {
        if (obstacles.stream().anyMatch(o -> o.contains(position))) {
            return;
        }
        Robot robot = new Robot(position.x, position.y);
        robot.updateRoute(obstacles, target);
        robots.add(robot);
    }

    public synchronized void addObstacle(Point point) {
        for (Robot robot : robots) {
            if (Utils.distance(robot.getX(), robot.getY(), point.x, point.y) <= Obstacle.DIAGONAL / 2) {
                return;
            }
        }
        obstacles.add(new Obstacle(point.x, point.y));
        robots.forEach(robot -> robot.updateRoute(obstacles, target));
    }

    public synchronized void removeObstacle(Point point) {
        obstacles.removeIf(obstacle -> obstacle.contains(point));
        robots.forEach(robot -> robot.updateRoute(obstacles, target));
    }

    public synchronized void update() {
        robots.forEach(robot -> robot.moveRobot(velocity, 10));
    }

    public void draw(Graphics2D g) {
        target.draw(g);
        for (Robot robot : robots) {
            robot.draw(g);
        }
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }
    }

    static boolean checkPath(Point p1, Point p2) {
        if (p1.equals(p2)) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.intersectsLine(new Line2D.Double(p1.x, p1.y, p2.x, p2.y))) {
                return false;
            }
        }
        return true;
    }
}
