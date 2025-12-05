package nl.jtepoel.AOC.year2025.day5;


import java.util.*;

import nl.jtepoel.AOC.utils.Pair;
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

    public Pair<List<Pair<Long, Long>>, List<Long>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2025/day5/input.txt");

        List<Pair<Long, Long>> pairs = new ArrayList<>();
        List<Long> single = new ArrayList<>();
        boolean space = false;
        for (String s : lines) {
            if (Objects.equals(s, "")) {
                space = true;
                continue;
            }
            if (!space) {
                String[] parts = s.split("-");
                pairs.add(new Pair<>(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
            } else {
                single.add(Long.parseLong(s));
            }
        }

        return new Pair<>(pairs, single);
    }

    public String part1() {
        Pair<List<Pair<Long, Long>>, List<Long>> pairs = getInput();
        List<Pair<Long, Long>> ranges = pairs.getFirst();
        List<Long> single = pairs.getSecond();

        int sum = 0;
        for (Long ingredient : single) {
            for (Pair<Long,Long> range : ranges) {
                if (range.x <= ingredient && ingredient <= range.y) {
                    sum++;
                    break;
                }
            }
        }

        return String.valueOf(sum);
    }

    public String part2() {
        Pair<List<Pair<Long, Long>>, List<Long>> pairs = getInput();
        List<Pair<Long, Long>> ranges = pairs.x;
        List<Pair<Long, Long>> combined = utils.simplifyRanges(ranges);
        long sum = 0;
        for (Pair<Long,Long> range : combined) {
            long difference = Math.abs(range.x - range.y) + 1;
            sum += difference;
        }

        return String.valueOf(sum);
    }


}