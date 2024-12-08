package nl.jtepoel.AOC.year2024.day8;


import java.util.*;

import nl.jtepoel.AOC.utils.*;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        long start = System.nanoTime();
        Main main = new Main();
        String part1 = main.part1();
        long part1Time = System.nanoTime() - start;
        String part2 = main.part2();
        long part2Time = System.nanoTime() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time/1000 + "µs");
        System.out.println("Part 2: " + part2 + " took " + part2Time/1000 + "µs");
    }

    public Grid<String> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day8/input.txt");
        Grid<String> grid = new Grid<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] sp = line.split("");
            for (int j = 0; j < sp.length; j++) {
                grid.set(j, i, sp[j]);
            }
        }


        return grid;
    }

    private void buildGridAndConnections() {
        grid = getInput();
        connections = grid.getFrequency();
        connections.remove(".");
    }

    private Pair<Integer,Integer> getDirection(Point point, Point otherPoint) {
        int dx = Math.abs(point.getX() - otherPoint.getX());
        int dy = Math.abs(point.getY() - otherPoint.getY());
        if (point.getX() < otherPoint.getX()) {
            dx = -dx;
        }

        if (point.getY() < otherPoint.getY()) {
            dy = -dy;
        }
        return new Pair<>(dx, dy);
    }

    private Grid<String> grid;
    private Map<String, List<Point>> connections = new HashMap<>();

    public String part1() {
        buildGridAndConnections();


        Pair<Integer,Integer> dimensions = grid.getDimensions();

        Set<Point> antiNodes = new HashSet<>();
        for (String node : connections.keySet()) {
            for (int i = 0; i < connections.get(node).size(); i++) {
                Point point = connections.get(node).get(i);
                for (int j = i + 1; j < connections.get(node).size(); j++) {
                    Point otherPoint = connections.get(node).get(j);
                    Pair<Integer,Integer> direction = getDirection(point, otherPoint);

                    antiNodes.add(new Point(point.getX() + direction.getFirst(), point.getY() + direction.getSecond()));
                    antiNodes.add(new Point(otherPoint.getX() - direction.getFirst(), otherPoint.getY() - direction.getSecond()));
                }
            }
        }


        //filter out the antiNodes that are not in the grid
        antiNodes.removeIf(point -> point.getX() < 0 || point.getX() > dimensions.getFirst() || point.getY() < 0 || point.getY() > dimensions.getSecond());


        return String.valueOf(antiNodes.size());
    }


    public String part2() {

        Pair<Integer,Integer> dimensions = grid.getDimensions();

        Set<Point> antiNodes = new HashSet<>();
        for (String node : connections.keySet()) {
            for (int i = 0; i < connections.get(node).size(); i++) {
                Point point = connections.get(node).get(i);
                antiNodes.add(point);
                for (int j = i + 1; j < connections.get(node).size(); j++) {
                    Point otherPoint = connections.get(node).get(j);
                    Pair<Integer,Integer> direction = getDirection(point, otherPoint);

                    int out = Math.max(dimensions.getFirst(), dimensions.getSecond()) / Math.min(Math.abs(direction.getFirst()), Math.abs(direction.getSecond()));
                    for (int t = 1; t <= out; t++) {
                        antiNodes.add(new Point(point.getX() + direction.getFirst() * t, point.getY() + direction.getSecond() * t));
                        antiNodes.add(new Point(otherPoint.getX() - direction.getFirst() * t, otherPoint.getY() - direction.getSecond() * t));
                    }

                }
            }
        }

        //filter out the antiNodes that are not in the grid
        antiNodes.removeIf(point -> point.getX() < 0 || point.getX() > dimensions.getFirst() || point.getY() < 0 || point.getY() > dimensions.getSecond());

        return String.valueOf(antiNodes.size());
    }





}