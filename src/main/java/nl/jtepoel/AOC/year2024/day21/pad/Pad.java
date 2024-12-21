package nl.jtepoel.AOC.year2024.day21.pad;

import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Point;
import nl.jtepoel.AOC.utils.Triple;

import java.util.*;

public class Pad {

    Point current;

    HashMap<Point, Character> grid;

    HashMap<Character, Point> keys;

    public Pair<Point, Character> move(char direction, Point current) {
        Point newPoint = new Point(current.x, current.y);
        Character res = null;
        switch (direction) {
            case '^' -> {
                newPoint.y--;
                res = '^';
            }
            case 'v' -> {
                newPoint.y++;
                res = 'v';
            }
            case '<' -> {
                newPoint.x--;
                res = '<';
            }
            case '>' -> {
                newPoint.x++;
                res = '>';
            }
        }
        if (grid.containsKey(newPoint)) {
            return new Pair<>(newPoint, res);
        }
        return null;
    }

    HashMap<Pair<Point, Point>, List<List<Character>>> cache2 = new HashMap<>();
    public List<List<Character>> gotTo(Character target) {
        List<List<Character>> out = new ArrayList<>();
        Point targetPoint = keys.get(target);

        if (cache2.containsKey(new Pair<>(current, targetPoint))) {
            return cache2.get(new Pair<>(current, targetPoint));
        }

        //get all paths
        Queue<Triple<Point, List<Pair<Point,Character>>, Set<Point>>> paths = new LinkedList<>();
        paths.add(new Triple<>(current, new ArrayList<>(), new HashSet<>()));

        while (!paths.isEmpty()) {
            Triple<Point, List<Pair<Point,Character>>, Set<Point>> path = paths.poll();
            Point p = path.getFirst();
            List<Pair<Point,Character>> steps = path.getSecond();
            Set<Point> visited = path.getThird();

            if (visited.contains(p)) {
                continue;
            }

            visited.add(p);


            if (p.equals(targetPoint)) {
                List<Character> temp = new ArrayList<>();
                for (Pair<Point, Character> step : steps) {
                    temp.add(step.getSecond());
                }
                temp.add('A');
                out.add(temp);
            }
            for (char direction : List.of('^', 'v', '<', '>')) {
                Pair<Point, Character> next = move(direction, p);

                if (next != null) {
                    List<Pair<Point, Character>> newSteps = new ArrayList<>(steps);
                    newSteps.add(next);
                    paths.add(new Triple<>(next.getFirst(), newSteps, new HashSet<>(visited)));
                }
            }
        }


        cache2.put(new Pair<>(current, targetPoint), out);

        current = targetPoint;
        return out;
    }

    public List<String> buildCode(Character c) {
        List<List<Character>> gotTo = gotTo(c);
        List<List<Character>> res = new ArrayList<>(gotTo);

        List<String> codes = new ArrayList<>();

        for (List<Character> path : res) {
            StringBuilder sb = new StringBuilder();
            for (Character step : path) {
                sb.append(step);
            }
            codes.add(sb.toString());
        }


        return codes;
    }
}
