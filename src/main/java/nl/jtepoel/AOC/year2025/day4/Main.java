package nl.jtepoel.AOC.year2025.day4;


import java.awt.*;
import java.util.List;

import nl.jtepoel.AOC.utils.Grid;
import nl.jtepoel.AOC.utils.GridVisualizer;
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

    public List<String> getInput() {
        return utils.getLines(Utils.LOCSRC + "/year2025/day4/input.txt");
    }

    public String part1() {
        List<String> getInput = getInput();
        Grid<Character> grid = new Grid<>();
        for (int i = 0; i < getInput.size(); i++) {
            String input = getInput.get(i);
            for  (int j = 0; j < input.length(); j++) {
                grid.set(j,i, input.charAt(j));
            }

        }

        Pair<Integer,Integer> dim = grid.getDimensions();
        int sum = 0;
        for (int i = 0; i <= dim.x; i++) {
            for (int j = 0; j <= dim.y; j++) {

                if (grid.get(j, i) != '@') continue;

                int count = 0;
                for (int[] dir : Grid.DIRECTIONS8) {
                    if (grid.getOrDefault(j + dir[0], i + dir[1], '.') == '@') {
                        count++;
                    }
                }

                if (count < 4) {
                    sum++;
                }
            }
        }




        return String.valueOf(sum);
    }

    public String part2() {
        List<String> getInput = getInput();
        Grid<Character> grid = new Grid<>();
        for (int i = 0; i < getInput.size(); i++) {
            String input = getInput.get(i);
            for  (int j = 0; j < input.length(); j++) {
                grid.set(j,i, input.charAt(j));
            }

        }

        GridVisualizer<Character> vis = new GridVisualizer<Character>()
                .setFitToScreen(true)
                .setFadeEnabled(true)
                .setHighlightColor(Color.red)
                .setFadeDuration(1000);

        vis.create(grid, '.');


        Pair<Integer,Integer> dim = grid.getDimensions();
        int total = 0;
        while(true) {
            int sum = 0;
            for (int i = 0; i <= dim.x; i++) {
                for (int j = 0; j <= dim.y; j++) {
                    if (grid.get(j, i) != '@') continue;
                    int count = 0;
                    for (int[] dir : Grid.DIRECTIONS8) {
                        if (grid.getOrDefault(j + dir[0], i + dir[1], '.') == '@') {
                            count++;
                        }
                    }

                    if (count < 4) {
                        sum++;
                        grid.set(j, i, '.');


                    }
                }

            }

            vis.update();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            if (sum == 0) {
                break;
            }

            total += sum;


        }


        return String.valueOf(total);
    }



}