package nl.jtepoel.AOC.year2024.day7;


import java.util.ArrayList;
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

    public List<Pair<Long, List<Long>>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day7/input.txt");
        List<Pair<Long, List<Long>>> rules = new ArrayList<>();
        for (String line : lines) {
            String[] sp = line.split(": ");
            long key = Long.parseLong(sp[0]);
            String[] values = sp[1].split(" ");
            List<Long> valuesL = new ArrayList<>();
            for (String v : values) {
                valuesL.add(Long.parseLong(v));
            }

            rules.add(new Pair<>(key, valuesL));
        }

        return rules;
    }

    public String part1() {
        List<Pair<Long, List<Long>>> rules = getInput();
        long sum = 0;
        for (Pair<Long, List<Long>> rule : rules) {
            List<Long> combinations = combinations(new ArrayList<>(rule.getSecond()).reversed(), false);
            if (combinations.contains(rule.getFirst())) {
                sum += rule.getFirst();
            }
        }


        return String.valueOf(sum);
    }

    public String part2() {
        List<Pair<Long, List<Long>>> rules = getInput();
        long sum = 0;
        for (Pair<Long, List<Long>> rule : rules) {
            List<Long> combinations = combinations(new ArrayList<>(rule.getSecond()).reversed(), true);
            if (combinations.contains(rule.getFirst())) {
                sum += rule.getFirst();
            }
        }

        return String.valueOf(sum);
    }

    public List<Long> combinations(List<Long> numbers, boolean part2) {
        if (numbers.size() == 1) {
            return new ArrayList<>(List.of(numbers.getFirst()));
        }

        List<Long> result = new ArrayList<>();
        long first = numbers.removeFirst();
        List<Long> rest = combinations(numbers, part2);
        for (long r : rest) {
            result.add(r * first);
            result.add(r + first);
            if (part2) {
                result.add(Long.parseLong(String.valueOf(r).concat(String.valueOf(first))));
            }
        }

        return result;
    }



}