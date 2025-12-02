package nl.jtepoel.AOC.year2020.day2;


import java.util.List;

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
        return utils.getLines(Utils.LOCSRC + "/year2020/day2/input.txt");
    }

    public String part1() {
        List<String> input = getInput();
        long count = 0;
        for(String line : input) {
            String[] parts = line.split(": ");
            int lower = Integer.parseInt(parts[0].substring(0, line.indexOf("-")));
            int upper = Integer.parseInt(parts[0].substring(line.indexOf("-") + 1, line.indexOf(" ")));
            char letter = parts[0].substring(line.indexOf(" ") + 1).toCharArray()[0];

            int occurence = 0;
            for (int i = 0; i < parts[1].length(); i++) {
                if (letter == parts[1].toCharArray()[i]) {
                    occurence++;
                }
            }

            if (occurence >= lower && occurence <= upper) {
                count++;
            }
        }

        return String.format("%d", count);
    }

    public String part2() {
        List<String> input = getInput();
        long count = 0;
        for(String line : input) {
            String[] parts = line.split(": ");
            int lower = Integer.parseInt(parts[0].substring(0, line.indexOf("-")));
            int upper = Integer.parseInt(parts[0].substring(line.indexOf("-") + 1, line.indexOf(" ")));
            char letter = parts[0].substring(line.indexOf(" ") + 1).toCharArray()[0];
            char charAtLower = parts[1].charAt(lower-1);
            char charAtUpper = parts[1].charAt(upper-1);
            if (charAtUpper != charAtLower && (letter == charAtUpper || letter == charAtLower)) {
                count++;
            }
        }

        return String.format("%d", count);
    }



}