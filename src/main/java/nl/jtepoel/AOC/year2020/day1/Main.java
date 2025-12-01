package nl.jtepoel.AOC.year2020.day1;


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

    public List<Long> getInput() {
        return utils.getLongs(Utils.LOCSRC + "/year2020/day1/input.txt");
    }

    public String part1() {
        List<Long> input1 = getInput();
        for (int i = 1; i < input1.size(); i++) {
            for (int j = i+1; j < input1.size(); j++) {
                if (input1.get(i) + input1.get(j) == 2020) {
                    return (input1.get(i) * input1.get(j)) + "";
                }
            }
        }

        return "";
    }

    public String part2() {
        List<Long> input1 = getInput();
        for (int i = 1; i < input1.size(); i++) {
            for (int j = i+1; j < input1.size(); j++) {
                for (int t = j+1; t < input1.size(); t++) {
                    if (input1.get(i) + input1.get(j) + input1.get(t) == 2020) {
                        return (input1.get(i) * input1.get(j) * input1.get(t)) + "";
                    }
                }
            }
        }

        return "";
    }



}