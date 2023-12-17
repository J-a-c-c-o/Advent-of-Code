package year2023.day17;


import java.util.*;

import utils.*;

public class Main {

    Utils utils = new Utils();

    private final List<String> directions;


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
        directions = new ArrayList<>();
        directions.add("^");
        directions.add("v");
        directions.add("<");
        directions.add(">");
    }

    public int[][] getInput() {
        List<String> lines = utils.getLines("src/year2023/day17/input.txt");
        int[][] map = new int[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                map[i][j] = Integer.parseInt(line.split("")[j]);
            }
        }

        return map;


    }

    public String part1() {
        int[][] map = getInput();

        int distance = getResult(map, 0, 3);

        return "" + distance;

    }

    public String part2() {
        int[][] map = getInput();

        int distance = getResult(map, 4, 10);

        return "" + distance;
    }

    private int getResult(int[][] map, int min, int max) {
        Pair<Integer, Integer> start = new Pair<>(0, 0);
        Pair<Integer, Integer> end = new Pair<>(map.length - 1, map[0].length - 1);


        PriorityQueue<Triple<Integer, String, Pair<Integer, Integer>>> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.x));
        queue.add(new Triple<>(0, "v", start));
        queue.add(new Triple<>(0, ">", start));


        //visited
        Set<Pair<Pair<Integer, Integer>, String>> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Triple<Integer, String, Pair<Integer, Integer>> current = queue.poll();
            Pair<Integer, Integer> cord = current.z;
            String direction = current.y;
            int distance = current.x;
            if (visited.contains(new Pair<>(cord, direction))) {
                continue;
            }

            visited.add(new Pair<>(cord, direction));

            for (String dir : directions) {


                Pair<Integer, Integer> dirStep = getDirection(dir);
                Pair<Integer, Integer> newCords = new Pair<>(cord.x + dirStep.x, cord.y + dirStep.y);

                //bounds
                if (newCords.x < 0 || newCords.x >= map.length || newCords.y < 0 || newCords.y >= map[0].length) {
                    continue;
                }


                String lastDir = direction.toCharArray()[direction.length() - 1] + "";

                boolean equals = dir.equals(lastDir);

                if (equals && direction.length() == max) {
                    continue;
                }

                if (!equals && direction.length() < min) {
                    continue;
                }

                if (lastDir.equals(opposite(dir))) {
                    continue;
                }

                String newDir;
                if (equals) {
                    newDir = direction + dir;
                } else {
                    newDir = dir;
                }

                if (visited.contains(new Pair<>(newCords, newDir))) {
                    continue;
                }

                if (newCords.equals(end) && newDir.length() >= min) {
                    return distance + map[newCords.x][newCords.y];
                }


                queue.add(new Triple<>(distance + map[newCords.x][newCords.y], newDir, newCords));
            }

        }
        return 0;
    }


    private Pair<Integer, Integer> getDirection(String direction) {
        switch (direction) {
            case "^" -> {return new Pair<>(-1, 0);}
            case "v" -> {return new Pair<>(1, 0);}
            case "<" -> {return new Pair<>(0, -1);}
            case ">" -> {return new Pair<>(0, 1);}
            default -> {return new Pair<>(0, 0);}
        }
    }

    private String opposite(String direction) {
        return switch (direction) {
            case "^" -> "v";
            case "v" -> "^";
            case "<" -> ">";
            case ">" -> "<";
            default -> "";
        };
    }





}