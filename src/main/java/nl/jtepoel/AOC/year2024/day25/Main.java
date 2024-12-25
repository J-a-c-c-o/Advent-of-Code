package nl.jtepoel.AOC.year2024.day25;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import nl.jtepoel.AOC.utils.Grid;
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

    public Pair<List<List<Integer>>, List<List<Integer>>> getInput() {
        List<List<Integer>> pins = new ArrayList<>();
        List<List<Integer>> keys = new ArrayList<>();
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day25/input.txt");


        List<Grid<Character>> grids = utils.getGrids(lines);
        for (Grid<Character> grid : grids) {
            List<Integer> temp = new ArrayList<>();
            List<List<Character>> columns = grid.columns();
            for (int i = 0; i < columns.size(); i++) {
                temp.add(0);
            }

            for (int i = 0; i < columns.size(); i++) {
                List<Character> column = columns.get(i);
                int num = column.stream().filter(c -> c == '#').mapToInt(_ -> 1).sum() - 1;
                temp.set(i, num);
            }


            if (grid.rows().getFirst().getFirst() == '#') {
                pins.add(temp);
            } else {
                keys.add(temp);
            }
        }


        return Pair.of(pins, keys);
    }

    public String part1() {
        Pair<List<List<Integer>>, List<List<Integer>>> input = getInput();
        List<List<Integer>> pins = input.getFirst();
        List<List<Integer>> keys = input.getSecond();

        int sum = 0;

        for (List<Integer> pin : pins) {
            for (List<Integer> key : keys) {

                boolean valid = IntStream.range(0, pin.size())
                        .allMatch(i -> pin.get(i) + key.get(i) <= 5);

                if (valid) {
                    sum++;
                }
            }
        }

        return String.valueOf(sum);
    }

    public String part2() {
        return "Merry Christmas!";
    }



}