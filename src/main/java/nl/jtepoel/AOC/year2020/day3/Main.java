package nl.jtepoel.AOC.year2020.day3;


import java.util.List;

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

    public List<String> getInput() {
        return utils.getLines(Utils.LOCSRC + "/year2020/day3/input.txt");
    }

    public String part1() {
        Grid<Character> grid = getCharacterGrid();

        int shiftx = 3;
        int shifty = 1;

        int count = slope(grid, shiftx, shifty);

        return String.valueOf(count);
    }

    private static int slope(Grid<Character> grid, int shiftx, int shifty) {
        Pair<Integer,Integer> dim = grid.getDimensions();
        int count = 0;
        int x = 0;
        int y = 0;
        while (y < dim.y + 1)  {

            int tempx = x % (dim.x + 1);
            int tempy = y % (dim.y + 1);

            char pos = grid.get(tempx, tempy);
            if (pos == '#') {
                count++;
            }

            x+= shiftx;
            y+= shifty;
        }
        return count;
    }

    public String part2() {
        Grid<Character> grid = getCharacterGrid();

        long mul = 1;

        int[][] dirs = {{1,1},{3,1},{5,1},{7,1},{1,2}};


        for (int[] dir : dirs) {
            mul *= slope(grid, dir[0], dir[1]);
        }

        return String.valueOf(mul);
    }

    private Grid<Character> getCharacterGrid() {
        Grid<Character> grid = new Grid<>();
        List<String> input = getInput();
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            char[] chars = line.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                grid.set(j, i, chars[j]);
            }
        }
        grid.print('.');
        return grid;
    }


}