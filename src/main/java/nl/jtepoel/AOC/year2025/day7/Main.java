package nl.jtepoel.AOC.year2025.day7;


import java.awt.*;
import java.util.*;
import java.util.List;

import nl.jtepoel.AOC.utils.*;
import nl.jtepoel.AOC.utils.Point;

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

    public Grid<Character> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2025/day7/input.txt");
        return utils.getGrids(lines).getFirst();
    }

    public String part1() {
        Grid<Character> grid = getInput();

        GridVisualizer<Character> vis = new GridVisualizer<Character>()
                .setFitToScreen(true)
                .setFadeEnabled(true)
                .setHighlightColor(Color.red)
                .setColorMapper(character -> switch (character) {
                    case 'S' -> Color.GREEN;
                    case '^' -> Color.WHITE;
                    case '|' -> new Color(0, 111, 0);
                    default -> new Color(0, 0, 40);
                })
                .setFadeDuration(1000);

        vis.setHeadingFont(new Font("Monospaced", Font.BOLD, 20))
                .setHeadingColor(Color.CYAN)
                .setHeading("Part 1: 0");

        vis.create(grid, '.');


        Point s = grid.find('S');
        List<Point> queue = new ArrayList<>();
        queue.add(s);
        long steps = 0;

        while (!queue.isEmpty()) {
            Point p = queue.removeFirst();


            Point below = new Point(p.x, p.y + 1);

            if (below.y > grid.getMaxY()) {
                continue;
            }

            char belowChar = grid.getOrDefault(below, '.');


            if (belowChar == '.') {
                grid.set(below, '|');
                queue.add(below);
            } else if (belowChar == '^') {
                steps++;
                vis.setHeading("Part 1: " + steps);
                queue.add(new Point(p.x + 1, p.y));
                queue.add(new Point(p.x - 1, p.y));
            }

            vis.update(grid);

        }


        return String.valueOf(steps);
    }

    public String part2() {
        Grid<Character> grid = getInput();


        Point s = grid.find('S');
        long total = recursiveCalculate(grid, s, new long[grid.getMaxX() + 1][grid.getMaxY() + 1]);
        return String.valueOf(total);
    }


    private static long recursiveCalculate(Grid<Character> grid, Point p, long[][] visited) {

        if (p.y > grid.getMaxY()) {
            return 1L;
        }

        if (visited[p.x][p.y] != 0) {
            return visited[p.x][p.y];
        }

        Point below = new Point(p.x, p.y + 1);
        char belowChar = grid.getOrDefault(below, '.');

        long total = 0L;

        if (belowChar == '.' || belowChar == '|') {
            total += recursiveCalculate(grid, below, visited);
        } else if (belowChar == '^') {
            Point right = new Point(p.x + 1, p.y);
            total += recursiveCalculate(grid, right, visited);

            Point left = new Point(p.x - 1, p.y);
            total += recursiveCalculate(grid, left, visited);
        }

        visited[p.x][p.y] = total;

        return total;
    }



}