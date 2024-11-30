package nl.jtepoel.AOC.year2023.day1;


import java.util.ArrayList;
import java.util.List;

import nl.jtepoel.AOC.utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public List<String> getInput() {
        return utils.getLines(Utils.LOCSRC + "/year2023/day1/input.txt");
    }

    public String part1() {
        return findNumbers(true);
    }

    public String part2() {
        return findNumbers(false);
    }

    private String findNumbers(boolean part1) {
        List<String> input = getInput();

        int sum = 0;
        for (String line : input) {
            List<String> result = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {
                if (Character.isDigit(line.charAt(i))) {
                    result.add(String.valueOf(line.charAt(i)));
                } else if (!part1) {
                    if (i + 2 < line.length()) {
                        String threeLetter = String.valueOf(line.charAt(i)) + line.charAt(i + 1) + line.charAt(i + 2);

                        switch (threeLetter) {
                            case "one" -> result.add("1");
                            case "two" -> result.add("2");
                            case "six" -> result.add("6");
                        }

                    }


                    if (i + 3 < line.length()) {
                        String fourLetters = String.valueOf(line.charAt(i)) + line.charAt(i + 1) + line.charAt(i + 2) + line.charAt(i + 3);
                        switch (fourLetters) {
                            case "four" -> result.add("4");
                            case "five" -> result.add("5");
                            case "nine" -> result.add("9");
                        }

                    }

                    if (i + 4 < line.length()) {
                        String fiveLetter = String.valueOf(line.charAt(i)) + line.charAt(i + 1) + line.charAt(i + 2) + line.charAt(i + 3) + line.charAt(i + 4);
                        switch (fiveLetter) {
                            case "three" -> result.add("3");
                            case "seven" -> result.add("7");
                            case "eight" -> result.add("8");
                            case "zero" -> result.add("0");
                        }
                    }

                }
            }
            int first = Integer.parseInt(result.get(0));
            int last = Integer.parseInt(result.get(result.size() - 1));

            sum = sum + first*10 + last;
        }

        return String.valueOf(sum);
    }





}
