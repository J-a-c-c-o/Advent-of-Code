package nl.jtepoel.AOC.year2024.day21;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Utils;
import nl.jtepoel.AOC.year2024.day21.pad.DirectionPad;
import nl.jtepoel.AOC.year2024.day21.pad.NumericalPad;

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

    public List<String> getInput() {
        return utils.getLines(Utils.LOCSRC + "/year2024/day21/input.txt");
    }


    public String part1() {
        List<String> input = getInput();


        long sum = calculateSum(input, 2);


        return String.valueOf(sum);
    }

    public String part2() {
        List<String> input = getInput();


        long sum = calculateSum(input, 25);


        return String.valueOf(sum);
    }

    private long calculateSum(List<String> input, int depth) {
        List<Long> result = new ArrayList<>();
        List<Long> numbers = new ArrayList<>();
        for (String line : input) {
            result.add(calculate(line, depth));
            String removed = line.replaceAll("[^0-9]", "");
            numbers.add(Long.parseLong(removed));
        }

        long sum = 0;

        for (int i = 0; i < result.size(); i++) {
            sum += numbers.get(i) * result.get(i);
        }
        return sum;
    }

    HashMap<Pair<String, Integer>, Long> cache = new HashMap<>();


    public Long calculate(String line, int depth) {
        NumericalPad pad = new NumericalPad();
        long shortest = 0L;

        for (Character c : line.toCharArray()) {
            long shortestLength = Long.MAX_VALUE;
            List<String> code = pad.buildCode(c);
            for (String s : code) {
                DirectionPad directionPad = new DirectionPad();
                long r = dps(s, directionPad, depth);
                if (r < shortestLength) {
                    shortestLength = r;
                }

            }

            shortest += shortestLength;
        }

        return shortest;
    }


    private long dps(String s, DirectionPad directionPad, int depth) {

        if (cache.containsKey(new Pair<>(s, depth))) {
            return cache.get(new Pair<>(s, depth));
        }

        if (depth == 0) {
            return s.length();
        }
        long shortest = 0L;
        for (Character c : s.toCharArray()) {
            long shortestLength = Long.MAX_VALUE;
            List<String> code = directionPad.buildCode(c);
            for (String s1 : code) {
                DirectionPad newPad = new DirectionPad();
                long res = dps(s1, newPad, depth - 1);
                if (res < shortestLength) {
                    shortestLength = res;
                }
            }


            shortest += shortestLength;
        }


        cache.put(new Pair<>(s, depth), shortest);


        return shortest;

    }



}