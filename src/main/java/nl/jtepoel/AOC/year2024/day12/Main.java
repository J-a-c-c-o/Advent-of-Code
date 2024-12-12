package nl.jtepoel.AOC.year2024.day12;


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

    public Grid<String> getInput() {

        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day12/input.txt");
        Grid<String> grid = new Grid<>();
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                grid.set(i, j, lines.get(i).substring(j, j + 1));
            }
        }

        return grid;

    }

    public String part1() {
        Grid<String> input = getInput();
        List<Set<Point>> regions = getRegions(input);
        long res = 0;

        for (Set<Point> region : regions) {
            long numPerimeter = getPerimeter(region);

            res += numPerimeter * region.size();
        }


        return String.valueOf(res);

    }

    private long getPerimeter(Set<Point> region) {
        long res = 0;
        for (Point p : region) {
            for (Point n : p.getNeighbours()) {
                if (!region.contains(n)) {
                    res++;
                }
            }
        }

        return res;
    }

    public String part2() {
        Grid<String> input = getInput();
        List<Set<Point>> regions = getRegions(input);
        long res = 0;
        for (Set<Point> region : regions) {
            long numSides = getNumSides(region);

            res += numSides*region.size();
        }


        return String.valueOf(res);
    }

    private long getNumSides(Set<Point> region) {
        int minX = region.stream().mapToInt(p -> p.x).min().orElseThrow();
        int maxX = region.stream().mapToInt(p -> p.x).max().orElseThrow();
        int minY = region.stream().mapToInt(p -> p.y).min().orElseThrow();
        int maxY = region.stream().mapToInt(p -> p.y).max().orElseThrow();

        long total = 0;

        HashMap<Integer, Boolean> boundry = new HashMap<>();
        for (int x = minX; x <= maxX; x++) {
            boolean prevIn = false;
            HashMap<Integer, Boolean> nextEdges = new HashMap<>();
            for (int y = minY; y <= maxY + 1; y++) {
                if (region.contains(new Point(x, y)) != prevIn) {
                    prevIn = !prevIn;
                    nextEdges.put(y, prevIn);
                    if (boundry.get(y) == null || boundry.get(y) != prevIn) {
                        total++;
                    }
                }
            }
            boundry = nextEdges;
        }

        boundry = new HashMap<>();
        for (int y = minY; y <= maxY; y++) {
            boolean prevIn = false;
            HashMap<Integer, Boolean> nextEdges = new HashMap<>();
            for (int x = minX; x <= maxX + 1; x++) {
                if (region.contains(new Point(x, y)) != prevIn) {
                    prevIn = !prevIn;
                    nextEdges.put(x, prevIn);
                    if (boundry.get(x) == null || boundry.get(x) != prevIn) {
                        total++;
                    }
                }
            }
            boundry = nextEdges;
        }

        return total;
    }




    private static List<Set<Point>> getRegions(Grid<String> input) {
        List<Set<Point>> regions = new ArrayList<>();
        Pair<Integer, Integer> dim = input.getDimensions();
        Set<Point> visited = new HashSet<>();
        for (int i = 0; i <= dim.x; i++) {
            for (int j = 0; j <= dim.y; j++) {
                Point p = new Point(j, i);

                if (visited.contains(p)) {
                    continue;
                }

                Queue<Point> q = new LinkedList<>();
                Set<Point> points = new HashSet<>();
                points.add(p);
                q.add(p);


                while (!q.isEmpty()) {
                    Point current = q.poll();
                    if (visited.contains(current)) {
                        continue;
                    }
                    visited.add(current);
                    for (Point n : current.getNeighbours()) {
                        if (input.get(n) != null && input.get(n).equals(input.get(current))) {
                            q.add(n);
                            points.add(n);
                        }
                    }

                }

                regions.add(points);
            }
        }


        return regions;
    }


}