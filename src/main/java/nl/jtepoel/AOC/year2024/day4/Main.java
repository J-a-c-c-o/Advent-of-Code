package nl.jtepoel.AOC.year2024.day4;


import java.util.ArrayList;
import java.util.List;

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
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day4/input.txt");
        Grid<String> grid = new Grid<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                grid.set(j, i, String.valueOf(line.charAt(j)));
            }
        }

        return grid;
    }

    public String part1() {
        Grid<String> grid = getInput();

        int count = 0;
        for (int j = 0; j <= grid.getDimensions().x; j++) {
            for (int i = 0; i <= grid.getDimensions().y; i++) {
                count += check(grid, j, i);
            }
        }


        return String.valueOf(count);


    }

    List<Pair<Integer, Integer>> directions = List.of(new Pair<>(0, 1), new Pair<>(0, -1), new Pair<>(1, 0), new Pair<>(-1, 0), new Pair<>(1, 1), new Pair<>(-1, -1), new Pair<>(1, -1), new Pair<>(-1, 1));

    private int check(Grid<String> grid, int x, int y) {
        int count = 0;
        for (Pair<Integer, Integer> direction : directions) {
            int xDir = direction.x;
            int yDir = direction.y;
            count += getCount(grid, x, y, xDir, yDir);
        }

        return count;
    }

    private static int getCount(Grid<String> grid, int x, int y, int xDir, int yDir) {
        if (grid.get(x, y) != null && grid.get(x, y).equals("X")) {
            if (grid.get(x + xDir, y + yDir) != null && grid.get(x + xDir, y + yDir).equals("M") &&
                    grid.get(x + xDir * 2, y + yDir * 2) != null && grid.get(x + xDir * 2, y + yDir * 2).equals("A") &&
                        grid.get(x + xDir * 3, y + yDir * 3) != null && grid.get(x + xDir * 3, y + yDir * 3).equals("S")) {
                return 1;
            }
        }
        return 0;
    }


    public String part2() {
        Grid<String> grid = getInput();
        int count = 0;
        for (int j = 0; j < grid.getDimensions().x; j++) {
            for (int i = 0; i < grid.getDimensions().y; i++) {
                if ((check2(grid, i-1, j-1, 1, 1) || check2(grid, i+1, j+1, -1, -1)) &&
                        (check2(grid, i-1, j+1, 1, -1) || check2(grid, i+1, j-1, -1, 1))) {
                    count++;
                }

            }
        }

        return String.valueOf(count);
    }


    private boolean check2(Grid<String> grid, int x, int y, int xDir, int yDir) {
        if (grid.get(x, y) != null && grid.get(x, y).equals("M")) {
            if (grid.get(x + xDir, y + yDir) != null && grid.get(x + xDir, y + yDir).equals("A")) {
                return grid.get(x + xDir * 2, y + yDir * 2) != null && grid.get(x + xDir * 2, y + yDir * 2).equals("S");
            }
        }

        return false;
    }



}