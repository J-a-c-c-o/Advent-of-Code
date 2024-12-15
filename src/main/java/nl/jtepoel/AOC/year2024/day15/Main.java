package nl.jtepoel.AOC.year2024.day15;


import java.util.*;

import nl.jtepoel.AOC.utils.Grid;
import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Point;
import nl.jtepoel.AOC.utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        String part1 = main.part1();
        long part1Time = System.currentTimeMillis() - start;
        String part2 = main.part2();
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    public Pair<Grid<Character>, List<Character>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day15/input.txt");

        List<String> linesGrid = new ArrayList<>();
        List<Character> linesDirection = new ArrayList<>();
        boolean inGrid = true;
        for (String line : lines) {
            if (line.equals("")) {
                inGrid = false;
                continue;
            }
            if (inGrid) {
                linesGrid.add(line);
            } else {
                for (char c : line.toCharArray()) {
                    linesDirection.add(c);
                }
            }
        }




        Grid<Character> grid = new Grid<>();

        for (int i = 0; i < linesGrid.size(); i++) {
            for (int j = 0; j < linesGrid.get(i).length(); j++) {
                grid.set(j, i, linesGrid.get(i).charAt(j));
            }
        }

        return Pair.of(grid, linesDirection);
    }

    public Pair<Grid<Character>, List<Character>> getInput2() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day15/input.txt");

        List<String> linesGrid = new ArrayList<>();
        List<Character> linesDirection = new ArrayList<>();
        boolean inGrid = true;
        for (String line : lines) {
            if (line.equals("")) {
                inGrid = false;
                continue;
            }
            if (inGrid) {
                linesGrid.add(line);
            } else {
                for (char c : line.toCharArray()) {
                    linesDirection.add(c);
                }
            }
        }

        Grid<Character> grid = new Grid<>();

        for (int i = 0; i < linesGrid.size(); i++) {
            for (int j = 0; j < linesGrid.get(i).length(); j++) {
                switch (linesGrid.get(i).charAt(j)) {
                    case '#':
                        grid.set(j*2, i, '#');
                        grid.set(j*2 + 1, i, '#');
                        break;
                    case '.':
                        grid.set(j*2, i, '.');
                        grid.set(j*2 + 1, i, '.');
                        break;
                    case '@':
                        grid.set(j*2, i, '@');
                        grid.set(j*2 + 1, i, '.');
                        break;
                    case 'O':
                        grid.set(j*2, i, '[');
                        grid.set(j*2 + 1, i, ']');
                        break;
                }
            }
        }

        return Pair.of(grid, linesDirection);
    }

    public String part1() {
        Pair<Grid<Character>, List<Character>> input = getInput();

        Grid<Character> grid = input.getFirst();
        List<Character> directions = input.getSecond();

        int count = walker(directions, grid, 'O');

        return String.valueOf(count);
    }


    public String part2() {
        Pair<Grid<Character>, List<Character>> input = getInput2();

        Grid<Character> grid = input.getFirst();
        List<Character> directions = input.getSecond();

        int count = walker(directions, grid, ']');

        return String.valueOf(count);
    }

    private static int walker(List<Character> directions, Grid<Character> grid, char end) {
        Pair<Integer, Integer> dimensions = grid.getDimensions();

        for (char direction : directions) {
            Pair<Integer, Integer> start = find(dimensions, grid);
            Point directionPoint;
            Pair<List<Point>, Boolean> result;
            List<Point> points;
            boolean hitWall;
            switch (direction) {
                case '^':
                    directionPoint = new Point(0, -1);
                    result = walk(grid, start, directionPoint);

                    points = result.getFirst();
                    hitWall = result.getSecond();

                    points.sort(Comparator.comparing(Point::getY).thenComparing(Point::getX).reversed());

                    //if not hit wall, move the points to the new location
                    if (!hitWall) {
                        setGrid(grid, points, directionPoint);
                    }
                    break;
                case 'v':
                    directionPoint = new Point(0, 1);
                    result = walk(grid, start, directionPoint);

                    points = result.getFirst();
                    hitWall = result.getSecond();


                    points.sort(Comparator.comparing(Point::getY).thenComparing(Point::getX));

                    //if not hit wall, move the points to the new location
                    if (!hitWall) {
                        setGrid(grid, points, directionPoint);
                    }
                    break;

                case '<':
                    directionPoint = new Point(-1, 0);
                    result = walk(grid, start, directionPoint);

                    points = result.getFirst();
                    hitWall = result.getSecond();


                    points.sort(Comparator.comparingInt(Point::getX).thenComparing(Point::getY).reversed());

                    //if not hit wall, move the points to the new location
                    if (!hitWall) {
                        setGrid(grid, points, directionPoint);
                    }
                    break;

                case '>':
                    directionPoint = new Point(1, 0);
                    result = walk(grid, start, directionPoint);

                    points = result.getFirst();
                    hitWall = result.getSecond();


                    points.sort(Comparator.comparingInt(Point::getX).thenComparing(Point::getY));


                    //if not hit wall, move the points to the new location
                    if (!hitWall) {
                        setGrid(grid, points, directionPoint);
                    }
                    break;
            }
        }

        int count = 0;

        for (int i = 0; i <= dimensions.getSecond(); i++) {
            for (int j = 0; j <= dimensions.getFirst(); j++) {
                if (grid.get(j, i) != null && grid.get(j, i) == end) {
                    count += i*100 + j;
                }
            }
        }

        return count;
    }

    private static void setGrid(Grid<Character> grid, List<Point> points, Point directionPoint) {
        Set<Point> visitedPoints = new HashSet<>();
        for (Point point : points.reversed()) {
            char c = grid.get(point.getX(), point.getY());
            grid.set(point.getX() + directionPoint.getX(), point.getY() + directionPoint.getY(), c);
            visitedPoints.add(new Point(point.getX() + directionPoint.getX(), point.getY() + directionPoint.getY()));
        }

        for (Point point : points) {
            if (visitedPoints.contains(point)) {
                continue;
            }
            grid.set(point.getX(), point.getY(), '.');
        }

        grid.set(points.getFirst().getX(), points.getFirst().getY(), '.');
    }

    private static Pair<List<Point>, Boolean> walk(Grid<Character> grid, Pair<Integer, Integer> start, Point directionPoint) {
        List<Point> points = new ArrayList<>();
        //get all points in the direction
        boolean hitWall = false;
        Point current = new Point(start.getFirst(), start.getSecond());
        Queue<Point> queue = new LinkedList<>();
        queue.add(current);
        Set<Point> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            Point p = queue.poll();

            if (visited.contains(p)) {
                continue;
            }
            visited.add(p);
            if (grid.get(p.getX(), p.getY()) == null || grid.get(p.getX(), p.getY()) == '#') {
                hitWall = true;
                break;
            } else if (grid.get(p.getX(), p.getY()) == '[') {
                points.add(new Point(p.getX() + 1, p.getY()));
                queue.add(new Point(p.getX() + 1 + directionPoint.getX(), p.getY() + directionPoint.getY()));
            } else if (grid.get(p.getX(), p.getY()) == ']') {
                points.add(new Point(p.getX() - 1, p.getY()));
                queue.add(new Point(p.getX() - 1 + directionPoint.getX(), p.getY() + directionPoint.getY()));
            }

            if (grid.get(p.getX(), p.getY()) != '.') {
                points.add(p);
                queue.add(new Point(p.getX() + directionPoint.getX(), p.getY() + directionPoint.getY()));
            }

        }

        Pair<List<Point>, Boolean> result = new Pair<>(points, hitWall);
        return result;
    }

    private static Pair<Integer, Integer> find(Pair<Integer, Integer> dimensions, Grid<Character> grid) {
        for (int i = 0; i <= dimensions.getSecond(); i++) {
            for (int j = 0; j < dimensions.getFirst(); j++) {
                if (grid.get(j, i) == '@') {
                    return Pair.of(j, i);
                }
            }
        }
        return null;
    }





}