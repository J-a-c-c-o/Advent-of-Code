package nl.jtepoel.AOC.year2024.day11;


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

    public List<Long> getInput() {
        String line = utils.getLines(Utils.LOCSRC + "/year2024/day11/input.txt").getFirst();
        String[] split = line.split(" ");
        List<Long> out = new ArrayList<>();
        for (String s : split) {
            out.add(Long.parseLong(s));
        }

        return out;
    }

    public String part1() {
        List<Long> input = getInput();
        long res = 0;
        for (Long l : input) {
            res += blink(l, 25);
        }

        return String.valueOf(res);
    }

    public String part2() {
        List<Long> input = getInput();
        long res = 0;
        for (Long l : input) {
            res += blink(l, 75);
        }

        return String.valueOf(res);
    }

    HashMap<Pair<Long,Integer>, Long> cache = new HashMap<>();
    private long blink(Long current, int depth) {
        if (depth == 0) {
            return 1;
        }

        if (cache.containsKey(Pair.of(current, depth))) {
            return cache.get(Pair.of(current, depth));
        }

        List<Long> newInput = new ArrayList<>();

        String s = current.toString();
        if (current == 0) {
            newInput.add(1L);
        } else if (s.length() % 2 == 0) {
            // split it in half so 1000 becomes 10 and 0 or 1010 becomes 10 and 10
            int half = s.length() / 2;
            newInput.add(Long.parseLong(s.substring(0, half)));
            newInput.add(Long.parseLong(s.substring(half)));
        } else {
            newInput.add(current * 2024);
        }

        long res = 0;
        for (Long l : newInput) {
            res += blink(l, depth - 1);
        }
        cache.put(Pair.of(current, depth), res);
        return res;
    }


}