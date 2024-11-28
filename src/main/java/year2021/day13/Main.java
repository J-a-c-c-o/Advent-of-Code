package year2021.day13;


import java.util.ArrayList;
import java.util.List;

import utils.Pair;
import utils.Point;
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

    public Pair<int[][], List<Pair<Character, Integer>>> getInput() {
        List<String> lines = utils.getLines("src/main/java/year2021/day13/input.txt");
        //get line number that is empty
        int lineNum = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).isEmpty()) {
                lineNum = i;
                break;
            }
        }

        List<String> gridCords = lines.subList(0, lineNum);
        List<Point> PointGrid = new ArrayList<>();
        for (String cord : gridCords) {
            String[] parts = cord.split(",");
            Point point = new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

            PointGrid.add(point);
        }

        //find max x and y
        int maxX = 0;
        int maxY = 0;
        for (Point point : PointGrid) {
            if (point.x > maxX) {
                maxX = point.x;
            }
            if (point.y > maxY) {
                maxY = point.y;
            }
        }


        int[][] grid = new int[maxY+1][maxX+1];

        for (Point point : PointGrid) {
            grid[point.y][point.x] = 1;
        }


        List<String> foldsLine = lines.subList(lineNum + 1, lines.size());
        List<Pair<Character, Integer>> folds = new ArrayList<>();
        for (String fold : foldsLine) {
            String[] parts = fold.split("=");
            folds.add(new Pair<>(parts[0].charAt(parts[0].length()-1), Integer.parseInt(parts[1])));
        }


        return new Pair<>(grid, folds);

    }

    public String part1() {
        Pair<int[][], List<Pair<Character, Integer>>> input = getInput();

        int[][] grid = input.x;

        List<Pair<Character, Integer>> folds = input.y;

        Pair<Character, Integer> fold = folds.getFirst();

        int[][] newGrid = getNewGrid(fold, grid);

        // count number of 1s
        int count = 0;
        for (int[] ints : newGrid) {
            for (int j = 0; j < newGrid[0].length; j++) {
                if (ints[j] == 1) {
                    count++;
                }
            }
        }

        return String.valueOf(count);

    }

    private int[][] getNewGrid(Pair<Character, Integer> fold, int[][] grid) {
        int[][] newGrid;

        if (fold.x == 'y') {
            // split grid at fold.y
            int[][] top = new int[fold.y][grid[0].length];
            int[][] bottom = new int[grid.length - fold.y - 1][grid[0].length];

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (i < fold.y) {
                        top[i][j] = grid[i][j];
                    } else if (i > fold.y) {
                        bottom[i - fold.y - 1][j] = grid[i][j];
                    }
                }
            }

            // flip left
            bottom = utils.flipGridY(bottom);


            // overlay left and right
            newGrid = new int[fold.y][grid[0].length];

            for (int i = 0; i < newGrid.length; i++) {
                for (int j = 0; j < newGrid[0].length; j++) {
                    if (i < top.length && j < top[0].length && top[i][j] == 1) {
                        newGrid[i][j] = 1;
                    }

                    if (i < bottom.length && j < bottom[0].length && bottom[i][j] == 1) {
                        newGrid[i + newGrid.length - bottom.length][j] = 1;
                    }
                }
            }

        } else {
            // split grid at fold.x
            int[][] left = new int[grid.length][fold.y];
            int[][] right = new int[grid.length][grid[0].length - fold.y - 1];

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (j < fold.y) {
                        left[i][j] = grid[i][j];
                    } else if (j > fold.y) {
                        right[i][j - fold.y - 1] = grid[i][j];
                    }
                }
            }

            // flip right
            right = utils.flipGridX(right);

            // overlay left and right
            newGrid = new int[grid.length][fold.y];

            for (int i = 0; i < newGrid.length; i++) {
                for (int j = 0; j < newGrid[0].length; j++) {
                    if (i < left.length && j < left[0].length && left[i][j] == 1) {
                        newGrid[i][j] = 1;
                    }

                    if (i < right.length && j < right[0].length && right[i][j] == 1) {
                        newGrid[i + newGrid.length - right.length][j + newGrid[0].length - right[0].length] = 1;
                    }
                }
            }

        }
        return newGrid;
    }

    public String part2() {
        Pair<int[][], List<Pair<Character, Integer>>> input = getInput();

        int[][] grid = input.x;

        List<Pair<Character, Integer>> folds = input.y;

        for (Pair<Character, Integer> fold : folds) {
            grid = getNewGrid(fold, grid);
        }


        // print grid
        for (int[] ints : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (ints[j] == 1) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }

        return "";
    }



}