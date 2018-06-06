package logic;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.*;

class Robot {
    private volatile double x;
    private volatile double y;
    private double direction;
    private Map<Point, ArrayList<Point>> graph;
    private Deque<Point> route;
    private Point nextPoint;
    private boolean stopped;

    Robot(double x, double y) {
        this.x = x;
        this.y = y;
        direction = 0;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    private Point getPosition() {
        return new Point(Utils.round(x),Utils.round(y));
    }

    void updateRoute(ArrayList<Obstacle> obstacles, Target target) {
        Point position = getPosition();
        if (position.equals(target.getPosition())) return;
        makeGraph(obstacles, target);
        route = Dijkstra.getRoute(position, target.getPosition(), graph);
        if (route == null) {
            stopped = true;
            return;
        }
        nextPoint = route.poll();
        direction = Utils.angleTo(x, y, nextPoint.x, nextPoint.y);
        stopped = false;
    }

    void moveRobot(double velocity, double duration) {
        if (stopped) return;
        double distance = Utils.distance(getPosition(), nextPoint);
        if (distance < 0.5) {
            nextPoint = route.poll();
            if (nextPoint == null) {
                stopped = true;
                return;
            }
            direction = Utils.angleTo(x, y, nextPoint.x, nextPoint.y);
        }
        double newX = x + velocity * duration * Math.cos(direction);
        double newY = y + velocity * duration * Math.sin(direction);
        x = newX;
        y = newY;
    }

    void draw(Graphics2D g) {
        int robotCenterX = Utils.round(x);
        int robotCenterY = Utils.round(y);

        AffineTransform noTransform = g.getTransform();

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

        g.setTransform(noTransform);
    }

    private void makeGraph(ArrayList<Obstacle> obstacles, Target target) {
        graph = new HashMap<>();
        ArrayList<Point> nodes = new ArrayList<>();
        nodes.add(getPosition());
        nodes.add(target.getPosition());
        obstacles.forEach(obstacle -> nodes.addAll(obstacle.getCornerPoints()));

        //mapping lines
        for (Point point : nodes) {
            ArrayList<Point> incident = new ArrayList<>();
            for (Point anotherPoint : nodes) {
                if (GameMap.checkPath(point, anotherPoint)) {
                    incident.add(anotherPoint);
                }
            }
            graph.put(point, incident);
        }
    }
}
