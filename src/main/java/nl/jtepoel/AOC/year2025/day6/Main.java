package nl.jtepoel.AOC.year2025.day6;


import java.util.ArrayList;
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
        return utils.getLines(Utils.LOCSRC + "/year2025/day6/input.txt");
    }

    public String part1() {
        List<String> input = getInput();
        List<List<Long>> numberss = new ArrayList<>();
        String lastLine = input.removeLast();

        List<List<Long>> numbersList = new ArrayList<>();
        for (String line : input) {
            line = line.trim();
            String[] parts = line.split(" +");
            List<Long> numbers = new ArrayList<>();
            for (String part : parts) {
                long num = Long.parseLong(part);
                numbers.add(num);
            }
            numbersList.add(numbers);
        }

        int cols = numbersList.get(0).size();
        for (int c = 0; c < cols; c++) {
            List<Long> transposedRow = new ArrayList<>();
            for (List<Long> row : numbersList) {
                transposedRow.add(row.get(c));
            }
            numberss.add(transposedRow);
        }


        List<Character> symbols = new ArrayList<>();
        String[] symbolParts = lastLine.trim().split(" +");
        for (String part : symbolParts) {
            char ch = part.charAt(0);
            symbols.add(ch);
        }

        long total = getTotal(symbols, numberss);

        return String.valueOf(total);

    }

    public String part2() {
        List<String> input = getInput();
        StringBuilder build = new StringBuilder();

        String lastLine = input.removeLast();
        for (int i = 0; i < input.getFirst().length(); i++) {
            for (String s : input) {
                char ch = s.charAt(i);
                build.append(ch);
            }
            build.append(",");
        }

        String[] parts = build.toString().split(",");
        List<List<Long>> numbers = new ArrayList<>();
        List<Long> num = new ArrayList<>();
        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) {
                numbers.add(num);
                num = new ArrayList<>();
                continue;
            }
            long n = Long.parseLong(part);
            num.add(n);
        }

        if (!num.isEmpty()) {
            numbers.add(num);
        }

        List<Character> symbols = new ArrayList<>();
        String[] symbolParts = lastLine.trim().split(" +");
        for (String part : symbolParts) {
            char ch = part.charAt(0);
            symbols.add(ch);
        }


        long total = getTotal(symbols, numbers);


        return  String.valueOf(total);
    }

    private static long getTotal(List<Character> symbols, List<List<Long>> numbers) {


        long total = 0L;
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i) == '+') {
                long sum = 0L;
                for (Long number : numbers.get(i)) {
                    sum += number;
                }
                total += sum;
            } else if (symbols.get(i) == '*') {
                long product = 1L;
                for (Long number : numbers.get(i)) {
                    product *= number;
                }
                total += product;
            }
        }
        return total;
    }


}