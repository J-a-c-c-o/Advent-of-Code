package nl.jtepoel.AOC.year2024.day19;


import java.util.ArrayList;
import java.util.HashMap;
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

    public Pair<String, List<String>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day19/input.txt");
        String first = lines.getFirst();

        List<String> out = new ArrayList<>();
        for (int i = 2; i < lines.size(); i++) {
            out.add(lines.get(i));
        }

        return Pair.of(first, out);
    }

    public String part1() {
        Pair<String, List<String>> input = getInput();
        String first = input.getFirst();
        String[] rules = first.split(", ");
        List<String> version = input.getSecond();

        int count = 0;
        for (String ver : version) {

            if (possible(rules, ver)) {
                count++;
            }
        }

        return String.valueOf(count);

    }

    public String part2() {
        Pair<String, List<String>> input = getInput();
        String first = input.getFirst();
        String[] rules = first.split(", ");
        List<String> version = input.getSecond();

        long count = 0;
        for (String ver : version) {
            count += possibleCount(rules, ver);
        }

        return String.valueOf(count);

    }

    HashMap<String, Boolean> memo = new HashMap<>();
    public boolean possible(String[] rules, String version) {
        if (version.isEmpty()) {
            return true;
        }

        if (memo.containsKey(version)) {
            return memo.get(version);
        }



        for (String rule : rules) {
            boolean possible = false;
            if (version.startsWith(rule)) {
                possible = possible(rules, version.substring(rule.length()));
            }

            if (possible) {

                memo.put(version, true);
                return true;
            }
        }

        memo.put(version, false);

        return false;
    }

    HashMap<String, Long> memoCount = new HashMap<>();
    public long possibleCount(String[] rules, String version) {
        if (version.isEmpty()) {
            return 1;
        }

        if (memoCount.containsKey(version)) {
            return memoCount.get(version);
        }


        long count = 0;
        for (String rule : rules) {
            if (version.startsWith(rule)) {
                count += possibleCount(rules, version.substring(rule.length()));
            }
        }

        memoCount.put(version, count);

        return count;
    }

}