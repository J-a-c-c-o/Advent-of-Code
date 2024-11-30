package nl.jtepoel.AOC.year2023.day12;


import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {


    private final Map<Pair<Integer, Integer>, Long> cache = new HashMap<>();
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

    public List<Pair<char[], long[]>> getInput() {
        List<String> input = utils.getLines(Utils.LOCSRC + "/year2023/day12/input.txt");

        List<Pair<char[], long[]>> pairs = new ArrayList<>();

        for (String s : input) {
            String[] split = s.split(" ");
            char[] chars = split[0].toCharArray();
            String[] ints = split[1].split(",");
            long[] group = new long[ints.length];
            for (int i = 0; i < ints.length; i++) {
                group[i] = Long.parseLong(ints[i]);
            }

            Pair<char[], long[]> pair = new Pair<>(chars, group);
            pairs.add(pair);
        }

        return pairs;
    }

    public String part1() {
        List<Pair<char[], long[]>> input = getInput();


        long sum = 0;
        for (Pair<char[], long[]> pair : input) {
            cache.clear();
            sum += findPossibleArrays(pair, 0, 0);
        }

        return sum + "";

    }

    public String part2() {
        List<Pair<char[], long[]>> input = getInput();

        List<Pair<char[], long[]>> newInput = new ArrayList<>();

        for (Pair<char[], long[]> pair : input) {

            char[] chars = pair.x;

            long[] group = pair.y;
            long[] newGroup = new long[group.length*5];

            List<Character> newChars = new ArrayList<>();

            int index = 0;
            for (int i = 0; i < 5; i++) {
                for (long k : group) {
                    newGroup[index] = k;
                    index++;
                }
            }

            for (int i = 0; i < 5; i++) {
                for (char c : chars) {
                    newChars.add(c);
                }
                newChars.add('?');
            }

            newChars.remove(newChars.size()-1);

            char[] newCharsArray = new char[newChars.size()];

            for (int i = 0; i < newChars.size(); i++) {
                newCharsArray[i] = newChars.get(i);
            }


            newInput.add(new Pair<>(newCharsArray, newGroup));


        }

        input = newInput;

        long sum = 0;
        for (Pair<char[], long[]> pair : input) {
            cache.clear();
            sum += findPossibleArrays(pair, 0, 0);
        }

        return sum + "";
    }


    public enum State {
        VALID,
        INVALID,
        UNKNOWN
    }


    private long findPossibleArrays(Pair<char[], long[]> pair, int index, int numberIndex) {
        // if we are done with adding #, we still have to check if the rest is empty if it is we have a valid option

        char[] possible = pair.x;
        long[] numbers = pair.y;

        State complete = isEnd(index, numberIndex, possible, numbers);

        if (complete == State.VALID) {
            return 1;
        } else if (complete == State.INVALID) {
            return 0;
        }

        // Check if we have already computed this
        var key = new Pair<>(index, numberIndex);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        long res = 0;
        State state = State.UNKNOWN;

        // Check validity of the current position
        for (int i = index; i < numbers[numberIndex] + index; i++) {
            if (possible.length <= i || possible[i] == '.') {
                state = State.INVALID;
                break;
            }
        }

        // Recursively explore valid options
        if (state == State.UNKNOWN && (possible.length <= index + numbers[numberIndex] || possible[(int) (index + numbers[numberIndex])] != '#')) {
            res += findPossibleArrays(new Pair<>(possible, numbers), (int) (index + numbers[numberIndex] + 1), numberIndex + 1);
        }

        // Explore the next position
        res += findPossibleArrays(new Pair<>(possible, numbers), index + 1, numberIndex);


        // Save the result
        cache.put(key, res);
        return res;
    }

    private State isEnd(int index, int numberIndex, char[] possible, long[] numbers) {
        if (numberIndex == numbers.length) {
            for (int i = index; i < possible.length; i++) {
                if (possible[i] == '#') {
                    return State.INVALID;
                }
            }
            return State.VALID;
        }

        // Check if we have enough space
        if (possible.length - index < numbers[numberIndex]) {
            return State.INVALID;
        }

        if (index > 0 && possible[index-1] == '#') {
            return State.INVALID;
        }
        return State.UNKNOWN;
    }


}