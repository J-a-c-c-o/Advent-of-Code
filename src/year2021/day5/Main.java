package year2021.day5;


import java.util.*;

import utils.Pair;
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

    public List<Pair<Pair<Integer,Integer>, Pair<Integer,Integer>>> getInput(boolean part1) {
        List<String> dirs = utils.getLines("src/year2021/day5/example.txt");
        List<Pair<Pair<Integer,Integer>, Pair<Integer,Integer>>> pairs = new ArrayList<>();
        for (String dir : dirs) {
            String[] temp = dir.split(" -> ");
            String[] temp2 = temp[0].split(",");
            String[] temp3 = temp[1].split(",");
            Pair<Integer,Integer> p1 = new Pair<>(Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]));
            Pair<Integer,Integer> p2 = new Pair<>(Integer.parseInt(temp3[0]), Integer.parseInt(temp3[1]));
            if (part1 && (!Objects.equals(p1.x, p2.x) && !Objects.equals(p1.y, p2.y))) {
                continue;
            }

            pairs.add(new Pair<>(p1, p2));
        }

        return pairs;
    }

    public String part1() {
        List<Pair<Pair<Integer,Integer>, Pair<Integer,Integer>>> pairs = getInput(true);

        Set<Pair<Integer,Integer>> set = new HashSet<>();
        Set<Pair<Integer,Integer>> visited = new HashSet<>();
        for (Pair<Pair<Integer,Integer>, Pair<Integer,Integer>> pair : pairs) {
            Pair<Integer,Integer> current = pair.x;
            Pair<Integer,Integer> target = pair.y;

            while (!Objects.equals(current.x, target.x) || !Objects.equals(current.y, target.y)) {
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
                    visited.add(current);
                }
                set.add(current);

            }
        }

        System.out.println(set);

        //filter frequency set to only include pairs that have been visited more than once


        return String.valueOf(visited.size());

    }

    public String part2() {
        return "";
    }



}