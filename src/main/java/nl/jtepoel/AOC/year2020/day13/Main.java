package nl.jtepoel.AOC.year2020.day13;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        return utils.getLines(Utils.LOCSRC + "/year2020/day13/input.txt");
    }

    public String part1() {
        List<String> lines = getInput();
        int earliest = Integer.parseInt(lines.getFirst());
        String[] split = lines.get(1).split(",");
        long min = Long.MAX_VALUE;
        String id = "";
        for (String s : split) {
            if (Objects.equals(s, "x")) {
                continue;
            }
            long l = Long.parseLong(s) - (earliest % Long.parseLong(s));
            if (min > l) {
                min = l;
                id = s;
            }
        }

        return String.valueOf(Long.parseLong(id) * min);
    }

    public String part2() {
        List<String> lines = getInput();
        String[] split = lines.get(1).split(",");

        List<Long> buses = new ArrayList<>();
        List<Long> waitTime = new ArrayList<>();

        for (int i = 0; i < split.length; i++) {
            String bus = split[i];
            if (Objects.equals(bus, "x")) {
                continue;
            }
            long longBus = Long.parseLong(bus);
            buses.add(longBus);
            waitTime.add(longBus - i);
        }
        return String.valueOf(utils.chineseRemainder(buses, waitTime));
        


    }



}