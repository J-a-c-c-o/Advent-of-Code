package year2021.day5;


import java.util.*;

import utils.Pair;
import utils.Point;
import utils.Utils;

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

    public List<Pair<Point, Point>> getInput(boolean part1) {
        List<String> dirs = utils.getLines("src/main/java/year2021/day5/input.txt");
        List<Pair<Point, Point>> pairs = new ArrayList<>();
        for (String dir : dirs) {
            String[] temp = dir.split(" -> ");
            String[] temp2 = temp[0].split(",");
            String[] temp3 = temp[1].split(",");
            Point p1 = new Point(Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]));
            Point p2 = new Point(Integer.parseInt(temp3[0]), Integer.parseInt(temp3[1]));
            if (part1 && (!Objects.equals(p1.x, p2.x) && !Objects.equals(p1.y, p2.y))) {
                continue;
            }

            pairs.add(new Pair<>(p1, p2));
        }

        return pairs;
    }

    public String part1() {
        List<Pair<Point, Point>> pairs = getInput(true);

        return countOverlap(pairs);
    }

    public String part2() {
        List<Pair<Point, Point>> pairs = getInput(false);

        return countOverlap(pairs);

    }

    private static String countOverlap(List<Pair<Point, Point>> pairs) {
        Set<Point> set = new HashSet<>();
        Set<Point> visited = new HashSet<>();
        for (Pair<Point, Point> pair : pairs) {
            Point current = pair.x;
            Point target = pair.y;

            if (set.contains(current)) {
                visited.add(new Point(current.x, current.y));
            }
            set.add(new Point(current.x, current.y));

            while (!current.equals(target)) {
                if (current.x < target.x) {
                    current.x++;
                } else if (current.x > target.x) {
                    current.x--;
                }

                if (current.y < target.y) {
                    current.y++;
                } else if (current.y > target.y) {
                    current.y--;
                }

                if (set.contains(current)) {
                    visited.add(new Point(current.x, current.y));
                }
                set.add(new Point(current.x, current.y));

            }
        }

        //filter frequency set to only include pairs that have been visited more than once


        return String.valueOf(visited.size());
    }


}