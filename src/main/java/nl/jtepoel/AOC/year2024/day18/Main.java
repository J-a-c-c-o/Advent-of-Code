package nl.jtepoel.AOC.year2024.day18;


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

    public Pair<Grid<Character>, List<Point>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day18/input.txt");
        Grid<Character> grid = new Grid<>();
        List<Point> remainder = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (i > 1024) {
                remainder.add(new Point(Integer.parseInt(lines.get(i).split(",")[0]), Integer.parseInt(lines.get(i).split(",")[1])));
                continue;
            }

            String line = lines.get(i);
            String split[] = line.split(",");
            Point p = new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            grid.set(p, '#');
        }

        return new Pair<>(grid, remainder);
    }

    public String part1() {
        Pair<Grid<Character>, List<Point>> input = getInput();
        Grid<Character> grid = input.getFirst();

        Set<Point> path = simulate(grid);
        if (path != null) {
            return path.size() + "";
        }


        System.out.println("No path found");

        return "";
    }

    public String part2() {
        Pair<Grid<Character>, List<Point>> input = getInput();
        Grid<Character> grid = input.getFirst();

        int i = 0;

        Set<Point> previous = simulate(grid);

        for (Point p : input.getSecond()) {
            if (!previous.contains(p)) {
                continue;
            }
            grid.set(p, '#');
            previous = simulate(grid);
            if (previous == null) {
                return p.toString();
            }

        }



        System.out.println("No path found");

        return "";
    }

    private static Set<Point> simulate(Grid<Character> grid) {
        Point start = new Point(0, 0);
        Point end = new Point(70, 70);

        Queue<Pair<Point,Set<Point>>> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.getSecond().size()));
        Set<Point> visited = new HashSet<>();
        queue.add(new Pair<>(start, new HashSet<>()));
        while (!queue.isEmpty()) {
            Pair<Point,Set<Point>> pair = queue.poll();
            Point current = pair.getFirst();
            Set<Point> path = pair.getSecond();
            if (current.equals(end)) {
                return path;
            }
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            path.add(current);
            for (Point p : current.getNeighbours()) {

                if (p.getX() > 70 || p.getY() > 70 || p.getX() < 0 || p.getY() < 0) {
                    continue;
                }

                if (grid.get(p) != null && grid.get(p) == '#') {
                    continue;
                }
                queue.add(new Pair<>(p, new HashSet<>(path)));
            }
        }
        return null;
    }


}