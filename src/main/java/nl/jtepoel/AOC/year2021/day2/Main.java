package nl.jtepoel.AOC.year2021.day2;


import java.util.ArrayList;
import java.util.List;

import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        long part1 = main.part1();
        long part1Time = System.currentTimeMillis() - start;
        long part2 = main.part2();
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    public List<Pair<String, Integer>> getInput() {
        List<String> StringList = utils.getLines(Utils.LOCSRC + "/year2021/day2/input.txt");
        List<Pair<String, Integer>> res = new ArrayList<>();
        for (String s : StringList) {
            String[] tup = s.split(" ");
            res.add(new Pair<>(tup[0], Integer.parseInt(tup[1])));
        }

        return res;
    }

    public long part1() {
        List<Pair<String, Integer>> tupList = getInput();
        long depth = 0;
        long pos = 0;
        for (Pair<String, Integer> tup : tupList) {
            switch (tup.x) {
                case "forward":
                    pos += tup.y;
                    break;
                case "down":
                    depth += tup.y;
                    break;
                case "up":
                    depth -= tup.y;
                    break;
            }
        }

        return depth*pos;
    }

    public long part2() {
        List<Pair<String, Integer>> tupList = getInput();
        long aim = 0;
        long pos = 0;
        long depth = 0;
        for (Pair<String, Integer> tup : tupList) {
            switch (tup.x) {
                case "forward":
                    pos += tup.y;
                    depth += tup.y * aim;
                    break;
                case "down":
                    aim += tup.y;
                    break;
                case "up":
                    aim -= tup.y;
                    break;
            }
        }

        return depth*pos;
    }



}