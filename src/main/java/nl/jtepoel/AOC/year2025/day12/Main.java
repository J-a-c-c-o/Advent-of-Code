package nl.jtepoel.AOC.year2025.day12;


import java.util.*;
import java.util.stream.Collectors;

import nl.jtepoel.AOC.utils.Grid;
import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Utils;
import org.antlr.runtime.CharStream;

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
        return utils.getLines(Utils.LOCSRC + "/year2025/day12/input.txt");
    }

    public String part1() {
        List<String> input = getInput();
        List<Integer> grids = new ArrayList<>();
        List<String> areas = new ArrayList<>();
        int tempInt = 0;
        for (String s : input) {
            if (s.isEmpty()) {
                grids.add(tempInt);
                areas = new ArrayList<>();
                tempInt = 0;
                continue;
            }
            areas.add(s);
            tempInt += Collections.frequency(s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()), '#');
        }


        long count = areas.stream().mapToLong(p -> {
            String[] parts = p.split(": ");
            String[] rowCol = parts[0].split("x");
            long x = Long.parseLong(rowCol[0]);
            long y = Long.parseLong(rowCol[1]);
            ArrayList<Long> amounts = Arrays.stream(parts[1].split(" ")).mapToLong(Long::parseLong).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

            long needed = 0;
            for (int i = 0; i < amounts.size(); i++) {
                needed += amounts.get(i) * grids.get(i);
            }


            return needed <= x * y ? 1 : 0;
        }).sum();





        return String.valueOf(count);


    }


    public String part2() {
        return "⭐";
    }



}