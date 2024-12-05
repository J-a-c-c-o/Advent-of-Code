package nl.jtepoel.AOC.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid<A> {
    private final Map<Point, A> grid;
    int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public Grid() {
        grid = new HashMap<>();
    }

    public void set(Point point, A value) {
        grid.put(point, value);
    }

    public void set(int x, int y, A value) {
        grid.put(new Point(x, y), value);
    }

    public A get(Point point) {
        return grid.get(point);
    }

    public A get(int x, int y) {
        return grid.get(new Point(x, y));
    }

    public boolean contains(Point point) {
        return grid.containsKey(point);
    }

    public boolean contains(int x, int y) {
        return grid.containsKey(new Point(x, y));
    }

    public void remove(Point point) {
        grid.remove(point);
    }

    public void remove(int x, int y) {
        grid.remove(new Point(x, y));
    }

    public int size() {
        return grid.size();
    }

    public Pair<Integer, Integer> getDimensions() {
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Point point : grid.keySet()) {
            maxX = Math.max(maxX, point.x);
            maxY = Math.max(maxY, point.y);
        }

        return new Pair<>(maxX, maxY);
    }

    public void clear() {
        grid.clear();
    }

    public List<Point> getNeighbours(Point point) {
        List<Point> neighbours = new ArrayList<>();
        for (int[] direction : directions) {
            Point neighbour = new Point(point.x + direction[0], point.y + direction[1]);
            if (contains(neighbour)) {
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }

    public List<A> getNeighbourValues(Point point) {
        List<A> neighbours = new ArrayList<>();
        for (int[] direction : directions) {
            Point neighbour = new Point(point.x + direction[0], point.y + direction[1]);
            if (contains(neighbour)) {
                neighbours.add(get(neighbour));
            }
        }

        return neighbours;
    }


    protected Map<Point, A> getGrid() {
        return grid;
    }

    public int getMinY() {
        int min = Integer.MAX_VALUE;
        for (Point y : getGrid().keySet()) {
            min = Math.min(min, y.y);
        }
        return min;
    }

    public int getMaxY() {
        int max = Integer.MIN_VALUE;
        for (Point y : getGrid().keySet()) {
            max = Math.max(max, y.y);
        }
        return max;
    }

    public int getMinX() {
        int min = Integer.MAX_VALUE;
        for (Point x : getGrid().keySet()) {
            min = Math.min(min, x.x);
        }
        return min;
    }

    public int getMaxX() {
        int max = Integer.MIN_VALUE;
        for (Point x : getGrid().keySet()) {
            max = Math.max(max, x.x);
        }
        return max;
    }

    public void print(A defaultValue) {
        int minX = getMinX();
        int maxX = getMaxX();
        int minY = getMinY();
        int maxY = getMaxY();

        System.out.println();
        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                System.out.print(getOrDefault(j, i, defaultValue));
            }
            System.out.println();
        }
        System.out.println();

    }

    public A getOrDefault(int x, int y, A i1) {
        if (contains(x, y)) {
            return get(x, y);
        }
        return i1;
    }

    public Grid<A> copy() {
        Grid<A> copy = new Grid<>();
        for (Point point : grid.keySet()) {
            copy.set(new Point(point.getX(), point.getY()), grid.get(point));
        }
        return copy;

    }
}
