package logic;

import java.awt.*;
import java.util.*;

public class Dijkstra {

    public static Deque<Point> getRoute(Point start, Point finish, Map<Point, ArrayList<Point>> graph, ArrayList<Obstacle> obstacles) {
        ArrayList<Point> nodes = new ArrayList<>(graph.keySet());
        Map<Point, Double> distance = new HashMap<>();
        Map<Point, Point> parent = new HashMap<>();
        Deque<Point> route = new ArrayDeque<>();

        distance.put(start, 0.0);
        parent.put(start, null);
        nodes.remove(start);

        for (Point p : nodes) {
            if (graph.get(p).contains(start)) {
                distance.put(p, Utils.distance(start, p));
            }
            else {
                distance.put(p, Double.POSITIVE_INFINITY);
            }
            parent.put(p, start);
        }

        for (int i = 0; i < graph.size() - 1; i++) {
            Point currentPoint = minV(nodes, distance);
            nodes.remove(currentPoint);
            for (Point nextPoint : graph.get(currentPoint)) {
                double distToNext = Utils.distance(currentPoint, nextPoint);
                if (distance.get(currentPoint) + distToNext < distance.get(nextPoint))
                {
                    distance.put(nextPoint, distance.get(currentPoint) + distToNext);
                    parent.put(nextPoint, currentPoint);
                }
            }
        }
        if (!GameMap.checkPath(parent.get(finish), finish, obstacles)) return null;
        route.push(finish);
        while (finish != start) {
            finish = parent.get(finish);
            route.push(finish);
        }
        return route;
    }

    private static Point minV(ArrayList<Point> list, Map<Point,Double> distance) {
        Point min = list.get(0);
        for (Point p : list) {
            if (distance.get(p) < distance.get(min)) {
                min = p;
            }
        }
        return min;
    }
}
