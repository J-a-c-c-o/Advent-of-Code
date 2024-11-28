package year2023.day23;


import org.graphstream.graph.implementations.SingleGraph;
import utils.Pair;
import utils.Triple;
import utils.Utils;
import org.graphstream.graph.*;


import java.util.*;


public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
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
        List<String> lines = utils.getLines("src/main/java/year2023/day23/example.txt");

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

        int getLongestPath = getLongestPath(map);

        return String.valueOf(getLongestPath);
    }

    public String part2() {
        char[][] map = getInput();

        //part 2: remove all hills
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[1].length; j++) {
                if (map[i][j] == 'v' || map[i][j] == '^' || map[i][j] == '<' || map[i][j] == '>') {
                    map[i][j] = '.';
                }
            }
        }


        int longestPath = getLongestPath(map);

        return String.valueOf(longestPath);
    }

    private int getLongestPath(char[][] map) {
        //start and end
        Pair<Integer, Integer> start = new Pair<>(1, 0);
        Pair<Integer, Integer> end = new Pair<>(map.length - 2, map[0].length - 1);


        //find all intersections
        List<Pair<Integer, Integer>> intersectionList = new ArrayList<>();

        intersectionList.add(start);
        intersectionList.add(end);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[1].length; j++) {
                if (map[i][j] == '#') {
                    continue;
                }
                int amountNeighbours = getNeighbours(new Pair<>(j, i), map).size();
                if (amountNeighbours > 2) {
                    intersectionList.add(new Pair<>(j, i));
                }
            }
        }


        //find all intersections and distances to the next intersections
        HashMap<Pair<Integer, Integer>, List<Pair<Pair<Integer, Integer>, Integer>>> intersectionsAndDistances = new HashMap<>();

        for (Pair<Integer, Integer> intersection : intersectionList) {
            intersectionsAndDistances.put(intersection, findIntersection(map, intersection, intersectionList));
        }

        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> edges = new ArrayList<>();
        for (Pair<Integer, Integer> intersection : intersectionsAndDistances.keySet()) {
            for (Pair<Pair<Integer, Integer>, Integer> intersectionAndDistance : intersectionsAndDistances.get(intersection)) {

                if (edges.contains(new Pair<>(intersectionAndDistance.x, intersection)) || edges.contains(new Pair<>(intersection, intersectionAndDistance.x))) {
                    continue;
                }


                edges.add(new Pair<>(intersection, intersectionAndDistance.x));
            }

        }


        //find weights
        List<Triple<Integer, Pair<Integer, Integer>, Pair<Integer, Integer>>> weights = new ArrayList<>();

        for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> edge : edges) {
            int distance = 0;
            List<Pair<Pair<Integer, Integer>, Integer>> intersectionsAndDistance = intersectionsAndDistances.get(edge.x);
            for (Pair<Pair<Integer, Integer>, Integer> intersectionAndDistance : intersectionsAndDistance) {
                if (intersectionAndDistance.x.equals(edge.y)) {
                    distance = intersectionAndDistance.y;
                }
            }


            weights.add(new Triple<>(distance, edge.x, edge.y));
        }


        //create for each intersection a list of possible neighbours
        HashMap<Pair<Integer, Integer>, List<Pair<Pair<Integer, Integer>, Integer>>> hashMapNeighbours = new HashMap<>();

        for (Pair<Integer, Integer> intersection : intersectionList) {
            hashMapNeighbours.put(intersection, new ArrayList<>());
        }

        for (Triple<Integer, Pair<Integer, Integer>, Pair<Integer, Integer>> weight : weights) {
            hashMapNeighbours.get(weight.y).add(new Pair<>(weight.z, weight.x));
        }

        for (Triple<Integer, Pair<Integer, Integer>, Pair<Integer, Integer>> weight : weights) {
            hashMapNeighbours.get(weight.z).add(new Pair<>(weight.y, weight.x));
        }


        //find the longest path
        List<Pair<Integer, Integer>> longestPathList = new ArrayList<>();

        int longestPath = 0;

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
                if (currentDistance > longestPath) {
                    longestPath = currentDistance;
                    longestPathList = currentVisited;
                }
                continue;
            }

            List<Pair<Pair<Integer, Integer>, Integer>> neighbours = hashMapNeighbours.get(currentPos);

            for (Pair<Pair<Integer, Integer>, Integer> neighbour : neighbours) {

                List<Pair<Integer, Integer>> newVisited = new ArrayList<>(currentVisited);
                if (currentVisited.contains(neighbour.x)) {
                    continue;
                } else {
                    newVisited.add(neighbour.x);
                }




                queue.add(new Triple<>(currentDistance + neighbour.y, neighbour.x, newVisited));
            }
        }

        displayGraph(intersectionList, intersectionsAndDistances, edges, longestPathList);

        return longestPath;
    }

    private void displayGraph(List<Pair<Integer, Integer>> intersections, HashMap<Pair<Integer, Integer>, List<Pair<Pair<Integer, Integer>, Integer>>> intersectionsAndDistances, List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> edges, List<Pair<Integer, Integer>> longestPathList) {
        Graph graph = new SingleGraph("A Long Walk");


        for (Pair<Integer, Integer> intersection : intersections) {
            graph.addNode(intersection.toString());

            Node temp = graph.getNode(intersection.toString());
            temp.setAttribute("ui.label", (intersection.x) + "," + (intersection.y));
            temp.setAttribute("ui.style", "fill-color: lightblue; text-color: blue;");


        }


        for (Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> edge : edges) {

            //distance
            List<Pair<Pair<Integer, Integer>, Integer>> intersectionsAndDistance = intersectionsAndDistances.get(edge.x);
            int distance = 0;
            for (Pair<Pair<Integer, Integer>, Integer> intersectionAndDistance : intersectionsAndDistance) {
                if (intersectionAndDistance.x.equals(edge.y)) {
                    distance = intersectionAndDistance.y;
                }
            }


            Edge temp = graph.addEdge(edge.x.toString() + edge.y.toString(), edge.x.toString(), edge.y.toString());
            temp.setAttribute("ui.label", String.valueOf(distance));
        }


        for (int i = 0; i < longestPathList.size() - 1; i++) {
            Edge temp = graph.getEdge(longestPathList.get(i).toString() + longestPathList.get(i + 1).toString());
            if (temp == null) {
                temp = graph.getEdge(longestPathList.get(i + 1).toString() + longestPathList.get(i).toString());
            }
            temp.setAttribute("ui.style", "fill-color: red;");


        }

        graph.display();
    }


    private List<Pair<Pair<Integer, Integer>, Integer>> findIntersection(char[][] map, Pair<Integer, Integer> start, List<Pair<Integer, Integer>> intersections) {

        List<Pair<Integer, Integer>> visited = new ArrayList<>();
        visited.add(start);

        Queue<Triple<Integer, Pair<Integer, Integer>, List<Pair<Integer, Integer>>>> queue = new LinkedList<>();

        queue.add(new Triple<>(0, start, new ArrayList<>(visited)));


        List<Pair<Pair<Integer, Integer>, Integer>> intersectionsAndDistance = new ArrayList<>();

        while (!queue.isEmpty()) {

            Triple<Integer, Pair<Integer, Integer>, List<Pair<Integer, Integer>>> current = queue.poll();
            Pair<Integer, Integer> currentPos = current.y;
            int currentDistance = current.x;
            List<Pair<Integer, Integer>> currentVisited = current.z;


            if (intersections.contains(currentPos) && !currentPos.equals(start)) {
                intersectionsAndDistance.add(new Pair<>(currentPos, currentDistance));
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

        //remove duplicates and keep the largest distance
        List<Pair<Pair<Integer, Integer>, Integer>> intersectionsAndDistance2 = new ArrayList<>();
        //sort by distance
        intersectionsAndDistance.sort(Comparator.comparingInt(o -> o.y));

        for (Pair<Pair<Integer, Integer>, Integer> pair : intersectionsAndDistance) {
            if (!intersectionsAndDistance2.contains(pair.x)) {
                intersectionsAndDistance2.add(pair);
            }
        }


        return intersectionsAndDistance2;
    }




}