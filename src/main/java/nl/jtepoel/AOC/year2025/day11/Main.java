package nl.jtepoel.AOC.year2025.day11;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import nl.jtepoel.AOC.utils.Triple;
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

    public List<String> getInput() {
        return utils.getLines(Utils.LOCSRC + "/year2025/day11/input.txt");
    }

    public String part1() {
        HashMap<String, String[]> graph = buildGraph(getInput());
        return "" + recur("you", graph, new HashSet<>(), new HashMap<>(), true, true);
    }



    public String part2() {
        HashMap<String, String[]> graph = buildGraph(getInput());
        return "" + recur("svr", graph, new HashSet<String>(), new HashMap<>(), false, false);
    }

    private HashMap<String, String[]> buildGraph(List<String> input) {

        HashMap<String, String[]> map = new HashMap<>();
        for (String line : input) {
            String[] parts = line.split(": ");
            String key = parts[0];
            String value = parts[1];
            String[] values = value.split(" ");

            map.put(key, values);
        }

        return map;
    }

    private long recur(String start, HashMap<String, String[]> graph, HashSet<String> visited, HashMap<Triple<String,Boolean,Boolean>, Long> cache, boolean foundFFT, boolean foundDAC) {
        if (start.equals("out")) {
            if (foundFFT && foundDAC) {
                return 1;
            }

            return 0;
        }
        if (visited.contains(start))
            return 0;
        visited.add(start);

        if (cache.containsKey(new Triple<>(start, foundDAC, foundFFT))) {
            return cache.get(new Triple<>(start, foundDAC, foundFFT));
        }

        if (start.equals("dac")) {
            foundDAC = true;
        }

        if  (start.equals("fft")) {
            foundFFT = true;
        }

        String[] values = graph.get(start);
        long sum = 0;
        for (String value : values) {
            long cur = recur(value, graph, new HashSet<>(visited), cache, foundFFT, foundDAC);

            sum += cur;
        }

        cache.put(new Triple<>(start, foundDAC, foundFFT), sum);

        return sum;


    }



}