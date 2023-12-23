package year2023.day23;


import utils.Pair;
import utils.Triple;
import utils.Utils;

import java.util.*;

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

    public char[][] getInput() {
        List<String> lines = utils.getLines("src/year2023/day23/example.txt");

        char[][] map = new char[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                map[i][j] = line.charAt(j);
            }
        }




        return map;
    }

    private List<Pair<Integer, Integer>> getNeighbours(Pair<Integer, Integer> currentPos, char[][] map) {
        List<Pair<Integer, Integer>> neighbours = new ArrayList<>();
        int x = currentPos.x;
        int y = currentPos.y;

        if (x - 1 >= 0 && map[y][x] == '<') {
            neighbours.add(new Pair<>(x - 1, y));
            return neighbours;
        }

        if (x + 1 < map[0].length && map[y][x] == '>') {
            neighbours.add(new Pair<>(x + 1, y));
            return neighbours;
        }

        if (y - 1 >= 0 && map[y][x] == '^') {
            neighbours.add(new Pair<>(x, y - 1));
            return neighbours;
        }

        if (y + 1 < map.length && map[y][x] == 'v') {
            neighbours.add(new Pair<>(x, y + 1));
            return neighbours;
        }




        if (x - 1 >= 0 && map[y][x - 1] != '#' && map[y][x - 1] != '>') {

            neighbours.add(new Pair<>(x - 1, y));
        }
        if (x + 1 < map[0].length && map[y][x + 1] != '#' && map[y][x + 1] != '<') {
            neighbours.add(new Pair<>(x + 1, y));
        }
        if (y - 1 >= 0 && map[y - 1][x] != '#' && map[y - 1][x] != 'v') {
            neighbours.add(new Pair<>(x, y - 1));
        }
        if (y + 1 < map.length && map[y + 1][x] != '#' && map[y + 1][x] != '^') {
            neighbours.add(new Pair<>(x, y + 1));
        }

        return neighbours;
    }

    public String part1() {

        char[][] map = getInput();

        Pair<Integer, Integer> start = new Pair<>(1, 0);
        Pair<Integer, Integer> end = new Pair<>(map.length - 2, map[0].length - 1);

        int currentDistance = getDistance(map, start, end);

        return String.valueOf(currentDistance);
    }

    private int getDistance(char[][] map, Pair<Integer, Integer> start, Pair<Integer, Integer> end) {

        int maxDistance = 0;

        List<Pair<Integer, Integer>> visited = new ArrayList<>();
        visited.add(start);

        Queue<Triple<Integer, Pair<Integer, Integer>, List<Pair<Integer, Integer>>>> queue = new LinkedList<>();

        queue.add(new Triple<>(0, start, new ArrayList<>(visited)));


        while (!queue.isEmpty()) {

            Triple<Integer, Pair<Integer, Integer>, List<Pair<Integer, Integer>>> current = queue.poll();
            Pair<Integer, Integer> currentPos = current.y;
            int currentDistance = current.x;
            List<Pair<Integer, Integer>> currentVisited = current.z;


            if (Objects.equals(currentPos.x, end.x) && Objects.equals(currentPos.y, end.y)) {
                if (currentDistance > maxDistance) {
                    maxDistance = currentDistance;
                    System.out.println(maxDistance);
                }
                continue;
            }

            List<Pair<Integer, Integer>> neighbours = getNeighbours(currentPos, map);

            for (Pair<Integer, Integer> neighbour : neighbours) {

                if (currentVisited.contains(neighbour)) {
                    continue;
                } else {
                    currentVisited.add(neighbour);
                }

                List<Pair<Integer, Integer>> newVisited = new ArrayList<>(currentVisited);


                queue.add(new Triple<>(currentDistance + 1, neighbour, newVisited));
            }
        }
        return maxDistance;
    }

    public String part2() {
        char[][] map = getInput();

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[1].length; j++) {
                if (map[i][j] == 'v' || map[i][j] == '^' || map[i][j] == '<' || map[i][j] == '>') {
                    map[i][j] = '.';
                }
            }
        }

        Pair<Integer, Integer> start = new Pair<>(1, 0);
        Pair<Integer, Integer> end = new Pair<>(map.length - 2, map[0].length - 1);

        int currentDistance = getDistance(map, start, end);

        return String.valueOf(currentDistance);





    }



}