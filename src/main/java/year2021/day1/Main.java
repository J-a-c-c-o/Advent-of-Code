package year2021.day1;


import java.util.ArrayList;
import java.util.List;

import utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        int part1 = main.part1();
        long part1Time = System.currentTimeMillis() - start;
        int part2 = main.part2();
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    public List<String> getInput() {
        return utils.getLines("src/year2021/day1/input.txt");
    }

    public int part1() {
        List<Integer> inputList = getIntegers();

        return countIncrease(inputList);
    }

    private static int countIncrease(List<Integer> inputList) {
        int count = 0;
        for (int i = 1; i < inputList.size(); i++) {
            if (inputList.get(i - 1) < inputList.get(i)) {
                count++;
            }
        }
        return count;
    }

    private List<Integer> getIntegers() {
        List<Integer> inputList = new ArrayList<>();
        for (String input : getInput()) {
            inputList.add(Integer.parseInt(input));
        }
        return inputList;
    }

    public int part2() {
        List<Integer> inputList = getIntegers();

        List<Integer> combined = getCombinedIntegers(3, inputList);

        return countIncrease(combined);
    }

    private List<Integer> getCombinedIntegers(int window, List<Integer> inputList) {
        assert window > 0;

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i <= inputList.size() - window; i++) {
            int sum = 0;

            for (int j = i; j < i + window ; j++) {
                sum += inputList.get(j);
            }

            result.add(sum);

        }

        return result;
    }



}