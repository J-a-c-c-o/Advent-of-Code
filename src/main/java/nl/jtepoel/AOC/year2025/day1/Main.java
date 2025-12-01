package nl.jtepoel.AOC.year2025.day1;


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
        return utils.getLines(Utils.LOCSRC + "/year2025/day1/input.txt");
    }

    public String part1() {
        int count = 0;
        List<String> input = getInput();
        int dial = 50;

        for (String line : input) {
            char direction = line.charAt(0);
            int num = Integer.parseInt(line.substring(1));
            if (direction == 'L') {
                dial -= num;
            }  else if (direction == 'R') {
                dial += num;
            }

            dial = dial % 100;
            if (dial<0) dial += 100;

            if (dial == 0) count++;

        }

        return String.valueOf(count);
    }

    public String part2() {
        int count = 0;
        List<String> input = getInput();
        int dial = 50;

        for (String line : input) {
            char direction = line.charAt(0);
            int num = Integer.parseInt(line.substring(1));
            if (direction == 'L') {
                for (int i = 0; i < num; i++) {
                    dial--;
                    if (dial == 100){
                        dial = 0;
                    }
                    if (dial == -1) {
                        dial = 99;
                    }
                    if (dial == 0) {
                        count++;
                    }
                }
            }  else if (direction == 'R') {
                for (int i = 0; i < num; i++) {
                    dial++;
                    if (dial == 100){
                        dial = 0;
                    }
                    if (dial == -1) {
                        dial = 99;
                    }
                    if (dial == 0) {
                        count++;
                    }
                }
            }


        }

        return String.valueOf(count);
    }




}