package nl.jtepoel.AOC.year2024.day6;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public Triple<Grid<Boolean>, Point, Character> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day6/input.txt");
        Grid<Boolean> grid = new Grid<>();
        Point pos = new Point(0, 0);
        char direction = ' ';
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] sp = line.split("");
            for (int j = 0; j < sp.length; j++) {
                String s = sp[j];
                if (s.equals("#")) {
                    grid.set(new Point(j, i), true);
                } else if (!s.equals(".")) {
                    pos = new Point(j, i);
                    direction = s.charAt(0);
                }
            }
        }

        return new Triple<>(grid, pos, direction);
    }

    public String part1() {
        Triple<Grid<Boolean>, Point, Character> pair = getInput();
        Grid<Boolean> grid = pair.getFirst();
        Grid<Boolean> visited = new Grid<>();
        Point pos = pair.getSecond();
        char direction = pair.getThird();


        Pair<Integer, Integer> dimensions = grid.getDimensions();
        while (pos.getX() >= 0 && pos.getX() <= dimensions.getFirst() && pos.getY() >= 0 && pos.getY() <= dimensions.getSecond()) {
            visited.set(new Point(pos.getX(), pos.getY()), true);
            while (grid.getOrDefault(pos.next(direction, true), false)) {
                direction = pos.turn(direction);
            }
            pos = pos.move(direction);
        }


        return String.valueOf(visited.size());
    }

    public String part2() {
        Triple<Grid<Boolean>, Point, Character> pair = getInput();

        Grid<Boolean> grid = pair.getFirst();
        Pair<Integer, Integer> dimensions = grid.getDimensions();

        char direction = pair.getThird();
        Point currentPos = new Point(pair.getSecond().getX(), pair.getSecond().getY());

        int loops = 0;
        for (int i = 0; i <= dimensions.x; i++) {
            for (int j = 0; j <= dimensions.y; j++) {
                if (grid.getOrDefault(new Point(i, j), false) || currentPos.equals(new Point(i, j))) {
                    continue;
                }

                grid.set(new Point(i, j), true);

                loops += loop(currentPos, direction, dimensions, grid) ? 1 : 0;

                grid.remove(new Point(i, j));
            }
        }
        return String.valueOf(loops);
    }

    private boolean loop(Point pos, char direction, Pair<Integer, Integer> dimensions, Grid<Boolean> grid) {
        Set<Pair<Point, Character>> loop = new HashSet<>();
        while (pos.getX() >= 0 && pos.getX() < dimensions.getFirst() && pos.getY() >= 0 && pos.getY() < dimensions.getSecond()) {
            if (loop.contains(new Pair<>(pos, direction))) {
                return true;
            }
            loop.add(new Pair<>(pos, direction));
            while (grid.getOrDefault(pos.next(direction, true), false)) {
                direction = pos.turn(direction);
            }
            pos = pos.move(direction);
        }
        return false;
    }



}