package nl.jtepoel.AOC.year2019.day1;


import nl.jtepoel.AOC.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public List<Integer> getInput() {
        return utils.getNumbers(Utils.LOCSRC + "/year2019/day1/input.txt");
    }

    public int part1() {
        List<Integer> input = getInput();
        int sum = 0;

        for (int in : input) {
            sum += in/3-2;
        }



        return sum;

    }

    public int part2() {
        List<Integer> input = getInput();
        List<Integer> result = new ArrayList<>();

        for (int in : input) {
            int sum = 0;
            while (true) {
                in = in/3-2;

                if (in <= 0) {
                    break;
                }

                sum += in;



            }
            result.add(sum);
        }

        int sum = 0;

        for (int res : result) {
            sum += res;
        }

        return sum;
    }



}