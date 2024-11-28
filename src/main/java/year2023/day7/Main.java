package year2023.day7;


import java.util.*;

import utils.Pair;
import utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public List<String[]> getInput() {
        List<String> input = utils.getLines("src/year2023/day7/input.txt");
        List<String[]> list = new java.util.ArrayList<>();
        for (String s : input) {
            String[] split = s.split(" ");
            list.add(split);
        }

        return list;
    }

    private int getScore(String[] input) {
        int score;

        //check for four of a kind
        Map<String, Integer> frequencyMap = new HashMap<>();

        for (String num : input) {
            if (num.equals("X")) {
                continue;
            }
            frequencyMap.put(num, (frequencyMap.getOrDefault(num, 0) + 1));
        }

        int jokerCount = 0;
        for (String count : input) {
            if (count.equals("X")) {
                jokerCount++;
            }

        }

        for (String key : frequencyMap.keySet()) {
            frequencyMap.put(key, frequencyMap.get(key) + jokerCount);
        }

        boolean five = false;
        boolean four = false;
        boolean three = false;
        boolean onePair = false;

        for (int count : frequencyMap.values()) {
            if (count == 5) {
                five = true;
            }
            if (count == 4) {
                four = true;
            }
            if (count == 3) {
                three = true;
            }
            if (count == 2) {
                onePair = true;
            }
        }

        if (frequencyMap.size() == 0) {
            score = 8;
            return score;
        }


        if (five) {
            score = 8;
            return score;
        }

        if (four) {
            score = 7;
            return score;
        }

        //check for full house
        if (frequencyMap.size() == 2) {
            score = 6;
            return score;
        }

        if (three) {
            score = 5;
            return score;
        }

        //two pair
        int pairCount = 0;
        for (int count : frequencyMap.values()) {
            if (count == 2) {
                pairCount++;
            }
        }

        if (pairCount == 2 || (pairCount == 1 && jokerCount >= 1)) {
            score = 4;
            return score;
        }


        if (onePair) {
            score = 3;
            return score;
        }

        //high card
        score = 2;
        return score;


    }

    private int Compare(Pair<Pair<Integer, Integer>, String[]> o1,  Pair<Pair<Integer, Integer>, String[]> o2, boolean isPart1) {

        if (o1.x.x > o2.x.x) {
            return -1;
        } else if (o1.x.x < o2.x.x) {
            return 1;
        }

        String[] s1 = o1.y;
        String[] s2 = o2.y;

        for (int i = 0; i < s1.length; i++) {
            String c1 = s1[i];
            String c2 = s2[i];

            int score1 = getScore(isPart1, c1);
            int score2 = getScore(isPart1, c2);


            if (score1 != score2) {
                if (score1 < score2) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
        return 0;
    }

    private int getScore(boolean isPart1, String c) {
        int score;
        switch (c) {
            case "T" -> score = 10;
            case "J" -> {
                if (isPart1) {
                    score = 11;
                } else {
                    score = 1;
                }
            }
            case "Q" -> score = 12;
            case "K" -> score = 13;
            case "A" -> score = 14;
            default -> score = Integer.parseInt(c);
        }
        return score;
    }

    private String specialSumSorted(List<Pair<Pair<Integer, Integer>, String[]>> list) {
        //reverse list
        Collections.reverse(list);

        //sum up the scores
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum = sum + (i + 1) * list.get(i).x.y;
        }

        return String.valueOf(sum);
    }


    public String part1() {
        List<String[]> input = getInput();
        List<Pair<Pair<Integer, Integer>, String[]> > list = new java.util.ArrayList<>();
        for (String[] s : input) {

            int score = getScore(s[0].split(""));

            Pair<Integer, Integer> pair = new Pair<>(score, Integer.parseInt(s[1]));

            list.add(new Pair<>(pair, s[0].split("")));
        }

        //sort list by score
        list.sort((o1, o2) -> Compare(o1, o2, true));

        return specialSumSorted(list);
    }



    public String part2() {
        List<String[]> input = getInput();
        List<Pair<Pair<Integer, Integer>, String[]> > list = new java.util.ArrayList<>();
        for (String[] s : input) {

            //replace jokers with X
            String[] s1 = s[0].split("");
            for (int i = 0; i < s1.length; i++) {
                if (s1[i].equals("J")) {
                    s1[i] = "X";
                }
            }
            int score = getScore(s1);

            Pair<Integer, Integer> pair = new Pair<>(score, Integer.parseInt(s[1]));

            list.add(new Pair<>(pair, s[0].split("")));
        }

        //sort list by score
        list.sort((o1, o2) -> Compare(o1, o2, false));

        //reverse list
        return specialSumSorted(list);
    }
}