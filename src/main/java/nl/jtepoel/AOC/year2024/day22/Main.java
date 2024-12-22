package nl.jtepoel.AOC.year2024.day22;


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

    public List<Long> getInput() {
        return utils.getLongs(Utils.LOCSRC + "/year2024/day22/input.txt");
    }

    public String part1() {
        List<Long> input = getInput();

        long sum = 0;
        for (long i : input) {
            sum += calculateSecret(i, 2000);
        }


        return String.valueOf(sum);

    }

    HashMap<Pair<Long, Integer>, Long> cache = new HashMap<>();
    public long calculateSecret(long secret, int depth) {
        if (depth == 0) {
            return secret;
        }

        if (cache.containsKey(new Pair<>(secret, depth))) {
            return cache.get(new Pair<>(secret, depth));
        }


        secret = (secret ^ (secret * 64)) % 16777216;
        secret = (secret ^ (secret / 32)) % 16777216;
        secret = (secret ^ (secret * 2048)) % 16777216;


        cache.put(new Pair<>(secret, depth), secret);

        return calculateSecret(secret, depth - 1);
    }


    public String part2() {
        List<Long> input = getInput();


        for (long i : input) {
            seen.clear();
            calculateSecret2(i, 2000, new ArrayList<>(), i%10);
        }

        long largestValue = map.values().stream().max(Long::compareTo).orElse(0L);


        return String.valueOf(largestValue);

    }


    HashMap<List<Long>, Long> map = new HashMap<>();
    Set<List<Long>> seen = new HashSet<>();
    public long calculateSecret2(long secret, int depth, List<Long> difrences, long previous) {

        if (depth == 0) {
            return secret;
        }


        secret = (secret ^ (secret * 64)) % 16777216;
        secret = (secret ^ (secret / 32)) % 16777216;
        secret = (secret ^ (secret * 2048)) % 16777216;

        long price = secret % 10;
        difrences.add(price - previous);
        if (difrences.size() > 4) {
            difrences.removeFirst();
        }

        if (difrences.size() == 4) {
            if (!seen.contains(difrences)) {
                map.put(List.copyOf(difrences), price + map.getOrDefault(difrences, 0L));
                seen.add(List.copyOf(difrences));
            }
        }


        return calculateSecret2(secret, depth - 1, difrences, price);
    }

}