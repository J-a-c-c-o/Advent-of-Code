package nl.jtepoel.AOC.utils;

import java.util.*;

public class Grid<A> {
    private final Map<Point, A> grid;
    private final Map<A, List<Point>> frequency;
    public final static int[][] DIRECTIONS = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public final static int[][] DIRECTIONS8 = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, 1}, {-1, -1}, {1, -1}};

    public Grid() {
        grid = new HashMap<>();
        frequency = new HashMap<>();
    }

    public void set(Point point, A value) {
        grid.put(point, value);
        frequency.putIfAbsent(value, new ArrayList<>());
        frequency.get(value).add(point);
    }

    public void set(int x, int y, A value) {
        grid.put(new Point(x, y), value);
        frequency.putIfAbsent(value, new ArrayList<>());
        frequency.get(value).add(new Point(x, y));
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
        frequency.get(grid.get(point)).remove(point);
        grid.remove(point);
    }

    public void remove(int x, int y) {
        frequency.get(grid.get(new Point(x, y))).remove(new Point(x, y));
        grid.remove(new Point(x, y));
    }

    public int size() {
        return grid.size();
    }

    public Pair<Integer, Integer> getDimensions() {
        return new Pair<>(getMaxX(), getMaxY());
    }

    public void clear() {
        grid.clear();
    }

    public List<Point> getNeighbours(Point point, int[][] directions) {
        List<Point> neighbours = new ArrayList<>();
        for (int[] direction : directions) {
            Point neighbour = new Point(point.x + direction[0], point.y + direction[1]);
            if (contains(neighbour)) {
                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }

    public List<A> getNeighbourValues(Point point, int[][] directions) {
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

    public A getOrDefault(Point point, A i1) {
        if (contains(point)) {
            return get(point);
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

    public boolean inRange(Point p2) {
        return p2.getX() >= 0 && p2.getX() <= getMaxX() && p2.getY() >= 0 && p2.getY() <= getMaxY();
    }


    public Map<A, List<Point>> getFrequency() {
        return new HashMap<>() {{
            putAll(frequency);
        }};
    }

    public List<List<Point>> connectedComponents() {
        // connected if they are neighbours
        Set<Point> visited = new HashSet<>();
        List<List<Point>> components = new ArrayList<>();
        for (Point point : grid.keySet()) {

            if (visited.contains(point)) {
                continue;
            }

            Queue<Point> queue = new LinkedList<>();
            queue.add(point);
            List<Point> component = new ArrayList<>();
            while (!queue.isEmpty()) {
                Point current = queue.poll();
                if (visited.contains(current)) {
                    continue;
                }

                A value = get(current);

                visited.add(current);

                component.add(current);
                for (Point neighbour : getNeighbours(current, Grid.DIRECTIONS)) {
                    if (get(neighbour).equals(value)) {
                        queue.add(neighbour);
                    }
                }
            }
            components.add(component);
        }

        return components;
    }

    public Point find(A value) {
        for (Point point : grid.keySet()) {
            if (get(point).equals(value)) {
                return point;
            }
        }
        return null;
    }

    public List<List<A>> columns() {
        List<List<A>> columns = new ArrayList<>();
        for (int i = getMinX(); i <= getMaxX(); i++) {
            List<A> column = new ArrayList<>();
            for (int j = getMinY(); j <= getMaxY(); j++) {
                column.add(getOrDefault(i, j, null));
            }
            columns.add(column);
        }
        return columns;
    }

    public List<List<A>> rows() {
        List<List<A>> rows = new ArrayList<>();
        for (int i = getMinY(); i <= getMaxY(); i++) {
            List<A> row = new ArrayList<>();
            for (int j = getMinX(); j <= getMaxX(); j++) {
                row.add(getOrDefault(j, i, null));
            }
            rows.add(row);
        }
        return rows;
    }

    public List<Point> getPoints() {
        return new ArrayList<>(grid.keySet());
    }
}
