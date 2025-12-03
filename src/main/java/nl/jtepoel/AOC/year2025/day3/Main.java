package nl.jtepoel.AOC.year2025.day3;


import java.util.Arrays;
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
        return utils.getLines(Utils.LOCSRC + "/year2025/day3/input.txt");
    }

    public String part1() {
        List<String> input = getInput();
        long sum = getBiggestJoltages(input, 2);

        return String.valueOf(sum);
    }

    public String part2() {
        List<String> input = getInput();
        long sum = getBiggestJoltages(input, 12);

        return String.valueOf(sum);
    }

    private static long getBiggestJoltages(List<String> input, int n) {
        long sum = 0;
        for (String line : input) {
            int[] highest = new int[n];
            Arrays.fill(highest, -1);
            for (int i = 0; i < line.length(); i++) {
                int value = line.charAt(i) - '0';
                for (int j = 0; j < highest.length; j++) {
                    if (highest[j] < value &&  line.length() - i >= n - j) {
                        for (int k = j; k < highest.length; k++) {
                            highest[k] = -1;
                        }
                        highest[j] = value;
                        break;
                    }
                }

            }
            for (int i = 0; i < n; i++) {
                sum += (long) (highest[(n-1)-i] * (Math.pow(10, i)));
            }
        }
        return sum;
    }


}