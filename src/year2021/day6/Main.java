package year2021.day6;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import utils.Point;
import utils.Utils;

import javax.swing.*;

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

    public List<LanternFish> getInput() {
        String line = utils.getLines("src/year2021/day6/input.txt").get(0);

        String[] split = line.split(",");
        List<LanternFish> fish = new ArrayList<>();
        for (String s : split) {
            fish.add(new LanternFish(Integer.parseInt(s), 0, null));
        }

        return fish;

    }

    private String countLanternFish(int days) {
        List<LanternFish> fishes = getInput();
        HashMap<Point, Long> cache = new HashMap<>();
        long count = fishes.size();
        for (LanternFish main_fish : fishes) {

            main_fish.setCache(cache);
            main_fish.setDays(days);
            count += main_fish.simulate();
        }

        return String.valueOf(count);
    }

    public String part1() {
        return countLanternFish(80);
    }



    public String part2() {
        return countLanternFish(256);
    }



}