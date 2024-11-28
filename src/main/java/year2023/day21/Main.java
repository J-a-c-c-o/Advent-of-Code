package year2023.day21;


import utils.Pair;
import utils.Utils;

import java.util.*;

public class Main {

    Utils utils = new Utils();

    private final List<Pair<Integer,Integer>> neighbors;


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

    public Main() {
        neighbors = new ArrayList<>();
        neighbors.add(new Pair<>(1, 0));
        neighbors.add(new Pair<>(-1, 0));
        neighbors.add(new Pair<>(0, 1));
        neighbors.add(new Pair<>(0, -1));

    }

    public char[][] getInput() {
        List<String> lines = utils.getLines("src/year2023/day21/input.txt");
        char[][] map = new char[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                map[i][j] = line.charAt(j);
            }
        }

        return map;


    }

    public String part1() {
        char[][] map = getInput();

        Pair<Integer, Integer> start = findStart(map);

        int distance = getResult(map, 64, start);

        return "" + distance;

    }

    public String part2() {
        char[][] map = getInput();

        char[][] bigMap = bigMap(map);

        Pair<Integer, Integer> start = new Pair<>(bigMap.length / 2, bigMap[0].length / 2);

        long a0 = getResult(bigMap, 65, start);
        long a1 = getResult(bigMap, 65 + 131, start);
        long a2 = getResult(bigMap, 65 + 2*131, start);


        long n = 26501365 / map.length;
        long result = a0 + n * (a1 - a0) + n * (n - 1) / 2 * (a2 - 2 * a1 + a0);

        return "" + result;

    }

    private char[][] bigMap(char[][] map) {
        //duplicate the map 5 times in both directions
        char[][] bigMap = new char[map.length * 5][map[0].length * 5];

        for (int i = 0; i < bigMap.length; i++) {
            for (int j = 0; j < bigMap[i].length; j++) {
                bigMap[i][j] = map[i % map.length][j % map[0].length];
            }
        }

        //replace all S with . because we use the middle of the map as the start anyway
        for (int i = 0; i < bigMap.length; i++) {
            for (int j = 0; j < bigMap[i].length; j++) {
                if (bigMap[i][j] == 'S') {
                    bigMap[i][j] = '.';
                }
            }
        }

        return bigMap;

    }

    private int getResult(char[][] map, int steps, Pair<Integer, Integer> start) {
        HashMap<Integer, Set<Pair<Integer, Integer>>> visited = new HashMap<>();

        visited.put(0, new HashSet<>());
        visited.get(0).add(start);

        for (int i = 0; i < steps; i++) {
            for (Pair<Integer, Integer> point : visited.get(i)) {
                int x = point.x;
                int y = point.y;

                for (Pair<Integer, Integer> neighbor : neighbors) {
                    int newX = x + neighbor.x;
                    int newY = y + neighbor.y;

                    if (newX < 0 || newX >= map.length || newY < 0 || newY >= map[0].length) {
                        continue;
                    }

                    if (map[newX][newY] != '#') {
                        if (!visited.containsKey(i + 1)) {
                            visited.put(i + 1, new HashSet<>());
                        }
                        visited.get(i + 1).add(new Pair<>(newX, newY));
                    }
                }
            }
        }

        return visited.get(visited.size() - 1).size();
    }




    private Pair<Integer, Integer> findStart(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            char[] row = map[i];
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 'S') {
                    return new Pair<>(i, j);
                }
            }
        }

        return null;
    }








}