package year2023.day6;


import java.util.List;

import utils.Pair;
import utils.Utils;

public class Main {


    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public List<Pair<Long,Long>> getInput() {
        List<String> input = utils.getLines("src/year2023/day6/input.txt");

        List<Pair<Long,Long>> input2 = new java.util.ArrayList<>();

        String split = input.get(0).split(": ")[1];
        String split2 = input.get(1).split(": ")[1];
        split = split.trim();
        split2 = split2.trim();

        String[] split3 = split.split(" ");
        String[] split4 = split2.split(" ");



        for (int i = 0; i < split4.length; i++) {
            if (!split4[i].equals(" ")) {

                long i1 = Long.parseLong(split3[i]);
                long i2 = Long.parseLong(split4[i]);
                Pair<Long,Long> pair = new Pair<>(i1,i2);
                input2.add(pair);
            }

        }

        return input2;
    }

    public String part1() {
        List<Pair<Long, Long>> input = getInput();
        List<Long> amount = new java.util.ArrayList<>();

        long sum = getSum(input, amount);

        return String.valueOf(sum);
    }

    private static long getSum(List<Pair<Long, Long>> input, List<Long> amount) {
        for (Pair<Long, Long> pair : input) {
            //simulate
            List<Long> combinations = new java.util.ArrayList<>();
            boolean b = true;
            boolean hold = false;
            long wait = 0;
            while (b || hold) {
                long distance = (pair.x - wait) * wait;

                if (distance > pair.y) {
                    combinations.add(wait);
                    hold = true;
                    b = false;
                } else {
                    hold = false;
                }
                wait++;
            }

            amount.add((long) combinations.size());
        }

        long sum = 1;
        for (long i : amount) {
            sum = sum * i;
        }
        return sum;
    }


    public String part2() {
        List<Pair<Long, Long>> input = getInput();
        List<Long> amount = new java.util.ArrayList<>();

        Pair<Long, Long> paired = new Pair<Long, Long>(0L,0L);

        for (Pair<Long, Long> pair : input) {
            //simulate
            paired.x = Long.parseLong(String.valueOf(paired.x) + pair.x);
            paired.y = Long.parseLong(String.valueOf(paired.y) + pair.y);
        }


        input = new java.util.ArrayList<>();
        input.add(paired);

        long sum = getSum(input, amount);

        return String.valueOf(sum);
    }



}