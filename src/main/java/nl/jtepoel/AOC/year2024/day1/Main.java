package nl.jtepoel.AOC.year2024.day1;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.jtepoel.AOC.utils.FrequencySet;
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

    public Pair<List<Integer>, List<Integer>> getInput() {
        List<String> strings = utils.getLines(Utils.LOCSRC + "/year2024/day1/input.txt");
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (String string : strings) {
            String[] split = string.split("\\s+");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));
        }

        left.sort(Integer::compareTo);
        right.sort(Integer::compareTo);

        return new Pair<>(left, right);
    }

    public String part1() {
        Pair<List<Integer>, List<Integer>> input = getInput();

        int totalDistance = 0;
        for (int i = 0; i < input.getLeft().size(); i++) {
            totalDistance += Math.abs(input.getLeft().get(i) - input.getRight().get(i));
        }

        return String.valueOf(totalDistance);
    }

    public String part2() {
        Pair<List<Integer>, List<Integer>> input = getInput();

        FrequencySet<Integer> left = new FrequencySet<>(input.getLeft());
        int intersection = input.getRight().stream().mapToInt(r -> r * left.get(r)).sum();

        return String.valueOf(intersection);
    }



}