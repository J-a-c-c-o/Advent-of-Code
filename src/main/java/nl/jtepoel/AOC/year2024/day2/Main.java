package nl.jtepoel.AOC.year2024.day2;


import java.util.ArrayList;
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

    public List<List<Integer>> getInput() {
        List<String> strings =  utils.getLines(Utils.LOCSRC + "/year2024/day2/input.txt");
        List<List<Integer>> input = new ArrayList<>();
        for (String string : strings) {
            String[] split = string.split(" ");
            List<Integer> line = new ArrayList<>();
            for (String s : split) {
                line.add(Integer.parseInt(s));
            }
            input.add(line);
        }

        return input;
    }

    public String part1() {
        List<List<Integer>> input = getInput();
        int count = 0;
        for (List<Integer> line : input) {
            boolean i = increasing(line, 3);
            boolean d = decreasing(line, 3);
            if (i || d) {
                count++;
            }
        }
        return String.valueOf(count);
    }
    private boolean increasing(List<Integer> line, int i) {
        for (int j = 0; j < line.size() - 1; j++) {
            int difference = line.get(j + 1) - line.get(j);

            if (difference > i || difference <= 0) {
                return false;
            }
        }
        return true;
    }

    private boolean decreasing(List<Integer> line, int i) {
        for (int j = 0; j < line.size() - 1; j++) {

            int difference = line.get(j) - line.get(j + 1);
            if (difference > i || difference <= 0) {
                return false;
            }
        }
        return true;
    }

    public String part2() {
        List<List<Integer>> input = getInput();
        int count = 0;
        for (List<Integer> line : input) {
            boolean isSafe = increasing(line, 3) || decreasing(line, 3);

            if (isSafe) {
                count++;
                continue;
            }

            for (int i = 0; i < line.size(); i++) {
                List<Integer> temp = new ArrayList<>(line);
                temp.remove(i);
                if (increasing(temp, 3) || decreasing(temp, 3)) {
                    isSafe = true;
                    break;
                }
            }

            if (isSafe) {
                count++;
            }
        }
        return String.valueOf(count);

    }




}