package nl.jtepoel.AOC.year2021.day11;


import java.util.HashSet;
import java.util.Set;

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

    public int[][] getInput() {
        return utils.makeGrid(utils.getLines(Utils.LOCSRC + "/year2021/day11/input.txt"),"");
    }

    public String part1() {
        int[][] grid = getInput();
        int count = 0;
        for (int t = 0; t < 100; t++) {
            count += step(grid);
        }
        return String.valueOf(count);
    }

    public String part2() {
        int[][] grid = getInput();
        int t;
        boolean blinkAll = false;
        for (t = 0; !blinkAll; t++) {
            int count = step(grid);;

            if (count == grid[0].length * grid.length) {
                blinkAll = true;
            }
        }

        return String.valueOf(t);
    }

    private int step(int[][] grid) {
        increaseAll(grid);

        int count = 0;
        boolean flashes = true;
        Set<Point> visited = new HashSet<>();
        while (flashes) {
            flashes = false;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] > 9 && !visited.contains(new Point(i, j))) {
                        flashes = true;
                        increaseAdjacent(grid, i, j);
                        count += 1;

                        visited.add(new Point(i, j));
                    }
                }
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] > 9) {
                    grid[i][j] = 0;
                }
            }
        }
        return count;
    }

    private void increaseAdjacent(int[][] grid, int i, int j) {
        // top
        if (i != 0) {
            grid[i-1][j] += 1;
        }

        // top right
        if (i != 0 && j != grid[0].length - 1) {
            grid[i-1][j+1] += 1;
        }

        // top left
        if (i != 0 && j != 0) {
            grid[i-1][j-1] += 1;
        }

        // right
        if (j != grid[0].length - 1) {
            grid[i][j+1] += 1;
        }

        // left
        if (j != 0) {
            grid[i][j-1] += 1;
        }

        // bottom
        if (i != grid.length - 1) {
            grid[i+1][j] += 1;
        }

        // bottom right
        if (i != grid.length - 1 && j != grid[0].length - 1) {
            grid[i+1][j+1] += 1;
        }

        // bottom left
        if (i != grid.length - 1 && j != 0) {
            grid[i+1][j-1] += 1;
        }
    }

    public void increaseAll(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] += 1;
            }
        }
    }
}