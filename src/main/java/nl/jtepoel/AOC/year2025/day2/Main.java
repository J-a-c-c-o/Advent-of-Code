package nl.jtepoel.AOC.year2025.day2;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<Pair<Long, Long>> getInput() {
        String line = utils.getLines(Utils.LOCSRC + "/year2025/day2/input.txt").getFirst();
        List<String> lines = Arrays.asList(line.split(","));
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        for (String s : lines) {
            String[] parts = s.split("-");
            pairs.add(new Pair<>(Long.parseLong(parts[0]), Long.parseLong(parts[1])));
        }

        return  pairs;
    }

    public String part1() {
        List<Pair<Long, Long>> pairs = getInput();
        long count_invalid = 0;
        for (Pair<Long, Long> pair : pairs) {
            for (long i = pair.x; i <= pair.y; i++) {
                long length = (long)(Math.log10(i) + 1);
                long mid = length / 2;
                if (mid*2 != length) {
                    continue;
                }

                long left_of_i = (long) (i / Math.pow(10, mid));
                long right_of_i = (long) (i % Math.pow(10, mid));
                if (left_of_i == right_of_i) {
                    count_invalid+=i;
                }

            }
        }

        return count_invalid + "";
    }

    public String part2() {
        List<Pair<Long, Long>> pairs = getInput();
        long count_invalid = 0;
        for (Pair<Long, Long> pair : pairs) {
            for (long i = pair.x; i <= pair.y; i++) {
                Long sequence = utils.getRepeatingPattern(i);
                if (sequence != -1L) {
                    count_invalid+=i;
                }

            }
        }

        return count_invalid + "";
    }
}


