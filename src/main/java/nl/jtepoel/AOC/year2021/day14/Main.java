package nl.jtepoel.AOC.year2021.day14;


import java.util.HashMap;
import java.util.List;

import nl.jtepoel.AOC.utils.FrequencySetLong;
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

    public Pair<HashMap<String, String>, String> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2021/day14/input.txt");
        HashMap<String, String> map = new HashMap<>();
        String init = lines.get(0);

        for (int i = 2; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(" -> ");
            map.put(parts[0], parts[1]);
        }

        return new Pair<>(map, init);
    }

    public String part1() {
        Pair<HashMap<String, String>, String> input = getInput();
        HashMap<String, String> tempMap = input.x;

        HashMap<String, String> map = new HashMap<>();
        for (String key : tempMap.keySet()) {
            String value = tempMap.get(key);
            map.put(key, key.charAt(0) + value + key.charAt(1));
        }

        String init = input.y;

        HashMap<Pair<String, Integer>, FrequencySetLong<Character>> cache = new HashMap<>();

        FrequencySetLong<Character> freq = calculate(init, map, cache, 10);



        return String.valueOf(freq.get(freq.getLargest()) - freq.get(freq.getSmallest()));
    }

    public String part2() {
        Pair<HashMap<String, String>, String> input = getInput();
        HashMap<String, String> tempMap = input.x;

        HashMap<String, String> map = new HashMap<>();
        for (String key : tempMap.keySet()) {
            String value = tempMap.get(key);
            map.put(key, key.charAt(0) + value + key.charAt(1));
        }

        String init = input.y;

        HashMap<Pair<String, Integer>, FrequencySetLong<Character>> cache = new HashMap<>();

        FrequencySetLong<Character> freq = calculate(init, map, cache, 40);



        return String.valueOf(freq.get(freq.getLargest()) - freq.get(freq.getSmallest()));
    }

    private FrequencySetLong<Character> calculate(String init, HashMap<String, String> map, HashMap<Pair<String, Integer>, FrequencySetLong<Character>> cache, int depth) {
        if (depth == 0) {
            return new FrequencySetLong<>(init.toCharArray());
        }

        if (cache.containsKey(new Pair<>(init, depth))) {
            return cache.get(new Pair<>(init, depth));
        }

        FrequencySetLong<Character> freq = new FrequencySetLong<>(init.toCharArray());

        for (int j = 1; j < init.length(); j++) {
            String sub = init.substring(j-1, j + 1);
            if (map.containsKey(sub)) {
                freq.addAll(calculate(map.get(sub), map, cache, depth - 1));
                freq.reduce(init.charAt(j));
                freq.reduce(init.charAt(j-1));
            } else {
                throw new RuntimeException("Invalid input");
            }
        }


        cache.put(new Pair<>(init, depth), freq);

        return freq;
    }


}