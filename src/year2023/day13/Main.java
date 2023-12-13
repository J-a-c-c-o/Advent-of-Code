package year2023.day13;


import java.util.ArrayList;
import java.util.List;

import utils.Utils;

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
        return utils.getLines("src/year2023/day13/input.txt");
    }


    public String part1() {
        List<String> input = getInput();

        int total = getTotal(input, 0);

        return String.valueOf(total);
    }

    public String part2() {
        List<String> input = getInput();

        int total = getTotal(input, 1);

        return String.valueOf(total);
    }


    private int getTotal(List<String> input, int smudgeTarget) {

        List<List<List<Character>>> vertical = new ArrayList<>();
        List<List<List<Character>>> horizontal = new ArrayList<>();


        //separate by null
        List<List<Character>> seperatedMaps = new ArrayList<>();
        for (String s : input) {
            if (s.equals("")) {
                vertical.add(seperatedMaps);
                seperatedMaps = new ArrayList<>();
            } else {
                List<Character> row = new ArrayList<>();
                for (char c : s.toCharArray()) {
                    row.add(c);
                }
                seperatedMaps.add(row);
            }
        }

        vertical.add(seperatedMaps);


        //rotate each map
        for (List<List<Character>> tempMap : vertical) {
            List<List<Character>> tempMap2 = new ArrayList<>();
            for (List<Character> row : tempMap) {
                for (int k = 0; k < row.size(); k++) {
                    if (tempMap2.size() == k) {
                        tempMap2.add(new ArrayList<>());
                    }
                    tempMap2.get(k).add(row.get(k));
                }
            }
            horizontal.add(tempMap2);
        }


        int total = 0;

        //find the vertical mirrors
        for (List<List<Character>> map : vertical) {
            total += findMirror(map, smudgeTarget) * 100;
        }


        //find the horizontal mirrors
        for (List<List<Character>> map : horizontal) {
            total += findMirror(map, smudgeTarget);
        }


        return total;
    }

    private int findMirror(List<List<Character>> integers, int smudges) {
        for (int s = 0; s < integers.size() - 1; s++) {
            int smudge = 0;
            for (int i = 0; i < s + 1; i++) {
                if (s + i + 1 >= integers.size()) {
                    continue;
                }

                List<Character> up = integers.get(s - i);
                List<Character> down = integers.get(s + i + 1);

                for (int j = 0; j < up.size(); j++) {
                    if (up.get(j) != down.get(j)) {
                        smudge++;
                    }
                }
            }

            if (smudge == smudges) {
                return s + 1;
            }
        }

        return 0;
    }




}