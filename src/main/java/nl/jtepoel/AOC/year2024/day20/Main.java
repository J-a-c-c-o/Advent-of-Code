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
                }

            }
        }

        return grid;
    }

    int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public String part1() {
        Grid<Integer> input = getInput();

        LinkedList<Point> path = calculateSaves(input);

        for (int i = 0; i < path.size(); i++) {
            Point p = path.get(i);
            input.set(p, i);
        }

        int count = 0;

        for (Point p : path) {
            if (input.get(p) != null) {
                int distance = input.get(p);
                // check if we go through a wall we have to cheat
                Queue<Pair<Point, Integer>> queue = new LinkedList<>();
                queue.add(new Pair<>(p, 0));

                HashSet<Point> visited = new HashSet<>();

                while (!queue.isEmpty()) {
                    Pair<Point, Integer> current = queue.poll();
                    Point currentPoint = current.getFirst();
                    int currentCheat = current.getSecond();

                    if (visited.contains(currentPoint)) {
                        continue;
                    }

                    visited.add(currentPoint);



                    if (input.get(currentPoint) != null && currentCheat != 0) {
                        if (currentPoint.equals(p)) {
                            continue;
                        }

                        if (input.get(currentPoint) <= (distance - currentCheat - 100)) {
                            count += 1;
                        }

                        continue;
                    }

                    if (currentCheat > 20) {
                        continue;
                    }



                    for (int[] direction : directions) {
                        Point neighbour = currentPoint.add(direction[0], direction[1]);

                        queue.add(new Pair<>(neighbour, currentCheat + 1));
                    }
                }


            }
        }



        return String.valueOf(count);


    }

    private LinkedList<Point> calculateSaves(Grid<Integer> input) {
        Point start = input.find(-2);
        Point end = input.find(-3);
        Queue<Triple<Point, LinkedList<Point>, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(t -> t.getSecond().size()));

        queue.add(new Triple<>(start, new LinkedList<>(), 0));

        while (!queue.isEmpty()) {
            Triple<Point, LinkedList<Point>, Integer> current = queue.poll();
            Point currentPoint = current.getFirst();
            LinkedList<Point> currentPath = current.getSecond();
            int currentCheat = current.getThird();

            if (currentPath.contains(currentPoint)) {
                continue;
            }
            currentPath.add(currentPoint);

            if (currentPoint.equals(end)) {
                return currentPath;
            }

            for (int[] direction : directions) {
                Point neighbour = currentPoint.add(direction[0], direction[1]);

                if (input.get(neighbour) == null) {
                    continue;
                }
                queue.add(new Triple<>(neighbour, new LinkedList<>(currentPath), currentCheat));

            }
        }
        return null;
    }

    public String part2() {
        return "";
    }



}