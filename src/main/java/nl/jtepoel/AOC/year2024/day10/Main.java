package nl.jtepoel.AOC.year2024.day10;


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

    public Grid<Integer> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day10/input.txt");
        Grid<Integer> grid = new Grid<Integer>();
        for (int i = 0; i < lines.size(); i++) {
            String[] split = lines.get(i).split("");
            for (int j = 0; j < split.length; j++) {
                grid.set(i, j, Integer.parseInt(split[j]));
            }
        }

        return grid;
    }

    private final int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public String part1() {
        Grid<Integer> grid = getInput();
        Pair<Integer, Integer> dim = grid.getDimensions();

        Queue<Point> queue = new LinkedList<>();

        //find all 0's
        long count = 0;
        for (int i = 0; i <= dim.x; i++) {
            for (int j = 0; j <= dim.y; j++) {
                if (grid.get(i, j).equals(0)) {
                    queue.add(new Point(i, j));

                    HashSet<Point> reached = new HashSet<>();
                    while (!queue.isEmpty()) {
                        Point current = queue.poll();

                        if (reached.contains(current)) {
                            continue;
                        }

                        reached.add(current);

                        if (grid.get(current) == 9) {
                            count++;
                        }

                        for (int[] direction : directions) {
                            Point newPos = new Point(current.x + direction[0], current.y + direction[1]);

                            if (grid.contains(newPos) && grid.get(newPos) - grid.get(current) == 1) {
                                queue.add(newPos);
                            }
                        }

                    }
                }
            }
        }


        return String.valueOf(count);
    }

    public String part2() {
        Grid<Integer> grid = getInput();
        Pair<Integer, Integer> dim = grid.getDimensions();

        //find all 0's
        long count = 0;
        for (int i = 0; i <= dim.x; i++) {
            for (int j = 0; j <= dim.y; j++) {
                if (grid.get(i, j).equals(0)) {
                    count += walk(grid, new Point(i, j), dim);
                }
            }
        }


        return String.valueOf(count);

    }
    HashMap<Point, Long> visited = new HashMap<>();
    public long walk(Grid<Integer> grid, Point pos, Pair<Integer, Integer> dim) {
        long count = 0;

        if (visited.containsKey(pos)) {
            return visited.get(pos);
        }

        if (grid.get(pos) == 9) {
            return 1;
        }

        for (int[] direction : directions) {
            Point newPos = new Point(pos.x + direction[0], pos.y + direction[1]);
            if (grid.contains(newPos) && grid.get(newPos) - grid.get(pos) == 1) {
                long inter = walk(grid, newPos, dim);
                visited.put(newPos, inter);
                count += inter;

            }
        }

        return count;

    }







}