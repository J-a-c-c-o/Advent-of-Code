package nl.jtepoel.AOC.year2024.day9;


import java.util.*;

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
        String line =  utils.getLines(Utils.LOCSRC + "/year2024/day9/input.txt").getFirst();
        List<Long> chars = new ArrayList<>();
        long ID = 0;

        for (int i = 0; i < line.length(); i++) {
            Long c;
            if (i%2 == 0) {
                c = ID;
                ID++;
            } else {
                c = null;
            }

            for (int j = 0; j < Character.getNumericValue(line.charAt(i)); j++) {
                chars.add(c);
            }

        }

        return chars;
    }

    public String part1() {
        List<Long> chars = getInput();
        List<Long> newChars = new ArrayList<>(chars);
        for (int i = chars.size() - 1; i >= 0; i--) {

            if (chars.get(i) == null) {
                continue;
            }
            long c = chars.get(i);
            newChars.set(i, null);

            //replace first null with c
            for (int j = 0; j < newChars.size(); j++) {
                if (newChars.get(j) == null) {
                    newChars.set(j, c);
                    break;
                }
            }

        }

        long sum = checkSum(newChars);
        return String.valueOf(sum);
    }

    private static long checkSum(List<Long> newChars) {
        long sum = 0;
        for (int i = 0; i < newChars.size(); i++) {
            if (newChars.get(i) == null) {
                continue;
            }

            sum += newChars.get(i) * i;
        }
        return sum;
    }

    public String part2() {
        List<Long> chars = getInput();
        List<Long> newChars = new ArrayList<>(chars);

        List<Long> cycle = new ArrayList<>(new HashSet<>(chars));
        cycle.removeIf(Objects::isNull);
        cycle.sort(Comparator.reverseOrder());


        int edited = 0;
        while (edited<=cycle.getLast()) {
            Long c = cycle.removeFirst();
            List<Integer> findAll = new ArrayList<>();
            for (int i = 0; i < newChars.size(); i++) {
                if (Objects.equals(newChars.get(i), c)) {
                    findAll.add(i);
                }
            }



            // find place where all fits
            boolean found = false;
            for (int i = 0; i < newChars.size(); i++) {
                if (newChars.get(i) == null && i + findAll.size() < newChars.size() && i < findAll.getFirst()) {
                    boolean fits = true;
                    for (int j = 0; j < findAll.size(); j++) {
                        if (newChars.get(i + j) != null) {
                            fits = false;
                            break;
                        }
                    }

                    if (fits) {
                        for (int j = 0; j < findAll.size(); j++) {
                            newChars.set(i + j, c);
                            newChars.set(findAll.get(j), null);
                        }
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                edited++;
                cycle.add(c);
            } else {
                edited = 0;
            }


        }

        long sum = checkSum(newChars);
        return String.valueOf(sum);
    }



}