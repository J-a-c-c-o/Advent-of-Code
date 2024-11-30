package nl.jtepoel.AOC.year2023.day18;


import java.util.ArrayList;
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

    public List<Pair<Character, Integer>> getInput(boolean part1) {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2023/day18/input.txt");

        List<Pair<Character, Integer>> instructions = new ArrayList<>();

        for (String line : lines) {
            String[] split = line.split(" ");

            int amount = Integer.parseInt(split[1]);
            char endChar = split[0].charAt(0);


            if (!part1) {

                StringBuilder hex = new StringBuilder();
                int end = 0;
                for (int i = 2; i < split[2].length() - 1; i++) {

                    if (i == split[2].length() - 2) {
                        end = Integer.parseInt(split[2].charAt(i) + "");
                        continue;
                    }

                    hex.append(split[2].charAt(i));
                }

                //convert hex to int
                amount = Integer.parseInt(hex.toString(), 16);

                if (end == 0) {
                    endChar = 'R';
                } else if (end == 1) {
                    endChar = 'D';
                } else if (end == 2) {
                    endChar = 'L';
                } else if (end == 3) {
                    endChar = 'U';
                }
            }



            instructions.add(new Pair<>(endChar, amount));
        }


        return instructions;


    }




    public String part1() {

        List<Pair<Character, Integer>> instructions = getInput(true);

        return getArea(instructions) + "";
    }

    public String part2() {

        List<Pair<Character, Integer>> instructions = getInput(false);

        return getArea(instructions) + "";
    }

    private long getArea(List<Pair<Character, Integer>> instructions) {
        long tempArea = 0;
        long bp = 0;


        long startX = 0;
        long startY = 0;

        long x = 0;
        long y = 0;

        for (Pair<Character, Integer> instruction : instructions) {
            char endChar = instruction.x;
            int start = instruction.y;


            if (endChar == 'R') {
                x += start;
            } else if (endChar == 'D') {
                y += start;
            } else if (endChar == 'L') {
                x -= start;
            } else if (endChar == 'U') {
                y -= start;
            }

            bp += start;

            //shoelace formula
            tempArea += (startX * y - x * startY);

            startX = x;
            startY = y;

        }

        long area = tempArea / 2;


        return area + 1 + bp/2;
    }


}