package year2019.day4;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Utils;

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

    public String getInput() {
        return utils.getLines("src/main/java/year2019/day4/input.txt").getFirst();
    }

    public String part1() {
        String rangeString = getInput();
        String[] rangeStr = rangeString.split("-");
        int[] range = {Integer.parseInt(rangeStr[0]), Integer.parseInt(rangeStr[1])};

        List<Integer> accepted = new ArrayList<>();
        for (int i = range[0]; i <= range[1]; i++) {
            if (doubleInt(i) && increasing(i)) {
                accepted.add(i);
            }
        }

        return String.valueOf(accepted.size());

    }

    private boolean increasing(int pass) {
        char[] charArray = String.valueOf(pass).toCharArray();
        for (int i = 1; i < charArray.length; i++) {
            if (charArray[i] < charArray[i-1]) {
                return false;
            }
        }

        return true;
    }

    private boolean doubleInt(int pass) {
        char[] charArray = String.valueOf(pass).toCharArray();
        HashMap<Character, Integer> doubles = new HashMap<>();;
        for (int i = 1; i < charArray.length; i++) {
            if (charArray[i] == charArray[i-1]) {
                doubles.put(charArray[i], doubles.getOrDefault(charArray[i], 1) + 1);
            }
        }

        return doubles.entrySet().stream().anyMatch(entry -> entry.getValue() == 2);
    }

    public String part2() {
        return "";
    }



}