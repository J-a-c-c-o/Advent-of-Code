package nl.jtepoel.AOC.year2024.day20;


import java.util.*;

import nl.jtepoel.AOC.utils.*;

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

    public Grid<Integer> getInput() {
        Grid<Integer> grid = new Grid<>();

        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day20/input.txt");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '.') {
                    grid.set(new Point(i, j), -1);
                } else if (line.charAt(j) == 'S') {
                    grid.set(new Point(i, j), -2);
                } else if (line.charAt(j) == 'E') {
                    grid.set(new Point(i, j), -3);
                } else {
                    grid.set(new Point(i, j), -4);
                }

            }
        }

        return grid;
    }

    int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public String part1() {
        Grid<Integer> input = getInput();

        LinkedList<Point> path = calculatePath(input);

        for (int i = 0; i < path.size(); i++) {
            Point p = path.get(i);
            input.set(p, i);
        }

        int count = getShortCuts(path, input, 2);


        return String.valueOf(count);


    }

    public String part2() {
        Grid<Integer> input = getInput();

        LinkedList<Point> path = calculatePath(input);

        for (int i = 0; i < path.size(); i++) {
            Point p = path.get(i);
            input.set(p, i);
        }

        int count = getShortCuts(path, input, 20);


        return String.valueOf(count);


    }

    private static int getShortCuts(LinkedList<Point> path, Grid<Integer> input, int allowed) {
        int count = 0;
        for (Point p : path) {
            int s = input.get(p);
            for (Point p2 : path) {
                int s2 = input.get(p2);

                if (s2 < s) {
                    continue;
                }

                if (p.equals(p2)) {
                    continue;
                }
                int distance = Math.abs(p.getX() - p2.getX()) + Math.abs(p.getY() - p2.getY());

                if (distance == 1) {
                    continue;
                }

                if (distance <= allowed) {
                    int dx = s2 - s - distance;
                    if (dx >= 100) {
                        count += 1;
                    }
                }
            }


        }
        return count;
    }

    private LinkedList<Point> calculatePath(Grid<Integer> input) {
        Point start = input.find(-2);
        Point end = input.find(-3);
        Queue<Pair<Point, LinkedList<Point>>> queue = new PriorityQueue<>(Comparator.comparingInt(t -> t.getSecond().size()));

        queue.add(new Pair<>(start, new LinkedList<>()));

        while (!queue.isEmpty()) {
            Pair<Point, LinkedList<Point>> current = queue.poll();
            Point currentPoint = current.getFirst();
            LinkedList<Point> currentPath = current.getSecond();

            if (currentPath.contains(currentPoint)) {
                continue;
            }
            currentPath.add(currentPoint);

            if (currentPoint.equals(end)) {
                return currentPath;
            }

            for (int[] direction : directions) {
                Point neighbour = currentPoint.add(direction[0], direction[1]);

                if (input.get(neighbour) == null || (input.get(neighbour) == -4)) {
                    continue;
                }
                queue.add(new Pair<>(neighbour, new LinkedList<>(currentPath)));

            }
        }
        return null;
    }



}