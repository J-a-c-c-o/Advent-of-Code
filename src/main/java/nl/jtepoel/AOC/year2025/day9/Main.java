package nl.jtepoel.AOC.year2025.day9;


import java.util.*;

import nl.jtepoel.AOC.utils.Grid;
import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Point;
import nl.jtepoel.AOC.utils.Utils;

import static com.ibm.icu.text.PluralRules.Operand.i;

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

    public  Pair<Grid<Character>, List<Point>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2025/day9/input.txt");
        Grid<Character> grid = new Grid<>();
        List<Point> points = new ArrayList<>();
        for  (String line : lines) {
            String[] parts = line.split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);
            Point point = new Point(row, col);
            grid.set(point,'#');
            points.add(point);
        }

        return Pair.of(grid, points);
    }

    public String part1() {
        Pair<Grid<Character>, List<Point>> pair = getInput();
        List<Point> points = pair.getSecond();
        long max = Long.MIN_VALUE;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i+1; j < points.size(); j++) {
                long area = (long) (Math.abs(points.get(i).x - points.get(j).x) + 1) * (Math.abs(points.get(i).y - points.get(j).y) + 1);
                if (area > max) {
                    max = area;

                }
            }

        }

        return String.valueOf(max);
    }

    public String part2() {
        Pair<Grid<Character>, List<Point>> pair = getInput();
        Grid<Character> grid = pair.getFirst();

        List<Point> points = pair.getSecond();
        points.add(points.getFirst());

        //compress grid
        List<Point> gridPoints = grid.getPoints();
        Set<Integer> x = new HashSet<>();
        Set<Integer> y = new HashSet<>();
        for (Point point : gridPoints) {
            x.add(point.x);
            y.add(point.y);
        }

        List<Integer> xList = new ArrayList<>(x);
        List<Integer> yList = new ArrayList<>(y);

        xList.sort(Integer::compareTo);
        yList.sort(Integer::compareTo);

        Grid<Character> newGrid = new Grid<>();
        for (Point point : gridPoints) {
            int yIndex = yList.indexOf(point.y);
            int xIndex = xList.indexOf(point.x);
            newGrid.set(xIndex,yIndex,'#');
        }



        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            int xIndexP1 = xList.indexOf(p1.x);
            int yIndexP1 = yList.indexOf(p1.y);
            Point nP1 = new Point(xIndexP1,yIndexP1);

            Point p2 = points.get(i+1);
            int xIndexP2 = xList.indexOf(p2.x);
            int yIndexP2 = yList.indexOf(p2.y);
            Point nP2 = new Point(xIndexP2, yIndexP2);

            draw(newGrid, nP1, nP2);
        }

        newGrid.floadFill(new Point(0,0), '?');

        long max = Long.MIN_VALUE;
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            int x1 = xList.indexOf(p1.x);
            int y1 = yList.indexOf(p1.y);
            for (int j = i+1; j < points.size(); j++) {

                Point p2 = points.get(j);
                int x2 = xList.indexOf(p2.x);
                int y2 = yList.indexOf(p2.y);
                if (valid(newGrid, Math.min(x1,x2), Math.min(y1,y2), Math.max(x1,x2), Math.max(y1,y2))) {
                    long area = (long) (Math.abs(p1.x - p2.x) + 1) * (Math.abs(p1.y - p2.y) + 1);
                    if (area > max) {
                        max = area;
                    }
                }
            }
        }

        return String.valueOf(max);
    }

    public void draw(Grid<Character> grid, Point point1, Point point2) {
        int x = Math.min(point1.x, point2.x);
        int y = Math.min(point1.y, point2.y);
        int x1 = Math.max(point1.x, point2.x);
        int y1 = Math.max(point1.y, point2.y);

        for (int i = x; i <= x1; i++) {
            for (int j = y; j <= y1; j++) {
                grid.set(i, j, 'X');
            }
        }
    }

    public boolean valid(Grid<Character> grid, int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            if (grid.getOrDefault(i, y1, 'X') == '?') {
                return false;
            }
            if (grid.getOrDefault(i, y2, 'X') == '?') {
                return false;
            }
        }

        for (int j = y1; j <= y2; j++) {
            if (grid.getOrDefault(x1, j, 'X') == '?') {
                return false;
            }

            if (grid.getOrDefault(x2, j, 'X') == '?') {
                return false;
            }
        }

        return true;
    }






}