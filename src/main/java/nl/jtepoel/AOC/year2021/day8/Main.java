package nl.jtepoel.AOC.year2021.day8;


import java.util.ArrayList;
import java.util.Arrays;
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

    public List<Pair<String[], String[]>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2021/day8/input.txt");
        List<Pair<String[], String[]>> input = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(" \\| ");
            input.add(new Pair<>(split[0].split(" "), split[1].split(" ")));
        }

        return input;
    }

    public String part1() {
        List<Pair<String[], String[]>> input = getInput();
        int count = 0;
        for (Pair<String[], String[]> p : input) {
            String[] right = p.y;
            for (String w : right) {
                int l = w.length();
                if (l == 2 || l == 4 || l == 3 || l == 7) {
                    count++;
                }
            }
        }

        return "" + count;
    }

    public String part2() {
        List<Pair<String[], String[]>> input = getInput();
        long count = 0;
        for (Pair<String[], String[]> p : input) {
            List<String> left = new ArrayList<>(List.of(p.x));
            List<String> right = new ArrayList<>(List.of(p.y));
            String[] num = new String[10];
            findKnown(left, num);

            findUsing(left, num, 3, 5, 1, 0);

            findUsing(left, num, 6, 6, 1, 1);

            findUsing(left, num, 5, 5, 6, 1);


            findUsing(left, num, 2, 5, 8, 2);


            findUsing(left, num, 0, 6, 5, 1);


            findUsing(left, num, 9, 6, 8, 1);




            String number = "";
            for (String w : right) {
                for (int i = 0; i < num.length; i++) {
                    char[] wordArray = w.toCharArray();
                    Arrays.sort(wordArray);
                    String word = new String(wordArray);
                    if (word.equals(num[i])) {
                        number += i;
                    }
                }
            }

            count += Long.parseLong(number);


        }


        return "" + count;
    }

    private static void findUsing(List<String> left, String[] num, int target, int length, int using, int changes) {
        for (String w : new ArrayList<>(left)) {
            int l = w.length();
            if (l == length) { //1
                int count = 0;
                for (char c : num[using].toCharArray()) {
                    if (!w.contains(c + "")) {
                        count++;
                    }
                }

                if (count == changes) {
                    char[] word = w.toCharArray();
                    Arrays.sort(word);

                    num[target] = new String(word);

                    left.remove(w);
                    return;
                }
            }
        }

    }

    private static void findKnown(List<String> left, String[] num) {
        for (String w : new ArrayList<>(left)) {
            int l = w.length();
            char[] wordArray = w.toCharArray();
            Arrays.sort(wordArray);
            String word = new String(wordArray);
            if (l == 2) { //1
                num[1] = word;
                left.remove(w);
            } else if (l == 4) { //4
                num[4] = word;
                left.remove(w);
            } else if (l == 3) { //7
                num[7] = word;
                left.remove(w);
            } else if (l == 7) { //8
                num[8] = word;
                left.remove(w);
            }
        }
    }


}