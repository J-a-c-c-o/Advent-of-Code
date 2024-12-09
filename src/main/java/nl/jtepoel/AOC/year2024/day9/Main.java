package nl.jtepoel.AOC.year2024.day9;


import java.util.*;

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

    public List<Pair<Long, Long>> getInput2() {
        String line =  utils.getLines(Utils.LOCSRC + "/year2024/day9/input.txt").getFirst();
        List<Pair<Long, Long>> chars = new ArrayList<>();
        long ID = 0;

        for (int i = 0; i < line.length(); i++) {
            Long c;
            if (i%2 == 0) {
                c = ID;
                ID++;
            } else {
                c = null;
            }

            if (Long.parseLong(String.valueOf(line.charAt(i))) >= 1) {
                chars.add(new Pair<>(Long.parseLong(String.valueOf(line.charAt(i))), c));
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
            for (int j = 0; j < i+1; j++) {
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
        List<Pair<Long, Long>> chars = getInput2();

        Queue<Pair<Long, Long>> queue = new LinkedList<>();
        //add all indices that are not null
        for (int i = chars.size() - 1; i >= 0; i--) {
            if (chars.get(i).getRight() != null) {
                queue.add(chars.get(i));
            }
        }

        int counter = 0;
        while (counter < queue.size()) {
            Pair<Long, Long> c = queue.poll();
            int index = chars.indexOf(c);
            //find place where c fits
            boolean found = false;
            for (int i = 0; i < index; i++) {
                Pair<Long, Long> l = chars.get(i);
                if (l.getRight() == null && l.getLeft() >= c.getLeft()) {
                    chars.set(index, new Pair<>(c.getLeft(), null));
                    chars.set(i, c);

                    if (l.getLeft() > c.getLeft()) {
                        chars.add(i+1, new Pair<>(l.getLeft() - c.getLeft(), null));
                    }
                    found = true;
                    break;
                }
            }

            if (!found) {
                queue.add(c);
                counter++;
            } else {
                counter = 0;
            }

        }

        long sum = checkSum2(chars);


        return String.valueOf(sum);
    }

    private static long checkSum2(List<Pair<Long, Long>> chars) {
        long sum = 0;
        int index = 0;
        for (Pair<Long, Long> c : chars) {
            if (c.getRight() != null) {
                for (int j = 0; j < c.getLeft(); j++) {
                    sum += c.getRight() * index;
                    index++;
                }
            } else {
                index += c.getLeft();
            }
        }
        return sum;
    }
}