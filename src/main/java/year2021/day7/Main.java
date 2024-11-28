package year2021.day7;


import java.util.ArrayList;
import java.util.List;

import utils.Pair;
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

    public Pair<List<crab>, Integer> getInput() {
        String line =  utils.getLines("src/main/java/year2021/day7/input.txt").get(0);
        String[] poss = line.split(",");

        List<crab> crabs = new ArrayList<>();
        int max = 0;
        for (String pos : poss) {
            int temp = Integer.parseInt(pos);
            crabs.add(new crab(temp));

            max = Math.max(max, temp);
        }

        return new Pair<>(crabs, max);
    }

    public String part1() {
        Pair<List<crab>, Integer> pair = getInput();
        List<crab> crabs = pair.x;
        int max = pair.y;
        int fuelM = Integer.MAX_VALUE;;
        for (int i = 0; i <= max; i++) {
            int fuel = 0;
            for (crab crab : crabs) {
                fuel+= crab.moveTo(i);
            }

            fuelM = Math.min(fuelM, fuel);

        }

        return String.valueOf(fuelM);
    }

    public String part2() {
        Pair<List<crab>, Integer> pair = getInput();
        List<crab> crabs = pair.x;
        int max = pair.y;
        int fuelM = Integer.MAX_VALUE;
        for (int i = 0; i <= max; i++) {
            int fuel = 0;
            for (crab crab : crabs) {
                fuel+= crab.moveToP2(i);
            }
            fuelM = Math.min(fuelM, fuel);

        }

        return String.valueOf(fuelM);
    }



}