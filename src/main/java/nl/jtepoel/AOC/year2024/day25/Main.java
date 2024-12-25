package nl.jtepoel.AOC.year2024.day25;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

    public Pair<List<List<Integer>>, List<List<Integer>>> getInput() {
        List<List<Integer>> pins = new ArrayList<>();
        List<List<Integer>> keys = new ArrayList<>();
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day25/input.txt");

        boolean isPins = true;

        List<Integer> temp = new ArrayList<>();
        for (int i = 0; i < lines.getFirst().length(); i++) {
            temp.add(-1);
        }


        for (String line : lines) {
            if (line.isEmpty()) {
                if (isPins) {
                    pins.add(temp);
                } else {
                    keys.add(temp);
                }

                temp = new ArrayList<>();
                continue;
            }

            if (temp.isEmpty()) {
                for (int i = 0; i < line.length(); i++) {
                    temp.add(-1);
                }
                // check if the first line has . or # in it
                // if it has . it is a key and not a pin
                isPins = !line.contains(".");
            }

            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                // count the number of # per column
                if (c == '#') {
                    temp.set(i, temp.get(i) + 1);
                }
            }
        }

        if (isPins) {
            pins.add(temp);
        } else {
            keys.add(temp);
        }



        return Pair.of(pins, keys);

    }

    public String part1() {
        Pair<List<List<Integer>>, List<List<Integer>>> input = getInput();
        List<List<Integer>> pins = input.getFirst();
        List<List<Integer>> keys = input.getSecond();

        int sum = 0;

        for (List<Integer> pin : pins) {
            for (List<Integer> key : keys) {

                boolean valid = IntStream.range(0, pin.size())
                        .allMatch(i -> pin.get(i) + key.get(i) <= 5);

                if (valid) {
                    sum++;
                }
            }
        }

        return String.valueOf(sum);
    }

    public String part2() {
        return "Merry Christmas!";
    }



}