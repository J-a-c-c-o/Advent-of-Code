package year2021.day9;


import java.util.*;

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

    public int[][] getInput() {
        return utils.makeGrid(utils.getLines("src/main/java/year2021/day9/input.txt"),"");
    }

    public String part1() {
        int[][] grid = getInput();
        int number = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (lowestPoint(grid, i, j)) {
                    number += grid[i][j] + 1;
                }
            }
        }

        return String.valueOf(number);
    }

    private boolean lowestPoint(int[][] grid, int i, int j) {
        if (i != 0 && grid[i][j] >= grid[i-1][j]) {
            return false;
        }

        if (i != grid.length - 1 && grid[i][j] >= grid[i+1][j]) {
            return false;
        }

        if (j != 0 && grid[i][j] >= grid[i][j-1]) {
            return false;
        }

        if (j != grid[0].length - 1 && grid[i][j] >= grid[i][j+1]) {
            return false;
        }

        return true;
    }

    public String part2() {
        int[][] grid = getInput();
        List<Integer> numbers= new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (lowestPoint(grid, i, j)) {
                    numbers.add(checkBasin(grid, i, j));
                }
            }
        }

        numbers.sort(Collections.reverseOrder());
        return String.valueOf(numbers.get(0)*numbers.get(1)*numbers.get(2));
    }

    private int checkBasin(int[][] grid, int i, int j) {
        int[][] cloneGrid = DeepClone(grid);
        LinkedHashSet<Point> queue = new LinkedHashSet<>();
        Set<Point> visited = new HashSet<>();

        queue.add(new Point(i, j));

        while (!queue.isEmpty()) {
            Point current = queue.iterator().next();
            queue.remove(current);
            if (visited.contains(current)) {
                continue;
            }

            List<Point> neighbors = getHigherNeighbors(cloneGrid, current.x, current.y);

            queue.addAll(neighbors);


            visited.add(current);
        }


        return visited.size();

    }

    private List<Point> getHigherNeighbors(int[][] cloneGrid, int x, int y) {
        List<Point> neighbors = new ArrayList<>();
        if (x != 0 && cloneGrid[x][y] <= cloneGrid[x-1][y] && cloneGrid[x-1][y] != 9) {
            neighbors.add(new Point(x-1, y));
        }

        if (x != cloneGrid.length - 1 && cloneGrid[x][y] <= cloneGrid[x+1][y] && cloneGrid[x+1][y] != 9) {
            neighbors.add(new Point(x+1, y));
        }

        if (y != 0 && cloneGrid[x][y] <= cloneGrid[x][y-1] && cloneGrid[x][y-1] != 9) {
            neighbors.add(new Point(x, y-1));
        }

        if (y != cloneGrid[0].length - 1 && cloneGrid[x][y] <= cloneGrid[x][y+1] && cloneGrid[x][y+1] != 9) {
            neighbors.add(new Point(x, y+1));
        }

        return neighbors;
    }

    private int[][] DeepClone(int[][] grid) {
        int[][] clone = new int[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            clone[i] = grid[i].clone();
        }

        return clone;
    }


}