package year2021.day3;


import java.util.List;

import utils.FrequencySet;
import utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        long part1 = main.part1();
        long part1Time = System.currentTimeMillis() - start;
        long part2 = main.part2();
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    public List<String> getInput() {
        return utils.rotate(utils.getLines("src/year2021/day3/input.txt"));
    }

    public List<String> getInput2() {
        return utils.getLines("src/year2021/day3/input.txt");
    }



    public char commonChar(String s) {


        FrequencySet<Character> fs = new FrequencySet<>(s.toCharArray());

        if (fs.get('0') > fs.get('1')) {
            return '0';
        } else {
            return '1';
        }
    }

    public char leastCommonChar(String s) {


        FrequencySet<Character> fs = new FrequencySet<>(s.toCharArray());

        if (fs.get('0') <= fs.get('1')) {
            return '0';
        } else {
            return '1';
        }
    }

    public long part1() {
        String gamma = "";
        String epsilon = "";

        for (String s : getInput()) {
            gamma+=commonChar(s);
            epsilon+=leastCommonChar(s);
        }

        return (Long.parseLong(gamma, 2) * Long.parseLong(epsilon, 2));
    }

    public long part2() {
        List<String> input = getInput2();
        List<String> input2 = getInput2();

        int i = 0;
        while (input.size() > 1) {
            List<String> rotated = utils.rotate(input);
            char cr = commonChar(rotated.get(i));

            int finalI = i;
            input = input.stream().filter(s -> s.charAt(finalI) == cr).toList();

            i++;
        }

        i = 0;
        while (input2.size() > 1) {
            List<String> rotated = utils.rotate(input2);
            char cr = leastCommonChar(rotated.get(i));

            int finalI = i;
            input2 = input2.stream().filter(s -> s.charAt(finalI) == cr).toList();

            i++;
        }

        return (Long.parseLong(input.get(0), 2) * Long.parseLong(input2.get(0), 2));
    }



}