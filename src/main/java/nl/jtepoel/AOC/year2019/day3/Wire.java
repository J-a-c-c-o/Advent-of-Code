package nl.jtepoel.AOC.year2019.day3;

import nl.jtepoel.AOC.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class Wire {

    Pair<Integer, Integer> current;
    List<Pair<Integer, Integer>> path;

    List<Pair<Integer, Integer>> sort;



    public Wire(List<String> input1) {
        path = new ArrayList<>();
        current = new Pair<Integer, Integer>(0, 0);
        sort = new ArrayList<>();
        runPath(input1);
    }

    public void runPath(List<String> input) {
        current = new Pair<Integer, Integer>(0, 0);
        for (String in : input) {
            String command = in.substring(0,1);
            int steps = Integer.parseInt(in.substring(1));
            switch (command) {
                case "R" -> stepsR(steps);
                case "L" -> stepsL(steps);
                case "U" -> stepsU(steps);
                case "D" -> stepsD(steps);
            }
        }
    }

    private void stepsR(int steps) {
        for (int i = 1; i <= steps; i++) {
            path.add(new Pair<Integer, Integer>(current.x + i, current.y));
        }
        current = new Pair<Integer, Integer>(current.x + steps, current.y);

    }

    private void stepsL(int steps) {
        for (int i = 1; i <= steps; i++) {
            path.add(new Pair<Integer, Integer>(current.x - i, current.y));
        }
        current = new Pair<Integer, Integer>(current.x - steps, current.y);

    }

    private void stepsU(int steps) {
        for (int i = 1; i <= steps; i++) {
            path.add(new Pair<Integer, Integer>(current.x, current.y + i));
        }
        current = new Pair<Integer, Integer>(current.x, current.y + steps);

    }

    private void stepsD(int steps) {
        for (int i = 1; i <= steps; i++) {
            path.add(new Pair<Integer, Integer>(current.x, current.y - i));
        }
        current = new Pair<Integer, Integer>(current.x, current.y - steps);

    }

    public void getIntersection(Wire wire, boolean part1) {
        ArrayList<Pair<Integer, Integer>> copy = new ArrayList<>(path);
        ArrayList<Pair<Integer, Integer>> copy2 = new ArrayList<>(wire.path);


        copy.retainAll(copy2);

        sort = copy;

        if (part1) {
            sort.sort((o1, o2) -> {
                int dist1 = Math.abs(o1.x) + Math.abs(o1.y);
                int dist2 = Math.abs(o2.x) + Math.abs(o2.y);
                return Integer.compare(dist1, dist2);
            });
        } else {
            sort.sort((o1, o2) -> {
                int dist1 = getDistancePath(wire, o1);
                int dist2 = getDistancePath(wire, o2);
                return Integer.compare(dist1, dist2);
            });
        }

    }


    public int getDistance() {
        return Math.abs(sort.get(0).x) + Math.abs(sort.get(0).y);
    }

    public int getDistance2(Wire wire) {
        return getDistancePath(wire, sort.get(0));
    }

    private int getDistancePath(Wire wire, Pair<Integer, Integer> pair) {
        int distance = path.indexOf(pair) + 1;
        int distance2 = wire.path.indexOf(pair) + 1;

        return distance + distance2;


    }


}