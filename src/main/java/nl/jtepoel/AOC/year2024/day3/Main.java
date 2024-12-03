package nl.jtepoel.AOC.year2024.day3;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import nl.jtepoel.AOC.utils.FrequencySet;
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
        return utils.getLines(Utils.LOCSRC + "/year2024/day3/input.txt");
    }

    public String part1() {
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        List<String> input = getInput();
        String combined = String.join("", input);

        List<String> mults = new ArrayList<>();
        var matcher = pattern.matcher(combined);
        while (matcher.find()) {
            mults.add(matcher.group());
        }


        long sum = 0;
        for (String mult : mults) {
            String[] split = mult.split(",");
            long n = Long.parseLong(split[0].substring(4));
            long m = Long.parseLong(split[1].substring(0, split[1].length() - 1));
            sum += n * m;
        }



        return String.valueOf(sum);
    }


    public String part2() {
        Pattern pattern = Pattern.compile("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)|do\\(\\)|don't\\(\\)");
        List<String> input = getInput();
        String combined = String.join("", input);

        List<String> mults = new ArrayList<>();
        var matcher = pattern.matcher(combined);
        while (matcher.find()) {
            mults.add(matcher.group());
        }

        long sum = 0;
        boolean skip = false;
        for (String mult : mults) {

            if (mult.equals("do()")) {
                skip = false;
                continue;
            } else if (mult.equals("don't()")) {
                skip = true;
                continue;
            }

            if (skip) {
                continue;
            }

            String[] split = mult.split(",");
            long n = Long.parseLong(split[0].substring(4));
            long m = Long.parseLong(split[1].substring(0, split[1].length() - 1));
            sum += n * m;
        }





        return String.valueOf(sum);
    }



}