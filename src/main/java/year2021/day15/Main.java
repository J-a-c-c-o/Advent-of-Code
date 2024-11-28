package year2021.day15;


import java.util.*;

import utils.*;

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

    public int[][] getInput() {
        return utils.makeGrid(utils.getLines("src/main/java/year2021/day15/input.txt"), "");
    }

    public String part1() {
        int[][] input = getInput();
        int sum = calculateRisk(input);

        return STR."\{sum}";
    }

    public String part2() {


        int[][] input = getInput();

        //5* the input
        int[][] newInput = new int[input.length * 5][input[0].length * 5];

        for (int t = 0; t < 5; t++) {
            for (int t2 = 0; t2 < 5; t2++) {

                for (int i = 0; i < input.length; i++) {
                    for (int j = 0; j < input[i].length; j++) {
                        int[] loc = newInput[(input.length * t) + i];
                        loc[(input[i].length * t2) + j] = (input[i][j] + t + t2);

                        if (loc[(input[i].length * t2) + j] > 9) {
                            loc[(input[i].length * t2) + j] = loc[(input[i].length * t2) + j] % 10 + 1;
                        }
                    }
                }


            }
        }

        int sum = calculateRisk(newInput);

        return STR."\{sum}";


    }

    private static int calculateRisk(int[][] input) {
        List<Pair<Point,Point>> queue = new LinkedList<>();
        queue.add(new Pair<>(new Point(0, 0), new Point(0, 0)));

        int[][] grid = new int[input.length][input[0].length];

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                grid[i][j] = Integer.MAX_VALUE;
            }
        }


        int[][] neighbours = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!queue.isEmpty()) {
            Pair<Point, Point> pair = queue.removeFirst();
            Point current = pair.x;
            Point previous = pair.y;




            int newRisk = input[current.y][current.x] + grid[previous.y][previous.x];

            if (current.equals(previous)) {
                newRisk = 0;
            }



            if (newRisk < grid[current.y][current.x]) {
                grid[current.y][current.x] = newRisk;
            } else {
                continue;
            }



            for (int[] neighbour : neighbours) {
                int x = current.x + neighbour[0];
                int y = current.y + neighbour[1];



                if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length) {
                    continue;
                }

                queue.add(new Pair<>(new Point(x, y), current));
            }
        }


        return grid[grid.length - 1][grid[0].length - 1];
    }





}