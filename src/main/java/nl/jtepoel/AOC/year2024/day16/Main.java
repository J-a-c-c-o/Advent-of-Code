package nl.jtepoel.AOC.year2024.day16;


import java.util.*;

import nl.jtepoel.AOC.utils.*;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        String sol = main.part2();
        long solved = System.currentTimeMillis() - start;

        System.out.println("Part 1, 2: " + sol + " took " + solved + "ms");

    }

    public Grid<Character> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day16/input.txt");

        Grid<Character> grid = new Grid<>();

        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {

                if (lines.get(i).charAt(j) == '#') {
                    continue;
                }

                grid.set(j, i, lines.get(i).charAt(j));
            }
        }

        return grid;

    }


    public String part1() {
        Grid<Character> grid = getInput();

        char direction = '>';
        Point end = grid.find('E');
        Point start = grid.find('S');

        Pair<Long, Integer> score = bfs(grid, start, end, direction);

        return String.valueOf(score.getFirst());
    }


    public String part2() {
        Grid<Character> grid = getInput();

        char direction = '>';
        Point end = grid.find('E');
        Point start = grid.find('S');


        Pair<Long, Integer> score = bfs(grid, start, end, direction);


        return String.valueOf(score);
    }

    HashMap<Character, char[]> directions = new HashMap<>() {{
        put('^', new char[]{'<', '>', '^'});
        put('>', new char[]{'^', 'v', '>'});
        put('v', new char[]{'<', '>', 'v'});
        put('<', new char[]{'v', '^', '<'});
    }};

    private Pair<Long, Integer> bfs(Grid<Character> grid, Point start, Point end, char direction) {
        Queue<Quad<Point, Character, Long, List<Point>>> queue = new PriorityQueue<>(Comparator.comparingLong(Quad::getThird));
        queue.add(new Quad<>(start, direction, 0L, new ArrayList<>()));

        HashMap<Pair<Point, Character>, Long> visited = new HashMap<>();

        Set<Point> visitedPoints = new HashSet<>();

        long best = Long.MAX_VALUE;

        while (!queue.isEmpty()) {
            Quad<Point, Character, Long, List<Point>> current = queue.poll();
            Point currentPoint = current.getFirst();
            char currentDirection = current.getSecond();
            long currentDist = current.getThird();
            List<Point> currentPath = current.getFourth();
            currentPath.add(currentPoint);


            if (visited.containsKey(new Pair<>(currentPoint, currentDirection))) {
                if (visited.get(new Pair<>(currentPoint, currentDirection)) < currentDist) {
                    continue;
                }
            }

            visited.put(new Pair<>(currentPoint, currentDirection), currentDist);

            if (currentPoint.equals(end)) {
                if (currentDist <= best) {
                    best = currentDist;
                    visitedPoints.addAll(currentPath);
                }

            }



            for (char d : directions.get(currentDirection)) {
                Point next = currentPoint.forward(d, true);


                if (grid.get(next) != null) {
                    long nextDist = currentDist + 1;

                    if (d != currentDirection) {
                        nextDist += 1000;
                    }

                    queue.add(new Quad<>(next, d, nextDist, new ArrayList<>(currentPath)));
                }
            }
        }


        Grid<Character> grid2 = grid.copy();
        for (Point p : visitedPoints) {
            grid2.set(p, 'X');
        }

        return Pair.of(best, visitedPoints.size());
    }






}