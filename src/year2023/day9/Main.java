package year2023.day9;


import java.util.ArrayList;
import java.util.List;

import utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public List<String[]> getInput() {
        List<String> input = utils.getLines("src/year2023/day9/input.txt");
        List<String[]> result = new ArrayList<>();
        for (String s : input) {
            result.add(s.split(" "));
        }

        return result;
    }

    public String part1() {
        int sum = getSum(false);

        return String.valueOf(sum);
    }

    private int getSum(boolean part2) {
        List<String[]> input = getInput();
        List<List<String[]>> groups = new ArrayList<>();
        List<String[]> group;
        for (String[] strings : input) {
            group = new ArrayList<>();
            group.add(strings);
            boolean notAllZero = true;
            while (notAllZero) {
                String[] next = new String[strings.length - 1];
                for (int j = 0; j < strings.length - 1; j++) {
                    int difference = Integer.parseInt(strings[j + 1]) - Integer.parseInt(strings[j]);
                    next[j] = String.valueOf(difference);
                }
                strings = next;
                group.add(next);
                boolean allZero = true;
                for (String s : next) {
                    if (!s.equals("0")) {
                        allZero = false;
                        break;
                    }
                }

                if (allZero) {
                    notAllZero = false;
                }
            }
            groups.add(group);
        }

        List<Integer> predictions = new ArrayList<>();

        for (List<String[]> list : groups) {
            int prediction = 0;
            for (int j = list.size() - 1; j > 0; j--) {
                String[] strings = list.get(j);
                String[] next = list.get(j - 1);
                if (part2) {
                    int first = Integer.parseInt(next[0]);
                    prediction = first - prediction;
                } else {
                    int last = Integer.parseInt(next[strings.length]);
                    prediction += last;
                }
            }
            predictions.add(prediction);
        }
        //sum of all predictions
        int sum = 0;
        for (int i : predictions) {
            sum += i;
        }
        return sum;
    }

    public String part2() {
        int sum = getSum(true);

        return String.valueOf(sum);
    }



}